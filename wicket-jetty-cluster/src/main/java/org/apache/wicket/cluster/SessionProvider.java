package org.apache.wicket.cluster;

import java.util.Set;

import javax.servlet.http.HttpSession;

/**
 * Interface that the session manager must implement.
 * 
 * @author Matej Knopp
 */
public interface SessionProvider {

	/**
	 * Returns the http session with given Id. If the session doesn't exist and
	 * the createIfDoesNotExist parameter is <code>true</code>, new session
	 * is created (honoring the specified sessionId).
	 * 
	 * @param sessionId
	 * @param createIfDoesNotExist
	 * @return
	 */
	public HttpSession getSession(String sessionId, boolean createIfDoesNotExist);

	/**
	 * Returns set of active session ids. The returned set should not be
	 * affected by new sessions created after it has been created.
	 * 
	 * @return set of active session ids
	 */
	public Set<String> getActiveSessions();

	/**
	 * Returns the count of active http sessions.
	 * 
	 * @return
	 */
	public int getActiveSessionsCount();

}