package org.wicketstuff.push.examples.application;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.protocol.http.WebApplication;
import org.wicketstuff.push.examples.pages.Index;

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