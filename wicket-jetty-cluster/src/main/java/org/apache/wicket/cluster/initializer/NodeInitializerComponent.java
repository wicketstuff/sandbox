package org.apache.wicket.cluster.initializer;

import java.io.Serializable;

import org.apache.wicket.cluster.Member;
import org.apache.wicket.cluster.MemberListener;
import org.apache.wicket.cluster.MessageListener;
import org.apache.wicket.cluster.MessageSender;
import org.apache.wicket.cluster.PageStoreReplicator;
import org.apache.wicket.cluster.SessionProvider;

public class NodeInitializerComponent implements MemberListener, MessageListener {

	private final NodeInitializer nodeInitializer;
	
	public NodeInitializerComponent(MessageSender messageSender, SessionProvider sessionProvider, PageStoreReplicator clusteredPageStore) {
		nodeInitializer = new NodeInitializer(messageSender, sessionProvider, clusteredPageStore);
	}
	
	public void onMemberAdded(Member member) {
		nodeInitializer.memberAdded(member);

	}

	public void onMemberRemoved(Member member) {
		
	}

	public boolean accepts(Serializable message) {
		return message instanceof InitializerMessage;
	}

	public void onProcessMessage(Serializable message, Member sender) {
		InitializerMessage initializerMessage = (InitializerMessage) message;
		initializerMessage.execute(nodeInitializer, sender);
	}

}
