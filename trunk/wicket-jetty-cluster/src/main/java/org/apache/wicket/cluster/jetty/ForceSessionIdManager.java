package org.apache.wicket.cluster.jetty;

import javax.servlet.http.HttpServletRequest;

import org.mortbay.jetty.servlet.HashSessionIdManager;

/**
 * Special Session Id manager that supports creating session with user specified
 * ID through use of {@link ForceSessionIdHttpRequest}.
 * 
 * @author Matej Knopp
 * 
 */
public class ForceSessionIdManager extends HashSessionIdManager {
	@Override
	public String newSessionId(HttpServletRequest request, long created) {
		if (request instanceof ForceSessionIdHttpRequest) {
			return request.getRequestedSessionId();
		} else {
			return super.newSessionId(request, created);
		}
	}
}
