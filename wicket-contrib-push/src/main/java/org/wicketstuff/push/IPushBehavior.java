package org.wicketstuff.push;

import java.util.Map;

/**
 * Add an implementation of this behavior to listen to event which 
 * been triggered by a {@link IPushPublisher}
 * 
 * @author Vincent Demay
 *
 */
public interface IPushBehavior {
	/**
	 * 
	 * @param channel channel which be used to listen to event
	 * @param datas data sent by the event
	 * @param target see {@link IPushTarget}
	 */
	public abstract void onEvent(String channel, Map<String, String> datas, IPushTarget target);

}
