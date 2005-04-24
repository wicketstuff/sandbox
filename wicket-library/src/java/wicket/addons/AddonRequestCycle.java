/*
 * $Id$
 * $Revision$
 * $Date$
 *
 * ====================================================================
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package wicket.addons;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.SessionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import wicket.Response;
import wicket.protocol.http.WebRequest;
import wicket.protocol.http.WebRequestCycle;
import wicket.protocol.http.WebSession;


/**
 * Special request cycle for this application that opens and closes a hibernate
 * session for each request.
 */
public final class AddonRequestCycle extends WebRequestCycle
{
	private static Log log = LogFactory.getLog(AddonRequestCycle.class);

	public static final String DEFAULT_SESSION_FACTORY_BEAN_NAME = "sessionFactory";
	private String sessionFactoryBeanName = DEFAULT_SESSION_FACTORY_BEAN_NAME;

	private transient boolean participate = false;

	private transient SessionFactory sessionFactory;
	private transient Session hibernateSession;
	
	private boolean singleSession = true;

	/**
	 * Construct.
	 * 
	 * @param session
	 *           session object
	 * @param request
	 *           request object
	 * @param response
	 *           response object
	 * @param sessionFactory
	 *           hibernate session factory
	 */
	public AddonRequestCycle(WebSession session, WebRequest request, Response response)
	{
		super(session, request, response);
	}

	/**
	 * Look up the SessionFactory that this filter should use.
	 * <p>
	 * Default implementation looks for a bean with the specified name in
	 * Spring's root application context.
	 * 
	 * @return the SessionFactory to use
	 * @see #getSessionFactoryBeanName
	 */
	private SessionFactory lookupSessionFactory()
	{
		if (log.isDebugEnabled())
		{
			log.debug("Using session factory '" + sessionFactoryBeanName
					+ "' for OpenSessionInViewFilter");
		}

		BeanFactory fac = ((AddonApplication)this.getApplication()).getBeanFactory();
		return (SessionFactory)fac.getBean(sessionFactoryBeanName);
	}

	/**
	 * Return whether to use a single session for each request.
	 */
	private boolean isSingleSession()
	{
		return singleSession;
	}

	/**
	 * @see wicket.RequestCycle#onBeginRequest()
	 */
	protected void onBeginRequest()
	{
		sessionFactory = lookupSessionFactory();

		if (isSingleSession())
		{
			// single session mode
			if (TransactionSynchronizationManager.hasResource(sessionFactory))
			{
				// do not modify the Session: just set the participate flag
				participate = true;
			}
			else
			{
				log.debug("Opening single Hibernate session in AddonRequestCycle");
				hibernateSession = getHibernateSession(sessionFactory);
				TransactionSynchronizationManager.bindResource(sessionFactory, new SessionHolder(
						hibernateSession));
			}
		}
		else
		{
			// deferred close mode
			if (SessionFactoryUtils.isDeferredCloseActive(sessionFactory))
			{
				// do not modify deferred close: just set the participate flag
				participate = true;
			}
			else
			{
				SessionFactoryUtils.initDeferredClose(sessionFactory);
			}
		}
	}

	/**
	 * @see wicket.RequestCycle#onEndRequest()
	 */
	protected void onEndRequest()
	{
		if (!participate)
		{
			if (isSingleSession())
			{
				// single session mode
				TransactionSynchronizationManager.unbindResource(sessionFactory);
				log.debug("Closing single Hibernate session in OpenSessionInViewFilter");
				closeHibernateSession(hibernateSession, sessionFactory);
			}
			else
			{
				// deferred close mode
				SessionFactoryUtils.processDeferredClose(sessionFactory);
			}
		}
	}

	/**
	 * Get a Session for the SessionFactory that this filter uses. Note that this
	 * just applies in single session mode!
	 * <p>
	 * The default implementation delegates to SessionFactoryUtils' getSession
	 * method and sets the Session's flushMode to NEVER.
	 * <p>
	 * Can be overridden in subclasses for creating a Session with a custom
	 * entity interceptor or JDBC exception translator.
	 * 
	 * @param sessionFactory
	 *           the SessionFactory that this filter uses
	 * @return the Session to use
	 * @throws DataAccessResourceFailureException
	 *            if the Session could not be created
	 * @see org.springframework.orm.hibernate3.SessionFactoryUtils#getSession(SessionFactory,
	 *      boolean)
	 * @see net.sf.hibernate.FlushMode#NEVER
	 */
	protected Session getHibernateSession(SessionFactory sessionFactory)
			throws DataAccessResourceFailureException
	{
		Session session = SessionFactoryUtils.getSession(sessionFactory, true);
		//session.setFlushMode(FlushMode.NEVER);
		return session;
	}

	/**
	 * Close the given Session. Note that this just applies in single session
	 * mode!
	 * <p>
	 * The default implementation delegates to SessionFactoryUtils'
	 * closeSessionIfNecessary method.
	 * <p>
	 * Can be overridden in subclasses, e.g. for flushing the Session before
	 * closing it. See class-level javadoc for a discussion of flush handling.
	 * Note that you should also override getSession accordingly, to set the
	 * flush mode to something else than NEVER.
	 * 
	 * @param session
	 *           the Session used for filtering
	 * @param sessionFactory
	 *           the SessionFactory that this filter uses
	 */
	protected void closeHibernateSession(Session session, SessionFactory sessionFactory)
	{
		SessionFactoryUtils.closeSessionIfNecessary(session, sessionFactory);
	}
}