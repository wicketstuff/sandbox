/*
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
package wicket.contrib.database.hibernate;

import java.io.Serializable;
import java.util.List;

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
	/**
	 * Constructor
	 */
	public HibernateDao()
	{
	}
	
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
