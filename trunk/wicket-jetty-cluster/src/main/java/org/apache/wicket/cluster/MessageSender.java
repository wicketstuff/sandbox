package org.apache.wicket.cluster;

import java.io.Serializable;


/**
 * 
 * @author Matej Knopp
 */
public interface MessageSender {

	/**
	 * Sends message to all available nodes in cluster
	 * 
	 * @param message
	 */
	public void sendMessage(Serializable message);

	/**
	 * Sends message to given node of cluster. Unlike
	 * {@link #sendMessage(Serializable)}, this method throws an exception when
	 * sending of the message fails
	 * @throws RuntimeException
	 * @param message
	 */
	public void sendMessage(Serializable message, Member member);

}
