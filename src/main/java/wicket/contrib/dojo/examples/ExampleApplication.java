package wicket.contrib.dojo.examples;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import wicket.ISessionFactory;
import wicket.Request;
import wicket.Session;
import wicket.contrib.dojo.skin.manager.SkinManager;
import wicket.contrib.dojo.skin.windows.WindowsDojoSkin;
import wicket.contrib.dojo.toggle.DojoWipeToggle;
import wicket.protocol.http.WebApplication;

/**
 * Runs the ExampleApplication when invoked from command line.
 */
public class ExampleApplication extends WebApplication {
	/** Logging */
	private static final Log log = LogFactory.getLog(ExampleApplication.class);

	/**
	 * Constructor
	 */
	public ExampleApplication() {
			SkinManager.getInstance().setupSkin(new WindowsDojoSkin());
	}

	/**
	 * @see wicket.Application#getHomePage()
	 */
	public Class getHomePage() {
		return Index.class;
	}

	/**
	 * @see wicket.protocol.http.WebApplication#getSessionFactory()
	 */
	public ISessionFactory getSessionFactory() {
		return new ISessionFactory() {
			public Session newSession(Request request) {
				return new ExampleSession(ExampleApplication.this, request);
			}
		};
	}
}