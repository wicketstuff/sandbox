/*
 * $Id$ $Revision:
 * 1629 $ $Date$
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

import org.apache.wicket.Application;
import org.apache.wicket.IRequestCycleFactory;
import org.apache.wicket.Request;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.Response;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.protocol.http.WebRequest;
import org.apache.wicket.protocol.http.WebSession;

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
	 * Called just before the database session is closed
	 */
	protected void beforeDatabaseSessionClose()
	{
	}

	/**
	 * @see wicket.Session#getRequestCycleFactory()
	 */
	protected IRequestCycleFactory getRequestCycleFactory()
	{
		return new IRequestCycleFactory()
		{
			private static final long serialVersionUID = 1L;

			public RequestCycle newRequestCycle(Application application, Request request,
					Response response)
			{
				return new DatabaseWebRequestCycle((WebApplication)application,
						(WebRequest)request, response);
			}
		};
	}
}
