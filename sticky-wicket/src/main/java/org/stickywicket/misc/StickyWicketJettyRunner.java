// $Id: $
package org.stickywicket.misc;

import org.mortbay.jetty.Connector;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.nio.SelectChannelConnector;
import org.mortbay.jetty.servlet.DefaultServlet;
import org.mortbay.jetty.webapp.WebAppContext;

public class StickyWicketJettyRunner {
    public static void main(String[] args) {
        Server server = new Server();
        SelectChannelConnector connector = new SelectChannelConnector();
        connector.setPort(8080);
        server.setConnectors(new Connector[] { connector });

        WebAppContext web = new WebAppContext();
        web.setContextPath("/");
        web.setWar("src/main/webapp");
        web.setDistributable(true);
        web.addServlet(DefaultServlet.class, "/*");
        server.addHandler(web);
        
        // JMX
        // MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
        // MBeanContainer mBeanContainer = new MBeanContainer(mBeanServer);
        // server.getContainer().addEventListener(mBeanContainer);
        // mBeanContainer.start();

        try {
            server.start();
            server.join();
        }
        catch (Exception e) {
            e.printStackTrace();
            System.exit(100);
        }
    }

}
