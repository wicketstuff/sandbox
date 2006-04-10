/*
 * $Id$ $Revision$
 * $Date$
 * 
 * ==================================================================== Licensed
 * under the Apache License, Version 2.0 (the "License"); you may not use this
 * file except in compliance with the License. You may obtain a copy of the
 * License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package wicket.addons.utils;

import java.io.Serializable;
import java.util.Collections;
import java.util.Map;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import wicket.Application;
import wicket.RequestCycle;
import wicket.addons.AddonSession;
import wicket.addons.BaseHtmlPage;
import wicket.addons.db.User;
import wicket.protocol.http.WebSession;
import EDU.oswego.cs.dl.util.concurrent.ConcurrentHashMap;

/**
 * Maintain a list of all users logged in to the web apps. The list is
 * controlled by means of a HttpSessionListener and possibly the Login and
 * Logout page.
 * <p>
 * Implements HttpSessionListener and thus must be configured with web.xml.
 * 
 * @author Juergen Donnerstag
 */
public class UserCount implements Serializable, HttpSessionListener
{
	/**
	 * A map of all userIds and there sessions (allow for users to be logged in
	 * more than once
	 */
	private static Map loggedInUsers = new ConcurrentHashMap();

	/**
	 * gets increased with each change to loggedInUsers. This allows to update
	 * cached copies of loggedInUsers, which is unmodifiable.
	 */
	private static int modificationId;

	/**
	 * Extract the userId from the HttpSession
	 * 
	 * @param evt
	 *            HttpSessionEvent
	 * @return the userId
	 */
	private User getUser(final HttpSessionEvent evt)
	{
		// Get Wicket Session object
		final AddonSession session = ((AddonSession)evt.getSession().getAttribute("session"));
		if (session == null)
		{
			return null;
		}

		return session.getUser();
	}

	/**
	 * Life cycle event method which is invoked when the session is created.
	 * 
	 * @see javax.servlet.http.HttpSessionListener#sessionCreated(javax.servlet.http.HttpSessionEvent)
	 * 
	 * @param http
	 *            session event
	 */
	public void sessionCreated(HttpSessionEvent evt)
	{
		// Add the user to the list of logged-on users
		addUser(getUser(evt), evt.getSession().getId());
	}

	/**
	 * Life cycle event method which is invoked when the session is destroyed.
	 * 
	 * @param http
	 *            session event
	 */
	public synchronized void sessionDestroyed(HttpSessionEvent evt)
	{
		// Remove the user from the list of logged-on users
		removeUser(null, evt.getSession().getId());
	}

	/**
	 * Add a user to the list of logged on users. The sessionId is taken from
	 * the RequestCycle attached to the current Thread.
	 * 
	 * @param userId
	 *            The user id
	 */
	public final synchronized static void addUser(final User user)
	{
		WebSession session = (WebSession)RequestCycle.get().getSession();
		if (session == null)
		{
			return;
		}

		addUser(user, getSessionId());
	}

	/**
	 * Add the userId and sessionId to the list of logged-on users. Duplicates
	 * (same userId and same sessionId) are stored only once.
	 * 
	 * @param userId
	 *            The user id
	 * @param sessionId
	 *            The session id
	 */
	public final synchronized static void addUser(final User user, final String sessionId)
	{
		// register users not logged in as well
		if (sessionId == null)
		{
			return;
		}

		if (loggedInUsers.get(sessionId) == null)
		{
			loggedInUsers.put(sessionId, user);
			modificationId++;
		}
	}

	/**
	 * Remove a user from the list of logged-on users.
	 * 
	 * @param userId
	 *            The user id
	 * @param sessionId
	 *            The session id
	 */
	public final synchronized static void removeUser(final User user, final String sessionId)
	{
		if (sessionId == null)
		{
			return;
		}

		if ((user != null) && !loggedInUsers.get(sessionId).equals(user))
		{
			return;
		}

		if (loggedInUsers.remove(sessionId) != null)
		{
			modificationId++;
		}
	}

	/**
	 * Remove a user from the list of logged-on users. All information required
	 * are accessible through the RequestCycle.
	 * 
	 * @param userId
	 */
	public final static void removeUser(final RequestCycle cycle)
	{
		final User user = ((BaseHtmlPage)cycle.getResponsePage()).getUser();
		if (user == null)
		{
			return;
		}

		final WebSession session = (WebSession)cycle.getSession();
		if (session == null)
		{
			return;
		}

		removeUser(user, getSessionId());
	}

	/**
	 * 
	 * @return xxx
	 */
	private static final String getSessionId()
	{
		return Application.get().getSessionFacade().getSessionId(RequestCycle.get().getRequest());
	}

	/**
	 * Get number of users currently logged-on
	 * 
	 * @return No. of users logged on
	 */
	final public static Integer getUserCount()
	{
		return new Integer(loggedInUsers.size());
	}

	/**
	 * 
	 * @return
	 */
	final public static Map getLoggedInUsers()
	{
		return Collections.unmodifiableMap(loggedInUsers);
	}

	/**
	 * 
	 * @return
	 */
	final public static int getModificationId()
	{
		return modificationId;
	}
}
