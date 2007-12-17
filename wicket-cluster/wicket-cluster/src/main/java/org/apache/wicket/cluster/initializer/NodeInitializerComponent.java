/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
	
	public NodeInitializerComponent(MessageSender messageSender, PageStoreReplicator pageStoreReplicator) {
		nodeInitializer = new NodeInitializer(messageSender, pageStoreReplicator);
	}
	
	public void registerSessionProvider(SessionProvider provider) {
		nodeInitializer.registerSessionProvider(provider);
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
