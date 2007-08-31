package org.apache.wicket.cluster.session.message;

import javax.servlet.http.HttpSession;

import org.apache.wicket.cluster.SessionProvider;

public class InvalidateSessionMessage implements SessionMessage {

	private static final long serialVersionUID = 1L;
		
	private final String contextPath;
	private final String sessionId;

	public InvalidateSessionMessage(String contextPath, String sessionId) {
		this.contextPath = contextPath;
		this.sessionId = sessionId;
	}
	
	public String getContextPath() {
		return contextPath;
	}
	
	public void execute(SessionProvider sessionProvider) {
		HttpSession session = sessionProvider.getSession(sessionId, false);
		if (session != null) {
			session.invalidate();
		}
	}

}
