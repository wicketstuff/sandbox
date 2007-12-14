import org.apache.wicket.protocol.http.WicketServlet;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.servlet.Context;
import org.mortbay.jetty.servlet.ServletHolder;

/*
 * Created on Dec 11, 2007
 */

/**
 * @author Daniel Spiewak
 */
public class ChartsDriver {
	public static void main(String[] args) throws Exception {
		Server server = new Server(8080);
		Context context = new Context(server, "/", Context.SESSIONS);

		ServletHolder servletHolder = new ServletHolder(new WicketServlet());
		servletHolder.setInitParameter("applicationClassName", "com.codecommit.wicket.test.ChartsApplication");
		servletHolder.setInitOrder(1);
		context.addServlet(servletHolder, "/*");

		server.start();
		server.join();
	}
}
