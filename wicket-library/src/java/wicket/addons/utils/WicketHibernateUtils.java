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
package wicket.addons.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.SessionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import wicket.addons.ServiceLocator;


/**
 * Special request cycle for this application that opens and closes a hibernate
 * session for each request.
 */
public final class WicketHibernateUtils 
{
	private static Log log = LogFactory.getLog(WicketHibernateUtils.class);

	private transient SessionFactory sessionFactory;
	private transient Session hibernateSession;
	private transient boolean participate = false;

	private boolean singleSession = true;

	/**
	 * Construct.
	 */
	public WicketHibernateUtils()
	{
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
		return (SessionFactory) ServiceLocator.instance().getUserService().getSessionFactory();
	}

	/**
	 * Return whether to use a single session for each request.
	 */
	private boolean isSingleSession()
	{
		return singleSession;
	}
	
	/**
	 * 
	 * @return
	 */
	public Session getCurrentHibernateSession()
	{
	    return this.hibernateSession;
	}

	/**
	 * @see wicket.RequestCycle#onBeginRequest()
	 */
	public void onBeginRequest()
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
				log.debug("Opening single Hibernate session in WicketHibernateUtils");
				hibernateSession = getHibernateSession(sessionFactory);
				TransactionSynchronizationManager.bindResource(sessionFactory, 
				        new SessionHolder(hibernateSession));
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
	public void onEndRequest()
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
	 * @see org.hibernate.FlushMode#NEVER
	 */
	private Session getHibernateSession(SessionFactory sessionFactory)
			throws DataAccessResourceFailureException
	{
		Session session = SessionFactoryUtils.getSession(sessionFactory, true);
		session.setFlushMode(FlushMode.NEVER);
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
	private void closeHibernateSession(Session session, SessionFactory sessionFactory)
	{
		SessionFactoryUtils.releaseSession(session, sessionFactory);
	}
}