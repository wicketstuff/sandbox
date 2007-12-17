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
package org.apache.wicket.cluster.session;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.Filter;

import org.apache.wicket.cluster.CommunicationModule;
import org.apache.wicket.cluster.Member;
import org.apache.wicket.cluster.MessageListener;
import org.apache.wicket.cluster.SessionProvider;
import org.apache.wicket.cluster.session.message.SessionMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SessionComponent implements MessageListener {
	
	private static SessionComponent instance = null;
	
	private CommunicationModule communicationModule;
	
	// ContextPath -> SessionProvider
	private Map<String, SessionProvider> contextPathToSessionProvider = new ConcurrentHashMap<String, SessionProvider>();
	
	public void registerSessionProvider(SessionProvider sessionProvider) {
		contextPathToSessionProvider.put(sessionProvider.getContextPath(), sessionProvider);
	}
	
	public SessionComponent(CommunicationModule communicationModule) {
		if (instance != null) {
			throw new IllegalStateException("Only one instance of SessionComponent can be created");
		}
		instance = this;
		
		this.communicationModule = communicationModule;		
	}
	
	public static SessionComponent getInstance() {
		return instance;
	}
	
	public CommunicationModule getCommunicationModule() {
		return communicationModule;
	}
	
	public boolean accepts(Serializable message) {
		return message instanceof SessionMessage;
	}
	
	public void onProcessMessage(Serializable message, Member sender) {
		SessionMessage sessionMessage = (SessionMessage) message;
		SessionProvider provider = contextPathToSessionProvider.get(sessionMessage.getContextPath());
		if (provider == null) {
			log.error("Couldn't find session provider for context " + sessionMessage.getContextPath());
		} else {
			sessionMessage.execute(provider);
		}
	}
	
	public static Class<? extends Filter> getFilterClass() {
		return ClusteredFilter.class;
	}
	
	private static final Logger log = LoggerFactory.getLogger(SessionComponent.class); 
	
}
