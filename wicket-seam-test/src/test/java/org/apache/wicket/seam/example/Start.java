package org.apache.wicket.seam.example;

import org.mortbay.jetty.Connector;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.bio.SocketConnector;
import org.mortbay.jetty.plus.webapp.Configuration;
import org.mortbay.jetty.plus.webapp.EnvConfiguration;
import org.mortbay.jetty.webapp.WebAppContext;
import org.mortbay.jetty.webapp.WebInfConfiguration;

public class Start {

	public static void main(String[] args) throws Exception {

		Server server = new Server();
		SocketConnector connector = new SocketConnector();
		connector.setPort(8080);
		server.setConnectors(new Connector[] { connector });

		WebAppContext webappCtx = new WebAppContext();
		webappCtx
				.setConfigurationClasses(new String[] {
						WebInfConfiguration.class.getName(),
						EnvConfiguration.class.getName(),
						Configuration.class.getName() });

		webappCtx.setContextPath("/wicket-seam-example");
		webappCtx.setWar("src/main/webapp");
		server.addHandler(webappCtx);

		try {
			server.start();
			server.join();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(100);
		}
	}
}
