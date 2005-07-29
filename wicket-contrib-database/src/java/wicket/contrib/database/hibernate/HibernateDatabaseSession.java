/*
 * $Id: HibernateDatabaseSession.java,v 1.5 2005/02/22 17:42:33 jonathanlocke
 * Exp $ $Revision$ $Date$
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
package wicket.contrib.database.hibernate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import wicket.contrib.database.DatabaseException;
import wicket.contrib.database.DatabaseSession;

/**
 * Thin wrapper around database session for abstraction.
 * 
 * @author Jonathan Locke
 */
public class HibernateDatabaseSession extends DatabaseSession
{
	/** Used for logging. */
	private static Log log = LogFactory.getLog(HibernateDatabase.class);

	/** The wrapped hibernate session object */
	private Session hibernateSession;

	/** Any request-level transaction going on */
	private Transaction transaction = null;

	/**
	 * Constructor
	 * 
	 * @param database
	 *            The hibernate database for this session
	 * @param hibernateSession
	 *            The hibernate session object to wrap
	 */
	public HibernateDatabaseSession(final HibernateDatabase database, final Session hibernateSession)
	{
		super(database);
		this.hibernateSession = hibernateSession;
	}

	/**
	 * Close this session
	 */
	public void close()
	{
		if (hibernateSession != null)
		{
			hibernateSession.flush();
			if (transaction != null)
			{
				try
				{
					transaction.commit();
				}
				catch (HibernateException e)
				{
					if (transaction != null)
					{
						try
						{
							transaction.rollback();
						}
						catch (HibernateException e1)
						{
							log.error("Unable to rollback transaction", e1);
						}
					}
					log.error(e.getMessage());
					throw new DatabaseException(e);
				}
			}
			hibernateSession.close();
		}
	}

	/**
	 * @see wicket.contrib.database.DatabaseSession#delete(java.lang.Class,
	 *      java.lang.Long)
	 */
	public void delete(final Class c, final Long id)
	{
		transaction(new Runnable()
		{
			public void run()
			{
				hibernateSession.delete(hibernateSession.load(c, id));
			}
		});
	}

	/**
	 * @see wicket.contrib.database.DatabaseSession#delete(java.lang.Object)
	 */
	public void delete(final Object object)
	{
		transaction(new Runnable()
		{
			public void run()
			{
				hibernateSession.delete(object);
			}
		});
	}

	/**
	 * Evict object from Hibernate session cache.
	 * 
	 * @param object
	 * @see Session#evict(java.lang.Object)
	 */
	public final void evict(Object object)
	{
		getHibernateSession().evict(object);
	}

	/**
	 * @return The underlying hibernate session
	 */
	public Session getHibernateSession()
	{
		return hibernateSession;
	}

	/**
	 * @see wicket.contrib.database.DatabaseSession#load(java.lang.Class,
	 *      java.lang.Long)
	 */
	public Object load(final Class c, final Long id) throws HibernateException
	{
		if (id == null)
		{
			throw new NullPointerException("Id must be not null");
		}
		return hibernateSession.load(c, id);
	}

	/**
	 * @param queryString
	 *            The select string
	 * @return The object
	 */
	public Object query(final String queryString)
	{
		Query query = hibernateSession.createQuery(queryString);
		return query.uniqueResult();
	}

	/**
	 * @see wicket.contrib.database.DatabaseSession#save(java.lang.Object)
	 */
	public void save(final Object object)
	{
		transaction(new Runnable()
		{
			public void run()
			{
				hibernateSession.save(object);
			}
		});
	}

	/**
	 * @see wicket.contrib.database.DatabaseSession#saveOrUpdate(java.lang.Object)
	 */
	public void saveOrUpdate(final Object object)
	{
		transaction(new Runnable()
		{
			public void run()
			{
				hibernateSession.saveOrUpdate(object);
			}
		});
	}

	/**
	 * Executes a command within a transaction.
	 * 
	 * @param runnable
	 *            The code that executes the transaction
	 */
	public final void transaction(Runnable runnable)
	{
		if (getTransactionSemantics() == DatabaseSession.TRANSACT_OPERATIONS)
		{
			Transaction transaction = null;
			try
			{
				transaction = hibernateSession.beginTransaction();
				runnable.run();
				hibernateSession.flush();
				transaction.commit();
			}
			catch (HibernateException e)
			{
				if (transaction != null)
				{
					try
					{
						transaction.rollback();
					}
					catch (HibernateException e1)
					{
						log.error("Unable to rollback transaction", e1);
					}
				}
				log.error(e.getMessage());
				throw new DatabaseException(e);
			}
		}
		else
		{
			if (transaction == null)
			{
				transaction = hibernateSession.beginTransaction();
			}
			runnable.run();
		}
	}

	/**
	 * @see wicket.contrib.database.DatabaseSession#update(java.lang.Object)
	 */
	public void update(final Object object)
	{
		transaction(new Runnable()
		{
			public void run()
			{
				hibernateSession.update(object);
			}
		});
	}
}
