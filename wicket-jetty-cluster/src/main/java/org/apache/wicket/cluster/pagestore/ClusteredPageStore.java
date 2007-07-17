package org.apache.wicket.cluster.pagestore;

import org.apache.wicket.cluster.Member;
import org.apache.wicket.protocol.http.SecondLevelCacheSessionStore.IClusteredPageStore;

public interface ClusteredPageStore extends IClusteredPageStore {

	// even though this method has same signature as PageStoreReplicator#replicatePageStore,
	// this is entirely different interface to avoid confusion
	public void replicatePageStore(String sessionId, Member target);
	
}
