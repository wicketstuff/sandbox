package wicket.contrib.scriptaculous.examples;

import wicket.Page;
import wicket.Request;
import wicket.Session;
import wicket.protocol.http.WebApplication;

/**
 * 
 */
public class ScriptaculousExamplesApplication extends WebApplication
{

	protected void init()
	{
		super.init();
		configure("development");
		getResourceSettings().setThrowExceptionOnMissingResource(false);
		getMarkupSettings().setAutomaticLinking(true);
	}

	/**
	 * @return class
	 */
	public Class< ? extends Page> getHomePage()
	{
		return ScriptaculousExamplesHomePage.class;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see wicket.protocol.http.WebApplication#newSession(wicket.Request)
	 */
	public Session newSession(Request request)
	{
		return new ScriptaculousExamplesSession(this, request);
	}
}
