package wicket.contrib.push.cometd;

import javax.servlet.ServletContext;

import org.mortbay.cometd.Bayeux;
import org.mortbay.cometd.Channel;
import org.mortbay.cometd.CometdServlet;

import wicket.contrib.push.IPushPublisher;
import wicket.contrib.push.PushEvent;
import wicket.protocol.http.WebApplication;

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
