/**
 * Copyright (C) 2005, Jonathan W. Locke. All Rights Reserved.
 */

package wicket.contrib.database;

import wicket.Application;
import wicket.IRequestCycleFactory;
import wicket.Request;
import wicket.RequestCycle;
import wicket.Response;
import wicket.Session;
import wicket.protocol.http.WebRequest;
import wicket.protocol.http.WebSession;

/**
 * WebSession subclass that conveniently implements a request cycle factory that
 * creates DatabaseWebRequestCycle objects which open and close database
 * sessions automatically as requests are made. The Application object must
 * implement IDatabaseApplication.
 * 
 * @author Jonathan Locke
 */
public class DatabaseWebSession extends WebSession
{
	/**
	 * Constructor
	 * 
	 * @param application
	 *            The application object
	 */
	public DatabaseWebSession(final Application application)
	{
		super(application);
	}

	/**
	 * @see wicket.Session#getRequestCycleFactory()
	 */
	protected IRequestCycleFactory getRequestCycleFactory()
	{
		return new IRequestCycleFactory()
		{
			public RequestCycle newRequestCycle(Session session, Request request, Response response)
			{
				return new DatabaseWebRequestCycle((WebSession)session, (WebRequest)request,
						response);
			}
		};
	}
}
