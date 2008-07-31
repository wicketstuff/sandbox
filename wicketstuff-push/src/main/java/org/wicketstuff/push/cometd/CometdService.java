package org.wicketstuff.push.cometd;

import java.io.Serializable;

import org.apache.wicket.Component;
import org.apache.wicket.protocol.http.WebApplication;
import org.mortbay.cometd.BayeuxService;
import org.wicketstuff.push.ChannelEvent;
import org.wicketstuff.push.IChannelListener;
import org.wicketstuff.push.IChannelService;

import dojox.cometd.Bayeux;
import dojox.cometd.Channel;
import dojox.cometd.RemoveListener;

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
 * @author Rodolfo Hasen
 *
 * @see IChannelService
 */
public class CometdService extends BayeuxService
  implements IChannelService, Serializable {

  public static final String BAYEUX_CLIENT_PREFIX = "wicket-push";

  private static final long serialVersionUID = 1L;

  public CometdService(final WebApplication application) {
    //Get the Bayeux object from the servlet context
    super((Bayeux) application.getServletContext().getAttribute(
        Bayeux.DOJOX_COMETD_BAYEUX), BAYEUX_CLIENT_PREFIX);
  }

  public void addChannelListener(final Component component,
      final String channel, final IChannelListener listener) {
    component.add(new CometdBehavior(channel, listener));
  }


  /** Cometd Specific method to Listen for client removals
   * @param channel
   * @param listener
   */
  public void addChannelRemoveListener(final String channel,
      final RemoveListener listener) {
    getClient().addListener(listener);
  }

  /**
   * Implementation of {@link IChannelService#publish(ChannelEvent)}, which actually sends
   * a cometd event to the client with a "proxy" attribute set to "true", which in turn
   * triggers a wicket ajax call to get the listener notified and update the page.
   *
   * @event the event to publish, which will be modify with "proxy" set to "true"
   */
  public void publish(final ChannelEvent event) {
    /* to avoid using implementation specific events,
     * we set the proxy data here.
     *
     * this property is used by CometdDefaultBehaviorTemplate.js
     * to know that the event should actually be converted in a wicket ajax
     * call to get the actual page refresh
     */
    event.addData("proxy", "true");
    final Channel channel = getBayeux().getChannel("/" + event.getChannel(), true);
    channel.publish(getClient(), event.getData(), event.getId());
  }

}
