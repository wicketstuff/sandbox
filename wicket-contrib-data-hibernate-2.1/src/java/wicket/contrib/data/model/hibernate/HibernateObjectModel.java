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

import wicket.WicketRuntimeException;
import wicket.contrib.data.model.PersistentObjectModel;
import wicket.model.IModel;
import wicket.model.Model;

/**
 * Model for one Hibernate Object. It will load the object with the session that is
 * provided by the given instance of
 * {@link wicket.contrib.data.model.hibernate.IHibernateSessionDelegate}.
 * @author Eelco Hillenius
 */
public class HibernateObjectModel extends PersistentObjectModel
{
	/** class of the Hibernate object to load. */
	private final Class objectClass;

	/**
	 * property for special, but very common, case for handling null ids. If true, this
	 * model tries to construct a new (empty) one, that can be used to receive input (set
	 * properties etc). If false, null will be returned (and Ognl will probably throw an
	 * exception if no further action is taken).
	 */
	private final boolean createNewObjectWhenIdIsNull;

	/** Handler that is called when the id is null. */
	private final NullIdHandler nullIdHandler;

	/**
	 * Construct with a model that provides the id.
	 * @param idModel the model that provides the id
	 * @param sessionDelegate delegate that provides instances of
	 *            {@link net.sf.hibernate.Session}.
	 * @param objectClass class of the Hibernate object to load.
	 */
	public HibernateObjectModel(IModel idModel, Class objectClass,
			IHibernateSessionDelegate sessionDelegate)
	{
		this(idModel, objectClass, sessionDelegate, true);
	}

	/**
	 * Construct with an id.
	 * @param id the object's id; will be wrapped in a {@link Model}
	 * @param sessionDelegate delegate that provides instances of
	 *            {@link net.sf.hibernate.Session}.
	 * @param objectClass class of the Hibernate object to load.
	 */
	public HibernateObjectModel(Serializable id, Class objectClass,
			IHibernateSessionDelegate sessionDelegate)
	{
		this(id, objectClass, sessionDelegate, true);
	}

	/**
	 * Construct with a model that provides the id.
	 * @param idModel the model that provides the id
	 * @param sessionDelegate delegate that provides instances of
	 *            {@link net.sf.hibernate.Session}.
	 * @param objectClass class of the Hibernate object to load.
	 * @param createNewObjectWhenIdIsNull property for special, but very common, case for
	 *            handling null ids. If true, this model tries to construct a new (empty)
	 *            one, that can be used to receive input (set properties etc). If false,
	 *            null will be returned (and Ognl will probably throw an exception if no
	 *            further action is taken).
	 */
	public HibernateObjectModel(IModel idModel, Class objectClass,
			IHibernateSessionDelegate sessionDelegate, boolean createNewObjectWhenIdIsNull)
	{
		super(idModel, new HibernateSelectObjectAction(objectClass, sessionDelegate));
		checkConstructorFields(objectClass, sessionDelegate);
		this.objectClass = objectClass;
		this.createNewObjectWhenIdIsNull = createNewObjectWhenIdIsNull;
		this.nullIdHandler = new DefaultNullIdHandler();
	}

	/**
	 * Construct with an id.
	 * @param id the object's id; will be wrapped in a {@link Model}
	 * @param sessionDelegate delegate that provides instances of
	 *            {@link net.sf.hibernate.Session}.
	 * @param objectClass class of the Hibernate object to load.
	 * @param createNewObjectWhenIdIsNull property for special, but very common, case for
	 *            handling null ids. If true, this model tries to construct a new (empty)
	 *            one, that can be used to receive input (set properties etc). If false,
	 *            null will be returned (and Ognl will probably throw an exception if no
	 *            further action is taken).
	 */
	public HibernateObjectModel(Serializable id, Class objectClass,
			IHibernateSessionDelegate sessionDelegate, boolean createNewObjectWhenIdIsNull)
	{
		super(id, new HibernateSelectObjectAction(objectClass, sessionDelegate));
		checkConstructorFields(objectClass, sessionDelegate);
		this.objectClass = objectClass;
		this.createNewObjectWhenIdIsNull = createNewObjectWhenIdIsNull;
		this.nullIdHandler = new DefaultNullIdHandler();
	}

	/**
	 * Construct with a model that provides the id.
	 * @param idModel the model that provides the id
	 * @param sessionDelegate delegate that provides instances of
	 *            {@link net.sf.hibernate.Session}.
	 * @param objectClass class of the Hibernate object to load.
	 * @param nullIdHandler Handler that is called when the id is null
	 */
	public HibernateObjectModel(IModel idModel, Class objectClass,
			IHibernateSessionDelegate sessionDelegate, NullIdHandler nullIdHandler)
	{
		super(idModel, new HibernateSelectObjectAction(objectClass, sessionDelegate));
		checkConstructorFields(objectClass, sessionDelegate, nullIdHandler);
		this.objectClass = objectClass;
		this.createNewObjectWhenIdIsNull = false; // we use the prodived nullIdHandler
		this.nullIdHandler = nullIdHandler;
	}

	/**
	 * Construct with an id.
	 * @param id the object's id; will be wrapped in a {@link Model}
	 * @param sessionDelegate delegate that provides instances of
	 *            {@link net.sf.hibernate.Session}.
	 * @param objectClass class of the Hibernate object to load.
	 * @param nullIdHandler Handler that is called when the id is null
	 */
	public HibernateObjectModel(Serializable id, Class objectClass,
			IHibernateSessionDelegate sessionDelegate, NullIdHandler nullIdHandler)
	{
		super(id, new HibernateSelectObjectAction(objectClass, sessionDelegate));
		checkConstructorFields(objectClass, sessionDelegate, nullIdHandler);
		this.objectClass = objectClass;
		this.createNewObjectWhenIdIsNull = false; // we use the prodived nullIdHandler
		this.nullIdHandler = nullIdHandler;
	}

	/**
	 * Check whether the fields are not null.
	 * @param objectClass the object class
	 * @param sessionDelegate the session delegate
	 */
	private void checkConstructorFields(Class objectClass,
			IHibernateSessionDelegate sessionDelegate)
	{
		if (objectClass == null)
		{
			throw new NullPointerException("objectClass is not allowed to be null");
		}
		if (sessionDelegate == null)
		{
			throw new NullPointerException("sessionDelegate is not allowed to be null");
		}
	}

	/**
	 * Check whether the fields are not null.
	 * @param objectClass the object class
	 * @param sessionDelegate the session delegate
	 * @param nullIdHandler the null id handler
	 */
	private void checkConstructorFields(Class objectClass,
			IHibernateSessionDelegate sessionDelegate, NullIdHandler nullIdHandler)
	{
		checkConstructorFields(objectClass, sessionDelegate);
		if (nullIdHandler == null)
		{
			throw new NullPointerException("nullIdHandler is not allowed to be null");
		}
	}

	/**
	 * Loads an object using the SelectObjectAction and the id. If the id is null,
	 * the set nullIdHandler will be used to get an object.
	 * @see wicket.contrib.data.model.PersistentObjectModel#loadObject(java.io.Serializable)
	 */
	public Object loadObject(Serializable id)
	{
		if (id == null)
		{
			return nullIdHandler.getObjectForNullId();
		}
		else
		{
			return getSelectObjectAction().execute(id);
		}
	}

	/**
	 * Gets the class of the Hibernate object to load.
	 * @return the class of the Hibernate object to load
	 */
	public Class getObjectClass()
	{
		return objectClass;
	}

	/**
	 * The default handler; if property createNewObjectWhenIdIsNull of
	 * HibernateObjectModel is true, a new instance is created using it's default
	 * constructor and returned, if createNewObjectWhenIdIsNull is false, null is
	 * returned.
	 */
	private final class DefaultNullIdHandler implements NullIdHandler
	{
		/** keep reference for redirects etc. */
		private transient Object dummy;

		/**
		 * Gets the object that is to be used when the id is null.
		 * @return the object that is to be used when the id is null
		 */
		public Object getObjectForNullId()
		{
			if (createNewObjectWhenIdIsNull) // create a new one
			{
				if(dummy == null)
				{
					try
					{
						this.dummy = objectClass.newInstance();
					}
					catch (InstantiationException e)
					{
						throw new WicketRuntimeException(e);
					}
					catch (IllegalAccessException e)
					{
						throw new WicketRuntimeException(e);
					}
				}
				return dummy;
			}
			else
			// return null; fat chance this will result in an Ognl exception!
			{
				return null;
			}
		}
	}

	/**
	 * Handler that is called when the id is null.
	 */
	public interface NullIdHandler extends Serializable
	{
		/**
		 * Gets the object that is to be used when the id is null.
		 * @return the object that is to be used when the id is null
		 */
		Object getObjectForNullId();
	}
}
