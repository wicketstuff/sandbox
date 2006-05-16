/*
 * $Id$ $Revision:
 * 245 $ $Date$
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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import wicket.RequestCycle;

/**
 * Abstraction for database sessions. Any database implementation must be able
 * to perform at least these basic functions.
 * 
 * @author Jonathan Locke
 */
public abstract class DatabaseSession
{
	public static class TransactionScope
	{
	}

	public static final TransactionScope TRANSACT_OPERATIONS = new TransactionScope();
	public static final TransactionScope TRANSACT_REQUESTS = new TransactionScope();

	/** Used for logging. */
	private static Log log = LogFactory.getLog(DatabaseSession.class);

	/**
	 * @return Current database session for this web request cycle
	 */
	public static DatabaseSession get()
	{
		DatabaseWebRequestCycle cycle = (DatabaseWebRequestCycle)RequestCycle.get();
		if (cycle != null)
		{
			return cycle.getDatabaseSession();
		}
		return null;
	}

	/**
	 * Database for this session
	 */
	private Database database;

	/** 
	 * Transaction scope semantics
	 */
	private TransactionScope transactionSemantics;

	/**
	 * Construct
	 * 
	 * @param database
	 *            Database for this session
	 */
	public DatabaseSession(final Database database)
	{
		this.database = database;
		setTransactionSemantics(database.getDefaultTransactionSemantics());
	}

	/**
	 * Close this session
	 */
	public abstract void close();

	/**
	 * Deletes an object.
	 * 
	 * @param c
	 *            The class of object to delete
	 * @param id
	 *            The object id to delete
	 */
	public abstract void delete(final Class c, final Long id);

	/**
	 * Deletes an object.
	 * 
	 * @param object
	 *            Object to delete
	 */
	public abstract void delete(IDatabaseObject object);

	/**
	 * @see wicket.contrib.database.DatabaseSession#delete(java.lang.Class,
	 *      java.lang.Long)
	 */
	public void deleteTransaction(final Class c, final Long id)
	{
		transaction(new Runnable()
		{
			public void run()
			{
				delete(load(c, id));
			}
		});
	}

	/**
	 * @see wicket.contrib.database.DatabaseSession#delete(java.lang.Object)
	 */
	public void deleteTransaction(final IDatabaseObject object)
	{
		transaction(new Runnable()
		{
			public void run()
			{
				delete(object);
			}
		});
	}

	/**
	 * Evict object from persistence cache
	 * 
	 * @param object
	 *            Object to evict
	 */
	public abstract void evict(IDatabaseObject object);

	/**
	 * @return Returns the database.
	 */
	public Database getDatabase()
	{
		return database;
	}

	/**
	 * @return Returns the transactionSemantics.
	 */
	public final TransactionScope getTransactionSemantics()
	{
		return transactionSemantics;
	}

	/**
	 * Load the object with the given id
	 * 
	 * @param c
	 *            The class of object to load
	 * @param id
	 *            The object's id
	 * @return The object
	 */
	public abstract IDatabaseObject load(final Class c, final Long id);

	/**
	 * Creates an object
	 * 
	 * @param object
	 *            The object to save
	 */
	public abstract void save(final IDatabaseObject object);

	/**
	 * Creates an object or updates it if it already exists
	 * 
	 * @param object
	 *            The object to save
	 */
	public abstract void saveOrUpdate(final IDatabaseObject object);

	/**
	 * @see wicket.contrib.database.DatabaseSession#saveOrUpdate(java.lang.Object)
	 */
	public void saveOrUpdateTransaction(final IDatabaseObject object)
	{
		transaction(new Runnable()
		{
			public void run()
			{
				log.info("Save or update " + object);
				saveOrUpdate(object);
			}
		});
	}

	/**
	 * @see wicket.contrib.database.DatabaseSession#save(java.lang.Object)
	 */
	public void saveTransaction(final IDatabaseObject object)
	{
		transaction(new Runnable()
		{
			public void run()
			{
				log.info("Save " + object);
				save(object);
			}
		});
	}

	/**
	 * @param transactionSemantics
	 *            The transactionSemantics to set.
	 */
	public final void setTransactionSemantics(TransactionScope transactionSemantics)
	{
		this.transactionSemantics = transactionSemantics;
	}

	/**
	 * Executes a command within a transaction.
	 * 
	 * @param runnable
	 *            The code that executes the transaction
	 */
	public abstract void transaction(Runnable runnable);

	/**
	 * Updates an existing object
	 * 
	 * @param object
	 *            The object to update
	 */
	public abstract void update(final IDatabaseObject object);

	/**
	 * @see wicket.contrib.database.DatabaseSession#update(java.lang.Object)
	 */
	public void updateTransaction(final IDatabaseObject object)
	{
		transaction(new Runnable()
		{
			public void run()
			{
				log.info("Update " + object);
				update(object);
			}
		});
	}
}
