/*
 * $Id$
 * $Revision$ $Date$
 * 
 * ==============================================================================
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package wicket.contrib.database;

import wicket.IRequestCycleFactory;
import wicket.Request;
import wicket.RequestCycle;
import wicket.Response;
import wicket.Session;
import wicket.protocol.http.WebApplication;
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
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 * 
	 * @param application
	 *            The application object
	 * @param request
	 *            the current request
	 */
	public DatabaseWebSession(final WebApplication application, Request request)
	{
		super(application, request);
	}

	/**
	 * @see wicket.Session#getRequestCycleFactory()
	 */
	protected IRequestCycleFactory getRequestCycleFactory()
	{
		return new IRequestCycleFactory()
		{
			private static final long serialVersionUID = 1L;

			public RequestCycle newRequestCycle(Session session, Request request, Response response)
			{
				return new DatabaseWebRequestCycle((WebSession)session, (WebRequest)request,
						response);
			}
		};
	}

	/**
	 * Called just before the database session is closed
	 */
	protected void beforeDatabaseSessionClose()
	{
	}
}
