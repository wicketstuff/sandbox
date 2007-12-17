package org.apache.wicket.cluster.initializer.message;

import org.apache.wicket.cluster.Member;
import org.apache.wicket.cluster.initializer.NodeInitializer;
import org.apache.wicket.cluster.initializer.InitializerMessage;



public class AddSessionCountMessage implements InitializerMessage {
	private static final long serialVersionUID = 1L;
	
	private final int sessionCount;

	public AddSessionCountMessage(int sessionCount) {
		this.sessionCount = sessionCount;
	}

	public void execute(NodeInitializer initializer, Member member) {
		initializer.addSessionCount(sessionCount, member);
	}
}