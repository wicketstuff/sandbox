package wicket.contrib.scriptaculous.examples;

import org.apache.wicket.ISessionFactory;
import org.apache.wicket.Request;
import org.apache.wicket.Response;
import org.apache.wicket.Session;
import org.apache.wicket.protocol.http.WebApplication;

/**
 * 
 */
public class ScriptaculousExamplesApplication extends WebApplication
{

	/**
	 * @return class
	 */
	public Class getHomePage()
	{
		return ScriptaculousExamplesHomePage.class;
	}

	protected ISessionFactory getSessionFactory()
	{
		return new ISessionFactory()
		{
			public Session newSession(Request request, Response response)
			{
				return new ScriptaculousExamplesSession(
						ScriptaculousExamplesApplication.this, request);
			}
		};
	}

	protected void init()
	{
		super.init();
		configure("development");
		getResourceSettings().setThrowExceptionOnMissingResource(false);
		getMarkupSettings().setAutomaticLinking(true);
	}
}
