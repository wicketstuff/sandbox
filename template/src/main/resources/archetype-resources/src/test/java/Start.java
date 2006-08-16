package wicket.myproject;

import org.grlea.log.SimpleLogger;
import org.mortbay.jetty.Connector;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.bio.SocketConnector;
import org.mortbay.jetty.webapp.WebAppContext;

public class Start {
    private static final SimpleLogger log = new SimpleLogger(Start.class);

    /**
     * Main function, starts the Jetty 6 server.
     *
     * @param args
     */
    public static void main(String[] args) {
        Server jettyServer = null;
        try {
            jettyServer = new Server();

            SocketConnector conn = new SocketConnector();
            conn.setPort(8080);
            jettyServer.setConnectors(new Connector[]{conn});

            WebAppContext wah = new WebAppContext();
            wah.setContextPath("/myapp");
            wah.setWar("src/main/webapp");

            jettyServer.setHandler(wah);

            jettyServer.start();
        }
        catch (Exception e) {
            log.fatal("Could not start the Jetty server: " + e);
            if (jettyServer != null) {
                try {
                    jettyServer.stop();
                }
                catch (Exception e1) {
                    log.fatal("Unable to stop the jetty server: " + e1);
                }
            }
        }
    }
}
