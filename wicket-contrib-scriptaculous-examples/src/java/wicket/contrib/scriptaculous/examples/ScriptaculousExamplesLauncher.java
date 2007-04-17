package wicket.contrib.scriptaculous.examples;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mortbay.jetty.Connector;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.nio.SelectChannelConnector;
import org.mortbay.jetty.webapp.WebAppContext;

public class ScriptaculousExamplesLauncher
{

	/**
	 * Used for logging.
	 */
	private static final Log log = LogFactory.getLog(ScriptaculousExamplesLauncher.class);

	/**
	 * Main function, starts the jetty server.
	 * 
	 * @param args
	 */
	public static void main(String[] args)
	{
		Server server = new Server();
		SelectChannelConnector connector = new SelectChannelConnector();
		connector.setPort(8080);
		server.setConnectors(new Connector[] {connector});

		WebAppContext web = new WebAppContext();
		web.setContextPath("/wicket-examples");
		web.setWar("src/main/webapp");
		server.addHandler(web);

		// MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
		// MBeanContainer mBeanContainer = new MBeanContainer(mBeanServer);
		// server.getContainer().addEventListener(mBeanContainer);
		// mBeanContainer.start();

		try
		{
			server.start();
			server.join();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.exit(100);
		}
	}

	/**
	 * Construct.
	 */
	ScriptaculousExamplesLauncher()
	{
		super();
	}
}
