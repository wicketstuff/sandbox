package org.wicketstuff.push.cometd;

import javax.servlet.ServletContext;

import org.apache.wicket.protocol.http.WebApplication;
import org.mortbay.cometd.Bayeux;
import org.mortbay.cometd.Channel;
import org.mortbay.cometd.CometdServlet;
import org.wicketstuff.push.IPushPublisher;
import org.wicketstuff.push.PushEvent;

/**
 * A publisher taking a cometdEvent and send it the cometd bus
 * 
 * @author Vincent Demay
 */
public class CometdPublisher implements IPushPublisher{
	
	private WebApplication application;
	
	public CometdPublisher(WebApplication application) {
		super();
		this.application = application;
	}
	
	public void publish(PushEvent event){
		ServletContext ctxt = this.application.getServletContext();
		Bayeux b = (Bayeux)ctxt.getAttribute(CometdServlet.ORG_MORTBAY_BAYEUX);
		Channel c = b.getChannel("/" + event.getChannel());
		if (c!=null){
			c.publish(event.getData(), b.newClient());
		}
	
	}

}
