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
package org.apache.wicket.cluster.tribes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.catalina.tribes.Channel;
import org.apache.catalina.tribes.ChannelException;
import org.apache.catalina.tribes.ChannelListener;
import org.apache.catalina.tribes.Member;
import org.apache.catalina.tribes.MembershipListener;
import org.apache.catalina.tribes.group.GroupChannel;
import org.apache.catalina.tribes.membership.McastService;
import org.apache.catalina.tribes.transport.ReceiverBase;
import org.apache.catalina.tribes.transport.ReplicationTransmitter;
import org.apache.catalina.tribes.transport.nio.NioReceiver;
import org.apache.catalina.tribes.transport.nio.PooledParallelSender;
import org.apache.wicket.cluster.CommunicationModule;
import org.apache.wicket.cluster.MemberListener;
import org.apache.wicket.cluster.MessageListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TribesCommunicationModule implements CommunicationModule {

	private final Channel channel;

	public TribesCommunicationModule() {
		channel = newTribesChannel();
	}

	private Channel newTribesChannel() {
		
		McastService membership = new McastService();
		
		// default multicast address is 228.0.0.4
		// default multicast port is 45564
		
		Integer customPort = Integer.getInteger("wjc-multicast-port");
		if (customPort != null)
			membership.setPort(customPort.intValue());
		
		
		String customAddress = System.getProperty("wjc-multicast-address");
		if (customAddress != null)
			membership.setAddress(customAddress);
		
		ReceiverBase receiver = new NioReceiver();
		receiver.setAddress("auto");

		ReplicationTransmitter sender = new ReplicationTransmitter();
		sender.setTransport(new PooledParallelSender());

		GroupChannel channel = new GroupChannel();
		channel.setMembershipService(membership);
		channel.setChannelReceiver(receiver);
		channel.setChannelSender(sender);

		channel.addChannelListener(new DefaultChannelListener());
		channel.addMembershipListener(new DefaultMembershipListener());
		return channel;
	}

	private List<MemberListener> memberListeners = Collections.synchronizedList(new ArrayList<MemberListener>());

	private List<MessageListener> messageListeners = Collections.synchronizedList(new ArrayList<MessageListener>());

	public synchronized void addMemberListener(MemberListener listener) {
		memberListeners.add(listener);
	}

	public void addMessageListener(MessageListener listener) {
		messageListeners.add(listener);
	}

	private static final ThreadLocal<Map<Member, List<Serializable>>> batch = new ThreadLocal<Map<Member, List<Serializable>>>();

	public void beginMessagesBatch() {
		batch.set(new HashMap<Member, List<Serializable>>());

	}

	private void sendTribesMessage(Member member, Serializable message) {
		Member[] ready;
		if (member != null) {
			ready = new Member[] { member };
		} else {
			Member[] group = channel.getMembers();
			ready = new Member[group.length];
			int i = 0;
			for (Member m : group) {
				if (m.isReady())
					ready[i++] = m;
			}
		}
		if (ready.length > 0 && ready[0] != null) {
			try {
				channel.send(ready, message, Channel.SEND_OPTIONS_DEFAULT);
			} catch (ChannelException e) {
				log.error("Error sending tribe message.", e);
			}
		}

	}

	public void endMessagesBatch() {
		Map<Member, List<Serializable>> messages = batch.get();
		if (messages != null) {
			for (Map.Entry<Member, List<Serializable>> entry : messages.entrySet()) {
				sendTribesMessage(entry.getKey(), (Serializable) entry.getValue());
			}

		}
		batch.set(null);
	}

	public void removeMemberListener(MemberListener listener) {
		memberListeners.remove(listener);
	}

	public void removeMessageListener(MessageListener listener) {
		messageListeners.remove(listener);
	}

	public void run() {
		try {
			channel.start(Channel.DEFAULT);
		} catch (ChannelException e) {
			throw new RuntimeException(e);
		}
		log.info("Tribes node started.");
	}

	public void sendMessage(Serializable message) {
		sendMessage(message, null);
	}

	public void sendMessage(Serializable message, org.apache.wicket.cluster.Member member) {

		if (member != null && member instanceof TribesMember == false)
			throw new IllegalArgumentException("Member must be instance of TribesMember");

		Member tribesMember = member != null ? ((TribesMember) member).getTribesMember() : null;

		Map<Member, List<Serializable>> map = batch.get();
		if (map != null) {
			List<Serializable> list = map.get(tribesMember);
			if (list == null) {
				list = new ArrayList<Serializable>();
				map.put(tribesMember, list);
			}
			list.add(message);
		} else {
			sendTribesMessage(tribesMember, message);
		}

	}

	private void processMessage(Serializable msg, Member sender) {
		List<MessageListener> copy = new ArrayList<MessageListener>(messageListeners);
		for (MessageListener listener : copy) {
			if (listener.accepts(msg))
				listener.onProcessMessage(msg, new TribesMember(sender));
		}
	}
	
	private boolean acceptMessage(Serializable message) {
		List<MessageListener> copy = new ArrayList<MessageListener>(messageListeners);
		for (MessageListener listener : copy) {
			if (listener.accepts(message))
				return true;
		}
		return false;
	}

	private class DefaultChannelListener implements ChannelListener {
		public boolean accept(Serializable msg, Member sender) {
			if (msg instanceof Collection) {
				for (Object o : ((Collection<?>)msg)) {
					if (acceptMessage((Serializable)o))
						return true;
				}
				return false;
			} else {
				return acceptMessage(msg);
			}
		}

		public void messageReceived(Serializable msg, Member sender) {
			if (msg instanceof Collection) {
				for (Object o : ((Collection<?>)msg)) {
					processMessage((Serializable)o, sender);
				}
			} else {
				processMessage(msg, sender);
			}
		}
	};

	private class DefaultMembershipListener implements MembershipListener {
		public void memberAdded(Member member) {
			log.info("Member added - " + member.toString());
			List<MemberListener> copy = new ArrayList<MemberListener>(memberListeners);
			for (MemberListener listener : copy) {
				listener.onMemberAdded(new TribesMember(member));
			}
		}

		public void memberDisappeared(Member member) {
			log.info("Member disappeared - " + member.toString());
			List<MemberListener> copy = new ArrayList<MemberListener>(memberListeners);
			for (MemberListener listener : copy) {
				listener.onMemberRemoved(new TribesMember(member));
			}
		}
	}

	private static final Logger log = LoggerFactory.getLogger(TribesCommunicationModule.class);
}
