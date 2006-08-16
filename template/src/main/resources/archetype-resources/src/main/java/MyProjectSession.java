package ${groupId};

import wicket.protocol.http.WebApplication;
import wicket.protocol.http.WebSession;

/**
 * Subclass of WebSession for MyProjectApplication to allow easy and typesafe
 * access to session properties.
 * 
 * @author Jonathan Locke
 */
public final class MyProjectSession extends WebSession
{
	//  TODO Add any session properties here

	/**
	 * Constructor
	 * 
	 * @param application
	 *            The application
	 */
	protected MyProjectSession(final WebApplication application)
	{
		super(application);
	}
}
