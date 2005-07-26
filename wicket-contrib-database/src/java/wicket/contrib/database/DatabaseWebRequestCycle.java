/**
 * Copyright (C) 2005, Jonathan W. Locke. All Rights Reserved.
 */

package wicket.contrib.database;

import org.hibernate.HibernateException;

import wicket.Response;
import wicket.protocol.http.WebRequest;
import wicket.protocol.http.WebRequestCycle;
import wicket.protocol.http.WebSession;

/**
 * Special request cycle implementation that lazy opens and later closes a
 * hibernate session for each request, as necessary. The session can get
 * retrieved by calling getHibernateSession(). The application referenced by the
 * session must implement the IHibernateDatabaseSouce interface.
 */
public final class DatabaseWebRequestCycle extends WebRequestCycle
{
	/** Any database session for the current request cycle. */
	private DatabaseSession databaseSession = null;

	/**
	 * Construct.
	 * 
	 * @param session
	 *            session object
	 * @param request
	 *            request object
	 * @param response
	 *            response object
	 */
	public DatabaseWebRequestCycle(WebSession session, WebRequest request, Response response)
	{
		super(session, request, response);
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
				databaseSession.close();
			}
			catch (HibernateException e)
			{
				throw new RuntimeException(e);
			}
			finally
			{
				databaseSession = null;
			}
		}
	}
}
