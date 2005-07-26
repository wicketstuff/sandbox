//*
 * $Id$ $Revision:
 * 1.43 $ $Date$
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
 * Thin wrapper around database session for abstraction.
 * 
 * @author Jonathan Locke
 */
public abstract class DatabaseSession
{
	public DatabaseSession()
	{
	}

	/**
	 * Get an instance of a DAO class
	 * 
	 * @param c
	 *            The class of DAO
	 * @return The DAO
	 */
	public DatabaseDao newDao(final Class c)
	{
		try
		{
			DatabaseDao dao = (DatabaseDao)c.newInstance();
			dao.setSession(this);
			return dao;
		}
		catch (InstantiationException e)
		{
			throw new DatabaseException(e);
		}
		catch (IllegalAccessException e)
		{
			throw new DatabaseException(e);
		}
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
	 * Creates an object or updates it if it already exists
	 * 
	 * @param object
	 *            The object to save
	 */
	public abstract void save(final Object object);

	/**
	 * Deletes an object.
	 * 
	 * @param object
	 *            Object to delete
	 */
	public abstract void delete(Object object);

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
	 * Evict object from persistence cache
	 * 
	 * @param object
	 *            Object to evict
	 */
	public abstract void evict(Object object);

	/**
	 * Close this session
	 */
	public abstract void close();
}
