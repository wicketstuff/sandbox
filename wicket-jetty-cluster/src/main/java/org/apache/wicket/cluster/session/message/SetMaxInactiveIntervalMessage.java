package org.apache.wicket.cluster.session.message;

import javax.servlet.http.HttpSession;

import org.apache.wicket.cluster.SessionProvider;

public class SetMaxInactiveIntervalMessage implements SessionMessage {

	private static final long serialVersionUID = 1L;
	
	private final String sessionId;
	private final int maxInactiveInterval;
	
	public SetMaxInactiveIntervalMessage(String sessionId, int maxInactiveInterval) {
		this.sessionId = sessionId;
		this.maxInactiveInterval = maxInactiveInterval;
	}
	
	public void execute(SessionProvider sessionProvider) {
		HttpSession session = sessionProvider.getSession(sessionId, true);
		session.setMaxInactiveInterval(maxInactiveInterval);
	}

}
