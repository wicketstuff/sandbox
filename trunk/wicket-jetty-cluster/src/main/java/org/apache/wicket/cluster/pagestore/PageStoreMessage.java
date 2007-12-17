package org.apache.wicket.cluster.pagestore;

import java.io.Serializable;


public interface PageStoreMessage extends Serializable {

	public String getStoreId();
	
	public void execute(ClusteredPageStore pageStore);
	
}
