package org.apache.wicket.cluster.session.message;

import javax.servlet.http.HttpSession;

import org.apache.wicket.cluster.SessionProvider;


public class RemoveAttributeMessage implements SessionMessage {

	private static final long serialVersionUID = 1L;
	
	private final String sessionId;
	private final String attributeName;
	
	public RemoveAttributeMessage(String sessionId, String attributeName) {
		this.sessionId = sessionId;
		this.attributeName = attributeName;
	}
	
	public void execute(SessionProvider sessionProvider) {
		HttpSession session = sessionProvider.getSession(sessionId, true);
		session.removeAttribute(attributeName);
	}

}
