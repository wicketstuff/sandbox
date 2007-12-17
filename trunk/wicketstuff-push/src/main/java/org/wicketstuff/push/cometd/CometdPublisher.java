package org.wicketstuff.push.cometd;

import java.io.Serializable;

import javax.servlet.ServletContext;

import org.apache.wicket.protocol.http.WebApplication;
import org.mortbay.cometd.Bayeux;
import org.mortbay.cometd.Channel;
import org.mortbay.cometd.Client;
import org.mortbay.cometd.CometdServlet;
import org.wicketstuff.push.ChannelEvent;
import org.wicketstuff.push.IChannelPublisher;

/**
 * A publisher taking a cometdEvent and send it the cometd bus
 * 
 * @author Vincent Demay
 */
public class CometdPublisher implements IChannelPublisher, Serializable {
	private static final long serialVersionUID = 1L;
	
	private WebApplication application;
	
	// msparer: MemoryLeak: as clients are only random-generated clients, 
	// and do not have an impact on sender or receiver, it is not
	// necessary to create a new client on each "publish", better
	// take one (random)-client for all publish-calls.
	// otherwise the Bayeux's client-hashmap would grow towards
	// an outofmemoryerror
	transient private Client client;
	
	public CometdPublisher(WebApplication application) {
		super();
		this.application = application;		
		
	}
	
	public void publish(ChannelEvent event){
		ServletContext ctxt = this.application.getServletContext();
		Bayeux b = (Bayeux)ctxt.getAttribute(CometdServlet.ORG_MORTBAY_BAYEUX);
		Channel c = b.getChannel("/" + event.getChannel());
		
		if (c!=null){
			// msparer: initialise client lazily, as servletcontext can't be accessed
			// on constructor call
			if (this.client == null) {
				this.client = b.newClient();
			}
			
			c.publish(event.getData(), this.client);
		}
	
	}

}
