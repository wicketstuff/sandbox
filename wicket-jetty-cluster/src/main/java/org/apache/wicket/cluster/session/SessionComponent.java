package org.apache.wicket.cluster.session;

import java.io.Serializable;

import javax.servlet.Filter;

import org.apache.wicket.cluster.CommunicationModule;
import org.apache.wicket.cluster.Member;
import org.apache.wicket.cluster.MessageListener;
import org.apache.wicket.cluster.SessionProvider;
import org.apache.wicket.cluster.session.message.SessionMessage;


public class SessionComponent implements MessageListener {
	
	private static SessionProvider sessionProvider;
	private static CommunicationModule communicationModule;
	
	public SessionComponent(CommunicationModule communicationModule, SessionProvider sessionProvider) {
		if (SessionComponent.sessionProvider != null && SessionComponent.sessionProvider != sessionProvider)
			throw new IllegalStateException("Different session provider was already set");
		
		if (SessionComponent.communicationModule != null && SessionComponent.communicationModule != communicationModule)
			throw new IllegalStateException("Different communication module was already set");
		
		SessionComponent.sessionProvider = sessionProvider;
		SessionComponent.communicationModule = communicationModule;
	}
	
	public static CommunicationModule getCommunicationModule() {
		return communicationModule;
	}
	
	public boolean accepts(Serializable message) {
		return message instanceof SessionMessage;
	}
	
	public void onProcessMessage(Serializable message, Member sender) {
		SessionMessage sessionMessage = (SessionMessage) message;
		sessionMessage.execute(sessionProvider);
	}
	
	public static Class<? extends Filter> getFilterClass() {
		return ClusteredFilter.class;
	}
	
}
