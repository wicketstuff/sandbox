package org.apache.wicket.cluster.initializer;

import java.io.Serializable;

import org.apache.wicket.cluster.Member;

public interface InitializerMessage extends Serializable {
	
	public void execute(NodeInitializer initializer, Member member);
	
}
