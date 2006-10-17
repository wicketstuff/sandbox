package wicket.contrib;

import org.mortbay.jetty.Connector;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.nio.SelectChannelConnector;
import org.mortbay.jetty.webapp.WebAppContext;

/**
 * @author <a href="mailto:jlee at antwerkz.com">Justin Lee</a>
 */
public class Start {
    public static void main(String[] args) {
        Server server = new Server();
        SelectChannelConnector connector = new SelectChannelConnector();
        connector.setPort(8080);
        server.setConnectors(new Connector[]{connector});

        WebAppContext webapp = new WebAppContext();
        webapp.setServer(server);
        webapp.setContextPath("/");
        webapp.setWar("src/examples/webapp");

        server.addHandler(webapp);

        try {
            server.start();
        } catch(Exception e) {
            e.printStackTrace();
            System.out.println("Unable to start Jetty server");
        }
    }
}