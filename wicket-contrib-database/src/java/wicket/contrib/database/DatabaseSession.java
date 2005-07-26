/**
 * Copyright (C) 2005, Jonathan W. Locke. All Rights Reserved.
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
