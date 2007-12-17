package org.apache.wicket.cluster;


public interface PageStoreReplicator  {

	public void replicatePageStore(String sessionId, Member target);
}