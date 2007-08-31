package org.apache.wicket.cluster.session.message;


import javax.servlet.http.HttpSession;

import org.apache.wicket.cluster.SessionProvider;
import org.apache.wicket.cluster.session.SessionAttributeHolder;



public class SetAttributeMessage implements SessionMessage {
	
	private static final long serialVersionUID = 1L;
	
	private final String contextPath;
	private final String sessionId;
	private final String attributeName;
	private final Object attributeValue;
		
	public SetAttributeMessage(String contextPath, String sessionId, String attributeName, Object attributeValue) {
		this.sessionId = sessionId;
		this.attributeName = attributeName;
		this.contextPath = contextPath;

		if (attributeValue instanceof SessionAttributeHolder)
			this.attributeValue = attributeValue;
		else
			this.attributeValue = new SessionAttributeHolder(attributeValue);
	}
	
	public String getContextPath() {
		return contextPath;
	};
	
	public void execute(SessionProvider sessionProvider) {
		HttpSession session = sessionProvider.getSession(sessionId, true);
		session.setAttribute(attributeName, attributeValue);
	}
}
