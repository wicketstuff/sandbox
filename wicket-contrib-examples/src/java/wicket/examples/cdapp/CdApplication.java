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

import org.apache.wicket.IRequestCycleFactory;
import org.apache.wicket.Request;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.Response;
import org.apache.wicket.Session;
import org.apache.wicket.examples.WicketExampleApplication;
import org.apache.wicket.examples.cdapp.util.DatabaseUtil;
import org.apache.wicket.protocol.http.WebRequest;
import org.apache.wicket.protocol.http.WebSession;

/**
 * Wicket test application.
 * 
 * @author Eelco Hillenius
 */
public class CdApplication extends WicketExampleApplication
{
	/** Logger. */
	private static Log log = LogFactory.getLog(CdApplication.class);

	/**
	 * custom request cycle factory.
	 */
	private static class CdRequestCycleFactory implements IRequestCycleFactory
	{
		/** hibernate session factory. */
		private final SessionFactory sessionFactory;

		/**
		 * Construct.
		 */
		public CdRequestCycleFactory()
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
		}

		/**
		 * @see wicket.IRequestCycleFactory#newRequestCycle(wicket.Session,
		 *      wicket.Request, wicket.Response)
		 */
		public RequestCycle newRequestCycle(Session session, Request request, Response response)
		{
			return new CdAppRequestCycle((WebSession)session, (WebRequest)request, response,
					sessionFactory);
		}
	};

	private final CdRequestCycleFactory requestCycleFactory;

	/**
	 * Constructor
	 */
	public CdApplication()
	{
		requestCycleFactory = new CdRequestCycleFactory();
	}

	/**
	 * @see wicket.protocol.http.WebApplication#init()
	 */
	protected void init()
	{
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
	 * @see wicket.protocol.http.WebApplication#getDefaultRequestCycleFactory()
	 */
	protected IRequestCycleFactory getDefaultRequestCycleFactory()
	{
		return requestCycleFactory;
	}
}