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
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import wicket.contrib.database.DatabaseException;
import wicket.contrib.database.DatabaseSession;
import wicket.contrib.database.IDatabaseObject;
import wicket.contrib.database.IDatabaseTransaction;

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
	 * @see wicket.contrib.database.DatabaseSession#attach(IDatabaseObject)
	 */
	public IDatabaseObject attach(final IDatabaseObject object)
	{
		// If object is not already in the session,
		if (!hibernateSession.contains(object))
		{
			// re-attach it to the session
			hibernateSession.lock(object, LockMode.NONE);
		}
		return object;
	}

	/**
	 * Close this session
	 */
	public void close()
	{
		if (hibernateSession != null)
		{
			hibernateSession.flush();
			hibernateSession.close();
		}
	}

	/**
	 * @see wicket.contrib.database.DatabaseSession#delete(java.lang.Object)
	 */
	public void delete(final IDatabaseObject object)
	{
		hibernateSession.delete(object);
	}

	/**
	 * @see wicket.contrib.database.DatabaseSession#evict(IDatabaseObject)
	 */
	public void evict(final IDatabaseObject object)
	{
		hibernateSession.evict(object);
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
	public IDatabaseObject load(final Class c, final Long id) throws HibernateException
	{
		if (id == null)
		{
			throw new IllegalArgumentException("Id must be not null");
		}

		// Get object from existing session if there is one
		final IDatabaseObject sessionObject = (IDatabaseObject)hibernateSession.get(c, id);
		if (sessionObject != null)
		{
			return sessionObject;
		}

		// Load the object
		return (IDatabaseObject)hibernateSession.load(c, id);
	}

	/**
	 * @param queryString
	 *            The select string
	 * @return The object
	 */
	public IDatabaseObject query(final String queryString)
	{
		final Query query = hibernateSession.createQuery(queryString);
		return (IDatabaseObject)query.uniqueResult();
	}

	/**
	 * @see wicket.contrib.database.DatabaseSession#save(java.lang.Object)
	 */
	public void save(final IDatabaseObject object)
	{
		log.info("Save " + object);
		hibernateSession.saveOrUpdate(object);
	}

	/**
	 * @see wicket.contrib.database.DatabaseSession#transaction(IDatabaseTransaction)
	 */
	public void transaction(final IDatabaseTransaction transactionCode)
	{
		final Transaction transaction = hibernateSession.beginTransaction();
		try
		{
			log.info("Transaction beginning");
			transaction.begin();
			transactionCode.run();
			log.info("Transaction committing");
			transaction.commit();
			log.info("Transaction committed");
			hibernateSession.flush();
		}
		catch (Exception e)
		{
			try
			{
				log.info("Rolling back transaction");
				transaction.rollback();
				log.info("Transaction rolled back");
			}
			catch (HibernateException e1)
			{
				log.error("Unable to rollback transaction", e1);
			}
			log.error(e.getMessage());
			throw new DatabaseException(e);
		}
	}
}
