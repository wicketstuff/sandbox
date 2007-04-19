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

import org.apache.wicket.RequestCycle;

/**
 * Abstraction for database sessions. Any database implementation must be able
 * to perform at least these basic functions.
 * <p>
 * The functions in this class are abstracted and simplified over the semantics
 * of ORM implementations such as Hibernate or JDO.
 * 
 * @author Jonathan Locke
 */
public abstract class DatabaseSession
{
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
	 * Construct
	 * 
	 * @param database
	 *            Database for this session
	 */
	public DatabaseSession(final Database database)
	{
		this.database = database;
	}

	/**
	 * Makes the given object navigable again if it might have been "detached"
	 * from the database session. If the object with the given id is already in
	 * the session, that object will be returned. If not, the object itself is
	 * reattached to the session.
	 * 
	 * @param object
	 *            The object to reattach to the session
	 * @return A live object attached to the session with the same id as object,
	 *         either object or some other object already in the session with
	 *         the same id.
	 */
	public abstract IDatabaseObject attach(IDatabaseObject object);

	/**
	 * Close this session
	 */
	public abstract void close();

	/**
	 * Deletes an object.
	 * 
	 * @param object
	 *            Object to delete
	 */
	public abstract void delete(IDatabaseObject object);

	/**
	 * @see wicket.contrib.database.DatabaseSession#delete(java.lang.Object)
	 */
	public void deleteTransaction(final IDatabaseObject object)
	{
		transaction(new IDatabaseTransaction()
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
	 * Load the object with the given id. If an object with the given id already
	 * exists in the session, that object will be returned. This means that load
	 * will always safely give you the object with the given id without any
	 * identity confusion.
	 * 
	 * @param c
	 *            The class of object to load
	 * @param id
	 *            The object's id
	 * @return The object
	 */
	public abstract IDatabaseObject load(final Class c, final Long id);
	
	/**
	 * Saves the given object, overwriting any previous object. This method is
	 * implemented to avoid any kind of complexity in the implementation such as
	 * the update / save / saveOrUpdate methods of Hibernate or other ORMs.
	 * 
	 * @param object
	 *            The object to save
	 */
	public abstract void save(final IDatabaseObject object);

	/**
	 * @see wicket.contrib.database.DatabaseSession#save(java.lang.Object)
	 */
	public void saveTransaction(final IDatabaseObject object)
	{
		transaction(new IDatabaseTransaction()
		{
			public void run()
			{
				log.info("Save " + object);
				save(object);
			}
		});
	}

	/**
	 * Executes a command within a transaction.
	 * 
	 * @param transaction
	 *            The code that executes the transaction
	 */
	public abstract void transaction(IDatabaseTransaction transaction);
}
