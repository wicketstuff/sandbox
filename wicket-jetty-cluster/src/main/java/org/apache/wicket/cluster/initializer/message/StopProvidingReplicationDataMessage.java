package org.apache.wicket.cluster.initializer.message;

import org.apache.wicket.cluster.Member;
import org.apache.wicket.cluster.initializer.NodeInitializer;
import org.apache.wicket.cluster.initializer.InitializerMessage;


public class StopProvidingReplicationDataMessage implements InitializerMessage {

	private static final long serialVersionUID = 1L;

	public void execute(NodeInitializer initializer, Member member) {
		
	}

}
