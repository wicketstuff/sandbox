package org.apache.wicket.cluster.initializer.message;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.wicket.cluster.Member;
import org.apache.wicket.cluster.initializer.NodeInitializer;
import org.apache.wicket.cluster.initializer.InitializerMessage;
import org.apache.wicket.cluster.session.SessionAttributeHolder;

public class ReplicateSessionMessage implements InitializerMessage {

	private static final long serialVersionUID = 1L;

	private final String id;
	private final int maxInactiveInterval;
	private final Map<String, SessionAttributeHolder> attributes;
	
	public ReplicateSessionMessage(HttpSession session) {
		this.id = session.getId();
		this.maxInactiveInterval = session.getMaxInactiveInterval();
		
		attributes = new HashMap<String, SessionAttributeHolder>();
		for (Enumeration<?> e = session.getAttributeNames(); e.hasMoreElements(); ) {
			String name = (String) e.nextElement();
			Object value = session.getAttribute(name);
			
			if (value instanceof SessionAttributeHolder == false) {
				value = new SessionAttributeHolder(value);
			}
			attributes.put(name, (SessionAttributeHolder)value);
		}
	}
	
	public void execute(NodeInitializer initializer, Member member) {
		initializer.replicateSession(id, maxInactiveInterval, attributes);
	}

}
