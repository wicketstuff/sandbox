package org.wicketstuff.push.cometd;

import java.io.Serializable;

import org.apache.wicket.Component;
import org.apache.wicket.protocol.http.WebApplication;
import org.wicketstuff.push.ChannelEvent;
import org.wicketstuff.push.IChannelListener;
import org.wicketstuff.push.IChannelPublisher;
import org.wicketstuff.push.IChannelService;

/**
 * Cometd based implementation of {@link IChannelService}.
 * <p>
 * This service is based on cometd client implemented by the dojo toolkit, which must
 * be embedded in your application.
 * <p>
 * This implementation relies on cometd for updating the page, but actually uses regular cometd 
 * events, which, if a channel listener is properly installed on a component of the page using
 * {@link #addChannelListener(Component, String, IChannelListener)}, will trigger a
 * wicket ajax call to get the page actually refreshed using regular wicket ajax mechanisms.
 * <p>
 * This mean that each time an event is published, a new connection is made to the server
 * to get the actual page update required by the {@link IChannelListener}.
 * 
 * @author Xavier Hanin
 * 
 * @see IChannelService
 */
public class CometdService implements IChannelService, Serializable {
	private static final long serialVersionUID = 1L;
	
	private IChannelPublisher publisher;
	
	public CometdService(WebApplication application) {
		publisher = new CometdPublisher(application);
	}

	public void addChannelListener(Component component, String channel, IChannelListener listener) {
		component.add(new CometdBehavior(channel, listener));
	}

	/**
	 * Implementation of {@link IChannelService#publish(ChannelEvent)}, which actually sends
	 * a cometd event to the client with a "proxy" attribute set to "true", which in turn
	 * triggers a wicket ajax call to get the listener notified and update the page.
	 * 
	 * @event the event to publish, which will be modify with "proxy" set to "true"
	 */
	public void publish(ChannelEvent event) {
		/* to avoid using implementation specific events, 
		 * we set the proxy data here.
		 * 
		 * this property is used by CometdDefaultBehaviorTemplate.js
		 * to know that the event should actually be converted in a wicket ajax
		 * call to get the actual page refresh
		 */ 
		event.addData("proxy", "true");
		publisher.publish(event);
	}

	/**
	 * Returns the publisher used underneath this service.
	 * Use this publisher directly if you want to send non proxy events 
	 * (events which do not trigger a wicket ajax call).
	 * 
	 * @return the publisher used underneath this service.
	 */
	public IChannelPublisher getPublisher() {
		return publisher;
	}
}
