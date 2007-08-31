package org.apache.wicket.cluster.pagestore;

import org.apache.wicket.cluster.Member;

public interface ClusteredPageStore  {

	// even though this method has same signature as PageStoreReplicator#replicatePageStore,
	// this is entirely different interface to avoid confusion
	public void replicatePageStore(String sessionId, Member target);
	
}
