package wicket.contrib.cometd.examples.application;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import wicket.contrib.cometd.examples.pages.Index;
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
		
	}

	/**
	 * @see wicket.Application#getHomePage()
	 */
	public Class getHomePage() {
		return Index.class;
	}
}