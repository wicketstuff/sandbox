package org.apache.wicket.cluster;

import java.io.Serializable;

public interface MessageListener {
	
	public boolean accepts(Serializable message);
	
	public void onProcessMessage(Serializable message, Member sender); 

}
