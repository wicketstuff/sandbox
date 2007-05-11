package org.wicketstuff.push.cometd;

import java.io.Serializable;

import javax.servlet.ServletContext;

import org.apache.wicket.protocol.http.WebApplication;
import org.mortbay.cometd.Bayeux;
import org.mortbay.cometd.Channel;
import org.mortbay.cometd.CometdServlet;
import org.wicketstuff.push.IChannelPublisher;
import org.wicketstuff.push.ChannelEvent;

/**
 * A publisher taking a cometdEvent and send it the cometd bus
 * 
 * @author Vincent Demay
 */
public class CometdPublisher implements IChannelPublisher, Serializable {
	private static final long serialVersionUID = 1L;
	
	private WebApplication application;
	
	public CometdPublisher(WebApplication application) {
		super();
		this.application = application;
	}
	
	public void publish(ChannelEvent event){
		ServletContext ctxt = this.application.getServletContext();
		Bayeux b = (Bayeux)ctxt.getAttribute(CometdServlet.ORG_MORTBAY_BAYEUX);
		Channel c = b.getChannel("/" + event.getChannel());
		if (c!=null){
			c.publish(event.getData(), b.newClient());
		}
	
	}

}
