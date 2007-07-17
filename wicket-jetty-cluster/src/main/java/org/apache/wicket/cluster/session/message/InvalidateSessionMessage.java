package org.apache.wicket.cluster.session.message;

import javax.servlet.http.HttpSession;

import org.apache.wicket.cluster.SessionProvider;

public class InvalidateSessionMessage implements SessionMessage {

	private static final long serialVersionUID = 1L;
	
	private final String sessionId;

	public InvalidateSessionMessage(String sessionId) {
		this.sessionId = sessionId;
	}
	
	public void execute(SessionProvider sessionProvider) {
		HttpSession session = sessionProvider.getSession(sessionId, false);
		if (session != null) {
			session.invalidate();
		}
	}

}
