package wicket.contrib.dojo.cometd.publisher;

import java.util.Map;

//import javax.servlet.ServletContext;

//import org.mortbay.cometd.Bayeux;
//import org.mortbay.cometd.Channel;
//import org.mortbay.cometd.CometdServlet;

import wicket.contrib.dojo.cometd.DojoSharedForm;
import wicket.protocol.http.WebApplication;

/**
 * <b>WARNING : This component is a prototype, Do NOT use it. It is still internal</b><br/>
 * {@link DojoSharedForm} uses Cometd to make it usable you should use an other servlet named cometd:
 * <pre>
 */
public class CometdPublisher {
	
	private WebApplication application;
	
	public CometdPublisher(WebApplication application) {
		super();
		this.application = application;
	}
	
	public void publish(String channel, Map message){
	/*	ServletContext ctxt = this.application.getServletContext();
		Bayeux b = (Bayeux)ctxt.getAttribute(CometdServlet.ORG_MORTBAY_BAYEUX);
		Channel c = b.getChannel(channel);
		if (c!=null){
			c.publish(message, b.newClient());
		}
	*/
	}

}
