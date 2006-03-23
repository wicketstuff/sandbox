/*
 * $Id$ $Revision$ $Date$
 * 
 * ==================================================================== Licensed
 * under the Apache License, Version 2.0 (the "License"); you may not use this
 * file except in compliance with the License. You may obtain a copy of the
 * License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package wicket.examples.cdapp;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import wicket.IRequestCycleFactory;
import wicket.ISessionFactory;
import wicket.Request;
import wicket.RequestCycle;
import wicket.Response;
import wicket.Session;
import wicket.examples.WicketExampleApplication;
import wicket.examples.cdapp.util.DatabaseUtil;
import wicket.protocol.http.WebRequest;
import wicket.protocol.http.WebSession;

/**
 * Wicket test application.
 * 
 * @author Eelco Hillenius
 */
public class CdApplication extends WicketExampleApplication implements ISessionFactory
{
	/** Logger. */
	private static Log log = LogFactory.getLog(CdApplication.class);

	/** hibernate session factory. */
	private SessionFactory sessionFactory;

	/**
	 * custom request cycle factory.
	 */
	private transient IRequestCycleFactory requestCycleFactory = new IRequestCycleFactory()
	{
		public RequestCycle newRequestCycle(Session session, Request request, Response response)
		{
			return new CdAppRequestCycle((WebSession)session, (WebRequest)request, response,
					sessionFactory);
		}

	};

	/**
	 * Constructor
	 */
	public CdApplication()
	{
	}

	/**
	 * @see wicket.protocol.http.WebApplication#init()
	 */
	protected void init()
	{
		try
		{
			final Configuration configuration = new Configuration();
			configuration.configure();
			// build hibernate SessionFactory for this application instance
			sessionFactory = configuration.buildSessionFactory();
			// create database
			new DatabaseUtil(configuration).createDatabase();
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}

		getResourceSettings().setThrowExceptionOnMissingResource(false);

		setSessionFactory(this);
	}

	/**
	 * @return class
	 */
	public Class getHomePage()
	{
		return Home.class;
	}

	/**
	 * @see wicket.ISessionFactory#newSession()
	 */
	public Session newSession()
	{
		return new WebSession(CdApplication.this)
		{
			/**
			 * @see wicket.protocol.http.WebSession#getRequestCycleFactory()
			 */
			protected IRequestCycleFactory getRequestCycleFactory()
			{
				return requestCycleFactory;
			}
		};
	}
}