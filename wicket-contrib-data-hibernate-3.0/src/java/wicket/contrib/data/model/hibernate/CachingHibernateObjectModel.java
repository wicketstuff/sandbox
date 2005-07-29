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
package wicket.contrib.data.model.hibernate;

import java.io.Serializable;

import wicket.model.IModel;

/**
 * Specialization of HibernateObjectModel that caches its result once it it loaded.
 *
 * @author Eelco Hillenius
 */
public class CachingHibernateObjectModel extends HibernateObjectModel
{
	/** the cached object. */
	private transient Object cached;

	/**
	 * Construct.
	 * @param idModel
	 * @param objectClass
	 * @param sessionDelegate
	 */
	public CachingHibernateObjectModel(IModel idModel, Class objectClass,
			IHibernateSessionDelegate sessionDelegate)
	{
		super(idModel, objectClass, sessionDelegate);
	}

	/**
	 * Construct.
	 * @param id
	 * @param objectClass
	 * @param sessionDelegate
	 */
	public CachingHibernateObjectModel(Serializable id, Class objectClass,
			IHibernateSessionDelegate sessionDelegate)
	{
		super(id, objectClass, sessionDelegate);
	}

	/**
	 * Construct.
	 * @param idModel
	 * @param objectClass
	 * @param sessionDelegate
	 * @param createNewObjectWhenIdIsNull
	 */
	public CachingHibernateObjectModel(IModel idModel, Class objectClass,
			IHibernateSessionDelegate sessionDelegate, boolean createNewObjectWhenIdIsNull)
	{
		super(idModel, objectClass, sessionDelegate, createNewObjectWhenIdIsNull);
	}

	/**
	 * Construct.
	 * @param id
	 * @param objectClass
	 * @param sessionDelegate
	 * @param createNewObjectWhenIdIsNull
	 */
	public CachingHibernateObjectModel(Serializable id, Class objectClass,
			IHibernateSessionDelegate sessionDelegate, boolean createNewObjectWhenIdIsNull)
	{
		super(id, objectClass, sessionDelegate, createNewObjectWhenIdIsNull);
	}

	/**
	 * Construct.
	 * @param idModel
	 * @param objectClass
	 * @param sessionDelegate
	 * @param nullIdHandler
	 */
	public CachingHibernateObjectModel(IModel idModel, Class objectClass,
			IHibernateSessionDelegate sessionDelegate, NullIdHandler nullIdHandler)
	{
		super(idModel, objectClass, sessionDelegate, nullIdHandler);
	}

	/**
	 * Construct.
	 * @param id
	 * @param objectClass
	 * @param sessionDelegate
	 * @param nullIdHandler
	 */
	public CachingHibernateObjectModel(Serializable id, Class objectClass,
			IHibernateSessionDelegate sessionDelegate, NullIdHandler nullIdHandler)
	{
		super(id, objectClass, sessionDelegate, nullIdHandler);
	}

	/**
	 * @see wicket.contrib.data.model.PersistentObjectModel#loadObject(java.io.Serializable)
	 */
	public Object loadObject(Serializable id)
	{
		if(cached == null)
		{
			cached = super.loadObject(id);
		}
		return cached;
	}

	/**
	 * Clears the cached model object so that on next getModel invocation, the model
	 * object will be reloaded.
	 */
	public void clear()
	{
		cached = null;
	}
}
