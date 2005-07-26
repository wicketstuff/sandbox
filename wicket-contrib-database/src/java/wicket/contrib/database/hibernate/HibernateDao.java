/**
 * Copyright (C) 2005, Jonathan W. Locke. All Rights Reserved.
 */

package wicket.contrib.database.hibernate;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;

import wicket.contrib.database.DatabaseDao;
import wicket.contrib.database.DatabaseException;


/**
 * Base class for DAO's.
 */
public abstract class HibernateDao extends DatabaseDao
{
	/** Used for logging. */
	private static Log log = LogFactory.getLog(HibernateDao.class);
	
	/**
	 * @param type
	 *            The class of object to find
	 * @return A list of all objects of the given type
	 */
	protected final List findAll(Class type)
	{
		try
		{
			Criteria criteria = getHibernateSession().createCriteria(type);
			return criteria.list();
		}
		catch (Exception e)
		{
			throw new DatabaseException(e);
		}
	}

	/**
	 * Look up object by name property.
	 * 
	 * @param type
	 *            Class of object
	 * @param name
	 *            Name property required
	 * @return Object with the given name.
	 */
	protected final Serializable findByName(Class type, String name)
	{
		try
		{
			Query query = getHibernateSession().createQuery(
					"from " + type + " as t where t.name = :name");
			query.setString("name", name);
			return (Serializable)query.uniqueResult();
		}
		catch (Exception e)
		{
			throw new DatabaseException(e);
		}
	}

	/**
	 * @return Hibernate session for this DAO
	 */
	protected Session getHibernateSession()
	{
		return ((HibernateDatabaseSession)getSession()).getHibernateSession();
	}
}
