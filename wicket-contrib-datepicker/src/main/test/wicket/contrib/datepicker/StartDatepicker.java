package wicket.contrib.datepicker;

import org.mortbay.jetty.Connector;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.bio.SocketConnector;
import org.mortbay.jetty.webapp.WebAppContext;

public class StartDatepicker {
	public static void main(String[] args) throws Exception {
		Server server = new Server();
		SocketConnector connector = new SocketConnector();
		connector.setPort(8080);
		server.setConnectors(new Connector[] { connector });

		WebAppContext ctx = new WebAppContext();
		ctx.setServer(server);
		ctx.setContextPath("/datepicker");
		ctx.setWar("src/main/webapp");

		server.addHandler(ctx);
		server.start();

	}
}
