package wicket.contrib.dojo.examples;

import wicket.Page;
import wicket.Request;
import wicket.Session;
import wicket.protocol.http.WebApplication;

/**
 * Runs the ExampleApplication when invoked from command line.
 */
public class ExampleApplication extends WebApplication {

	/**
	 * Constructor
	 */
	public ExampleApplication() {
	}

	/**
	 * @see wicket.Application#getHomePage()
	 */
	public Class<? extends Page> getHomePage() {
		return Index.class;
	}

	/**
	 * @see wicket.protocol.http.WebApplication#newSession(wicket.Request)
	 */
	public Session newSession(Request request) {
		return new ExampleSession(ExampleApplication.this, request);
	}
}
