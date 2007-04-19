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

import org.apache.wicket.Response;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.protocol.http.WebRequest;
import org.apache.wicket.protocol.http.WebRequestCycle;

/**
 * Special request cycle implementation that lazy opens and later closes a
 * hibernate session for each request, as necessary. The session can get
 * retrieved by calling getHibernateSession(). The application referenced by the
 * session must implement the IHibernateDatabaseSouce interface.
 */
public class DatabaseWebRequestCycle extends WebRequestCycle
{
	/** Any database session for the current request cycle. */
	private transient DatabaseSession databaseSession = null;

	/**
	 * Construct.
	 * 
	 * @param application
	 *            application object
	 * @param request
	 *            request object
	 * @param response
	 *            response object
	 */
	public DatabaseWebRequestCycle(WebApplication application, WebRequest request, Response response)
	{
		super(application, request, response);
	}


	/**
	 * Gets the database session for this request.
	 * 
	 * @return The database session
	 */
	public DatabaseSession getDatabaseSession()
	{
		if (databaseSession == null)
		{
			final IDatabaseApplication application = (IDatabaseApplication)getSession()
					.getApplication();
			final Database database = application.getDatabase();
			databaseSession = database.newDatabaseSession();
		}
		return databaseSession;
	}

	/**
	 * @see wicket.RequestCycle#onEndRequest()
	 */
	protected void onEndRequest()
	{
		if (databaseSession != null)
		{
			try
			{
				((DatabaseWebSession)getWebSession()).beforeDatabaseSessionClose();
				databaseSession.close();
			}
			finally
			{
				databaseSession = null;
			}
		}
	}
}
