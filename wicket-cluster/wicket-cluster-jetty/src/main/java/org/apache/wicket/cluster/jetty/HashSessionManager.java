/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.wicket.cluster.jetty;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.wicket.cluster.SessionProvider;
import org.mortbay.jetty.servlet.AbstractSessionManager;
import org.mortbay.util.LazyList;


/**
 * An in-memory implementation of SessionManager. Enhanced to implement the
 * {@link SessionProvider} interface.
 * 
 * @author Greg Wilkins (gregw)
 * @author Matej Knopp
 */
public class HashSessionManager extends AbstractSessionManager implements SessionProvider {
	private Timer timer;

	private TimerTask task;

	private int scavengePeriodMs = 30000;

	protected Map<String, Session> sessions;

	private final String contextPath;
	
	public HashSessionManager(String contextPath) {
		super();
		
		if (contextPath.endsWith("/")) {
			contextPath = contextPath.substring(0, contextPath.length() - 1);
		}
		this.contextPath = contextPath;
	}

	public void doStart() throws Exception {
		sessions = new HashMap<String, Session>();
		super.doStart();

		timer = new Timer();

		setScavengePeriod(getScavengePeriod());
	}

	public String getContextPath() {
		return contextPath;		
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see cluster.jetty.SessionProvider#getOrCreateSession(java.lang.String)
	 */
	public HttpSession getSession(String clusterId, boolean createIfDoesNotExist) {
		synchronized (HashSessionManager.this) {
			Session session = sessions.get(clusterId);
			if (session == null && createIfDoesNotExist) {
				session = (Session) newHttpSession(new ForceSessionIdHttpRequest(clusterId));
			}
			
			// only change timestamp when the createIfNotExist argument is set
			// otherwise the session is probably retrieved for reading only so we don't
			// need to update the timestamp
			if (session != null && createIfDoesNotExist) {
				session.access(System.currentTimeMillis());
			}
			return session;
		}
	}

	public void doStop() throws Exception {
		super.doStop();
		sessions.clear();
		sessions = null;

		// stop the scavenger
		synchronized (this) {
			if (task != null)
				task.cancel();
			if (timer != null)
				timer.cancel();
			timer = null;
		}
	}

	public int getScavengePeriod() {
		return scavengePeriodMs / 1000;
	}

	public Map<String, Session> getSessionMap() {
		return Collections.unmodifiableMap(sessions);
	}

	public int getSessions() {
		return sessions.size();
	}

	public void setMaxInactiveInterval(int seconds) {
		super.setMaxInactiveInterval(seconds);

		if (_dftMaxIdleSecs > 0 && scavengePeriodMs > _dftMaxIdleSecs * 1000)
			setScavengePeriod((_dftMaxIdleSecs + 9) / 10);
	}

	public void setScavengePeriod(int seconds) {
		if (seconds == 0)
			seconds = 60;

		int old_period = scavengePeriodMs;
		int period = seconds * 1000;
		if (period > 60000)
			period = 60000;
		if (period < 1000)
			period = 1000;

		scavengePeriodMs = period;
		if (timer != null && (period != old_period || task == null)) {
			synchronized (this) {
				if (task != null)
					task.cancel();
				task = new TimerTask() {
					public void run() {
						scavenge();
					}
				};
				timer.schedule(task, scavengePeriodMs, scavengePeriodMs);
			}
		}
	}

	/**
	 * Find sessions that have timed out and invalidate them. This runs in the
	 * SessionScavenger thread.
	 */
	private void scavenge() {
		// don't attempt to scavenge if we are shutting down
		if (isStopping() || isStopped())
			return;

		Thread thread = Thread.currentThread();
		ClassLoader old_loader = thread.getContextClassLoader();
		try {
			if (_loader != null)
				thread.setContextClassLoader(_loader);

			long now = System.currentTimeMillis();

			// Since Hashtable enumeration is not safe over deletes,
			// we build a list of stale sessions, then go back and invalidate
			// them
			Object stale = null;

			synchronized (HashSessionManager.this) {
				// For each session
				for (Iterator<Session> i = sessions.values().iterator(); i.hasNext();) {
					Session session = (Session) i.next();
					long idleTime = session.getMaxIdleMs();
					if (idleTime > 0 && session.getAccessed() + idleTime < now) {
						// Found a stale session, add it to the list
						stale = LazyList.add(stale, session);
					}
				}
			}

			// Remove the stale sessions
			for (int i = LazyList.size(stale); i-- > 0;) {
				// check it has not been accessed in the meantime
				Session session = (Session) LazyList.get(stale, i);
				long idleTime = session.getMaxIdleMs();
				if (idleTime > 0 && session.getAccessed() + idleTime < System.currentTimeMillis()) {
					((Session) session).timeout();
					int nbsess = this.sessions.size();
					if (nbsess < this._minSessions)
						this._minSessions = nbsess;
				}
			}
		} finally {
			thread.setContextClassLoader(old_loader);
		}
	}

	protected void addSession(AbstractSessionManager.Session session) {
		sessions.put(((Session) session).getClusterId(), (Session) session);
	}

	public Session getSession(String idInCluster) {
		return (Session) sessions.get(idInCluster);
	}

	protected void invalidateSessions() {
		// Invalidate all sessions to cause unbind events
		ArrayList<Session> sessionList = new ArrayList<Session>(sessions.values());
		for (Iterator<Session> i = sessionList.iterator(); i.hasNext();) {
			Session session = (Session) i.next();
			session.invalidate();
		}
		sessions.clear();

	}

	protected AbstractSessionManager.Session newSession(HttpServletRequest request) {
		return new Session(request);
	}

	protected void removeSession(String clusterId) {
		sessions.remove(clusterId);
	}

	protected class Session extends AbstractSessionManager.Session {
		private static final long serialVersionUID = -2134521374206116367L;

		protected Session(HttpServletRequest request) {
			super(request);
		}

		public void setMaxInactiveInterval(int secs) {
			super.setMaxInactiveInterval(secs);
			if (_maxIdleMs > 0 && (_maxIdleMs / 10) < scavengePeriodMs)
				HashSessionManager.this.setScavengePeriod((secs + 9) / 10);
		}

		protected Map<String, Object> newAttributeMap() {
			return new HashMap<String, Object>(3);
		}

		long getMaxIdleMs() {
			return _maxIdleMs;
		}

		long getAccessed() {
			return _accessed;
		}

		@Override
		protected void timeout() throws IllegalStateException {
			super.timeout();
		}

		@Override
		protected String getClusterId() {
			return super.getClusterId();
		}

		@Override
		protected void access(long time) {
			super.access(time);
		}
	}

	public Set<String> getActiveSessions() {
		return Collections.unmodifiableSet(new HashSet<String>(sessions.keySet()));
	}

	public int getActiveSessionsCount() {
		return sessions != null ? sessions.size() : 0;
	}

}
