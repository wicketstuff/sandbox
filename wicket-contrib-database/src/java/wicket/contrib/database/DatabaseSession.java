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

/**
 * Abstraction for database sessions. Any database implementation must be able
 * to perform at least these basic functions.
 * 
 * @author Jonathan Locke
 */
public abstract class DatabaseSession
{
	public static final TransactionScope TRANSACT_OPERATIONS = new TransactionScope();
	public static final TransactionScope TRANSACT_REQUESTS = new TransactionScope();
	private TransactionScope transactionSemantics;
	
	public static class TransactionScope { }

	public DatabaseSession(final Database database)
	{
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
	public abstract void delete(Object object);

	/**
	 * Evict object from persistence cache
	 * 
	 * @param object
	 *            Object to evict
	 */
	public abstract void evict(Object object);

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
	public abstract Object load(final Class c, final Long id);

	/**
	 * Creates an object
	 * 
	 * @param object
	 *            The object to save
	 */
	public abstract void save(final Object object);

	/**
	 * Creates an object or updates it if it already exists
	 * 
	 * @param object
	 *            The object to save
	 */
	public abstract void saveOrUpdate(final Object object);

	/**
	 * @param transactionSemantics The transactionSemantics to set.
	 */
	public final void setTransactionSemantics(TransactionScope transactionSemantics)
	{
		this.transactionSemantics = transactionSemantics;
	}

	/**
	 * Updates an existing object
	 * 
	 * @param object
	 *            The object to update
	 */
	public abstract void update(final Object object);
}
