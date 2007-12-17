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
import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.Application;
import org.apache.wicket.cluster.Member;
import org.apache.wicket.cluster.MessageSender;
import org.apache.wicket.protocol.http.SecondLevelCacheSessionStore;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.protocol.http.pagestore.DiskPageStore;
import org.apache.wicket.protocol.http.pagestore.PageWindowManager.PageWindow;
import org.apache.wicket.util.lang.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Clustered version of {@link DiskPageStore}. Install this by providing it
 * with your {@link SecondLevelCacheSessionStore} like this in your Application
 * object:
 * 
 * <pre>
 * protected ISessionStore newSessionStore() {
 * 	return new Ts4SecondLevelCacheSessionStore(this,
 * 			new ClusteredDiskPageStore());
 * }
 * </pre>
 */
public class ClusteredDiskPageStore extends DiskPageStore implements
		ClusteredPageStore {

	public ClusteredDiskPageStore() {
		this((int) Bytes.megabytes(10).bytes(), (int) Bytes.megabytes(100)
				.bytes(), 50);
	}

	public ClusteredDiskPageStore(int maxSizePerPagemap, int maxSizePerSession,
			int fileChannelPoolCapacity) {

		super(maxSizePerPagemap, maxSizePerSession, fileChannelPoolCapacity);

		WebApplication application = (WebApplication) Application.get();
		id = PageStoreComponent.registerPageStore(application
				.getServletContext(), application.getApplicationKey(), this);

		log.info("ClusteredDiskPageStore initialized.");
	}

	private final String id;

	private static class SaveFileMessage implements PageStoreMessage {

		private static final long serialVersionUID = 1L;

		private final String storeId;
		private final String sessionId;
		private final List<SerializedPage> pages;

		public SaveFileMessage(String storeId, String sessionId,
				List<SerializedPage> pages) {
			this.storeId = storeId;
			this.sessionId = sessionId;
			this.pages = pages;
		}

		public String getStoreId() {
			return storeId;
		}

		public void execute(ClusteredPageStore filePageStore) {
			ClusteredDiskPageStore pageStore = (ClusteredDiskPageStore) filePageStore;
			pageStore.storeSerializedPages(sessionId, pages);
		}
	}

	private MessageSender getMessageSender() {
		MessageSender messageSender = PageStoreComponent.getMessageSender();
		if (messageSender != null) {
			return messageSender;
		} else {
			log.warn("Message sender is not available! Was "
					+ "the server started up in clustered mode?");
			return new MessageSender() {

				public void sendMessage(Serializable message) {
				}

				public void sendMessage(Serializable message, Member member) {
				}
			};
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void onPagesSerialized(String sessionId, List pages) {
		SaveFileMessage message = new SaveFileMessage(id, sessionId,
				(List<SerializedPage>) pages);
		getMessageSender().sendMessage(message);
	}

	public int getNumPagesToInitialize() {
		return 10;
	}

	@SuppressWarnings("unused")
	private static class ReplicateSessionFilesMessage implements
			PageStoreMessage {

		private static final long serialVersionUID = 1L;

		private final String storeId;
		private final String sessionId;
		private final List<SerializedPage> pages;

		public ReplicateSessionFilesMessage(String storeId, String sessionId,
				List<SerializedPage> pages) {
			this.storeId = storeId;
			this.sessionId = sessionId;
			this.pages = pages;
		}

		public String getStoreId() {
			return storeId;
		}

		public void execute(ClusteredPageStore pageStore) {
			ClusteredDiskPageStore store = (ClusteredDiskPageStore) pageStore;
			store.storeSerializedPages(sessionId, pages);
		}

	}

	@SuppressWarnings("unchecked")
	private void replicateSessionEntry(SessionEntry entry, Member target) {
		List<SerializedPage> pages = new ArrayList<SerializedPage>();

		List<PageMapEntry> pageMapEntryList = entry.getPageMapEntryList();

		for (PageMapEntry pageMapEntry : pageMapEntryList) {

			// get last X pagewindows for the pagemap
			List<PageWindow> windows = pageMapEntry.getManager()
					.getLastPageWindows(getNumPagesToInitialize());

			for (PageWindow window : windows) {
				// make sure the window is valid

				if (window.getPageId() != -1) {
					// get the serialized page
					byte[] data = entry.loadPage(window, pageMapEntry
							.getFileName());
					SerializedPage page = new SerializedPage(
							window.getPageId(), pageMapEntry.getPageMapName(),
							window.getVersionNumber(), window
									.getAjaxVersionNumber(), data);
					pages.add(page);
				}
			}
		}

		if (pages.isEmpty() == false) {
			ReplicateSessionFilesMessage message = new ReplicateSessionFilesMessage(
					id, entry.getSessionId(), pages);
			getMessageSender().sendMessage(message, target);
		}
	}

	public void replicatePageStore(String sessionId, Member target) {
		SessionEntry entry = getSessionEntry(sessionId, false);
		synchronized (entry) {
			if (isSynchronous()) {
				replicateSessionEntry(entry, target);
			} else {
				List<?> pages = getPagesToSaveList(sessionId);
				synchronized (pages) {
					flushPagesToSaveList(sessionId, pages);
					replicateSessionEntry(entry, target);
				}
			}
		}
	}

	@Override
	public void destroy() {
		super.destroy();
		PageStoreComponent.unregisterPageStore(id);
	}

	private static final Logger log = LoggerFactory
			.getLogger(ClusteredDiskPageStore.class);
}
