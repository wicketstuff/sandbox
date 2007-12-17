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
package org.apache.wicket.cluster.pagestore;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletContext;

import org.apache.wicket.cluster.Member;
import org.apache.wicket.cluster.MessageListener;
import org.apache.wicket.cluster.MessageSender;
import org.apache.wicket.cluster.PageStoreReplicator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PageStoreComponent implements MessageListener, PageStoreReplicator {

	private static MessageSender messageSender;	
	
	private static final Map<String, ClusteredPageStore> idToPageStore = new ConcurrentHashMap<String, ClusteredPageStore>();
	
	/**
	 * @param application
	 * @param clusteredPageStore
	 * @return page store id
	 */
	public static String registerPageStore(ServletContext context, String applicationKey, ClusteredPageStore clusteredPageStore) {		
		String id = context.getContextPath() + "--" + applicationKey;
		idToPageStore.put(id, clusteredPageStore);
		return id;
	}
	
	public static void unregisterPageStore(String id) {
		idToPageStore.remove(id);
	}
	
	public PageStoreComponent(MessageSender messageSender) {
		if (PageStoreComponent.messageSender != null && PageStoreComponent.messageSender != messageSender)
			throw new IllegalStateException("Different message sender was already set");
		
		PageStoreComponent.messageSender = messageSender;
	}
	
	public static MessageSender getMessageSender() {
		return messageSender;
	}
	
	public boolean accepts(Serializable message) {
		return message instanceof PageStoreMessage;
	}
	
	public void onProcessMessage(Serializable message, Member sender) {
		PageStoreMessage pageStoreMessage = (PageStoreMessage) message;
		ClusteredPageStore store = idToPageStore.get(pageStoreMessage.getStoreId());
		if (store != null) {
			pageStoreMessage.execute(store);
		} else {
			log.error("PageStore with id " + pageStoreMessage.getStoreId() + " not found.");
		}
	}
	
	public void replicatePageStore(String sessionId, Member target) {
		for (ClusteredPageStore store : idToPageStore.values()) {
			store.replicatePageStore(sessionId, target);
		}
	}
	
	private static final Logger log = LoggerFactory.getLogger(PageStoreComponent.class);
}
