package org.apache.wicket.cluster.pagestore;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletContext;

import org.apache.wicket.cluster.PageStoreReplicator;
import org.apache.wicket.cluster.Member;
import org.apache.wicket.cluster.MessageListener;
import org.apache.wicket.cluster.MessageSender;
import org.apache.wicket.protocol.http.WebApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PageStoreComponent implements MessageListener, PageStoreReplicator {

	private static MessageSender messageSender;
	
	static final String SERVLET_CONTEXT_KEY = PageStoreReplicator.class.getName() + "-ServletContextKey";
	
	public static PageStoreReplicator getPageStore(ServletContext context) {
		return (PageStoreReplicator) context.getAttribute(SERVLET_CONTEXT_KEY);
	}
	
	private static final Map<String, ClusteredPageStore> idToPageStore = new ConcurrentHashMap<String, ClusteredPageStore>();
	
	/**
	 * @param application
	 * @param clusteredPageStore
	 * @return page store id
	 */
	public static String registerPageStore(WebApplication application, ClusteredPageStore clusteredPageStore) {
		ServletContext context = application.getServletContext();
		context.setAttribute(SERVLET_CONTEXT_KEY, clusteredPageStore);
		String id = application.getApplicationKey();
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