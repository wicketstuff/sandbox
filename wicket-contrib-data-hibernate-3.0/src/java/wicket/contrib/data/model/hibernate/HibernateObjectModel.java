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

import org.hibernate.proxy.HibernateProxy;
import org.hibernate.proxy.LazyInitializer;

import wicket.WicketRuntimeException;
import wicket.contrib.data.model.PersistentObjectModel;
import wicket.model.IModel;
import wicket.model.Model;

/**
 * Model for one Hibernate Object. It will load the object with the session that
 * is provided by the given instance of
 * {@link wicket.contrib.data.model.hibernate.IHibernateSessionDelegate}.
 * 
 * @author Eelco Hillenius
 */
public class HibernateObjectModel<T, V> extends PersistentObjectModel<T, V>
{
	/** whether to 'unproxy' the objects after loading. */
	private boolean unproxy = false;

	/** class of the Hibernate object to load. */
	private final Class<T> objectClass;

	/**
	 * property for special, but very common, case for handling null ids. If
	 * true, this model tries to construct a new (empty) one, that can be used
	 * to receive input (set properties etc). If false, null will be returned
	 * (and Ognl will probably throw an exception if no further action is
	 * taken).
	 */
	private final boolean createNewObjectWhenIdIsNull;

	/** Handler that is called when the id is null. */
	private INullIdHandler<T> nullIdHandler;

	/**
	 * Construct with a model that provides the id.
	 * 
	 * @param idModel
	 *            the model that provides the id
	 * @param sessionDelegate
	 *            delegate that provides instances of
	 *            {@link org.hibernate.Session}.
	 * @param objectClass
	 *            class of the Hibernate object to load.
	 */
	public HibernateObjectModel(IModel<V> idModel, Class<T> objectClass,
			IHibernateSessionDelegate sessionDelegate)
	{
		this(idModel, objectClass, sessionDelegate, true);
	}

	/**
	 * Construct with an id.
	 * 
	 * @param id
	 *            the object's id; will be wrapped in a {@link Model}
	 * @param sessionDelegate
	 *            delegate that provides instances of
	 *            {@link org.hibernate.Session}.
	 * @param objectClass
	 *            class of the Hibernate object to load.
	 */
	public HibernateObjectModel(V id, Class<T> objectClass,
			IHibernateSessionDelegate sessionDelegate)
	{
		this(id, objectClass, sessionDelegate, true);
	}

	/**
	 * Construct with a model that provides the id.
	 * 
	 * @param idModel
	 *            the model that provides the id
	 * @param sessionDelegate
	 *            delegate that provides instances of
	 *            {@link org.hibernate.Session}.
	 * @param objectClass
	 *            class of the Hibernate object to load.
	 * @param createNewObjectWhenIdIsNull
	 *            property for special, but very common, case for handling null
	 *            ids. If true, this model tries to construct a new (empty) one,
	 *            that can be used to receive input (set properties etc). If
	 *            false, null will be returned (and Ognl will probably throw an
	 *            exception if no further action is taken).
	 */
	public HibernateObjectModel(IModel<V> idModel, Class<T> objectClass,
			IHibernateSessionDelegate sessionDelegate, boolean createNewObjectWhenIdIsNull)
	{
		super(idModel,
				new HibernateSelectObjectAction<T, V>(objectClass, sessionDelegate));
		checkConstructorFields(objectClass, sessionDelegate);
		this.objectClass = objectClass;
		this.createNewObjectWhenIdIsNull = createNewObjectWhenIdIsNull;
		this.nullIdHandler = new DefaultNullIdHandler();
	}

	/**
	 * Construct with an id.
	 * 
	 * @param id
	 *            the object's id; will be wrapped in a {@link Model}
	 * @param sessionDelegate
	 *            delegate that provides instances of
	 *            {@link org.hibernate.Session}.
	 * @param objectClass
	 *            class of the Hibernate object to load.
	 * @param createNewObjectWhenIdIsNull
	 *            property for special, but very common, case for handling null
	 *            ids. If true, this model tries to construct a new (empty) one,
	 *            that can be used to receive input (set properties etc). If
	 *            false, null will be returned (and Ognl will probably throw an
	 *            exception if no further action is taken).
	 */
	public HibernateObjectModel(V id, Class<T> objectClass,
			IHibernateSessionDelegate sessionDelegate, boolean createNewObjectWhenIdIsNull)
	{
		super(id, new HibernateSelectObjectAction<T, V>(objectClass, sessionDelegate));
		checkConstructorFields(objectClass, sessionDelegate);
		this.objectClass = objectClass;
		this.createNewObjectWhenIdIsNull = createNewObjectWhenIdIsNull;
		this.nullIdHandler = new DefaultNullIdHandler();
	}

	/**
	 * Construct with a model that provides the id.
	 * 
	 * @param idModel
	 *            the model that provides the id
	 * @param sessionDelegate
	 *            delegate that provides instances of
	 *            {@link org.hibernate.Session}.
	 * @param objectClass
	 *            class of the Hibernate object to load.
	 * @param nullIdHandler
	 *            Handler that is called when the id is null
	 */
	public HibernateObjectModel(IModel<V> idModel, Class<T> objectClass,
			IHibernateSessionDelegate sessionDelegate, INullIdHandler<T> nullIdHandler)
	{
		super(idModel,
				new HibernateSelectObjectAction<T, V>(objectClass, sessionDelegate));
		checkConstructorFields(objectClass, sessionDelegate, nullIdHandler);
		this.objectClass = objectClass;
		this.createNewObjectWhenIdIsNull = false; // we use the prodived
		// nullIdHandler
		this.nullIdHandler = nullIdHandler;
	}

	/**
	 * Construct with an id.
	 * 
	 * @param id
	 *            the object's id; will be wrapped in a {@link Model}
	 * @param sessionDelegate
	 *            delegate that provides instances of
	 *            {@link org.hibernate.Session}.
	 * @param objectClass
	 *            class of the Hibernate object to load.
	 * @param nullIdHandler
	 *            Handler that is called when the id is null
	 */
	public HibernateObjectModel(V id, Class<T> objectClass,
			IHibernateSessionDelegate sessionDelegate, INullIdHandler<T> nullIdHandler)
	{
		super(id, new HibernateSelectObjectAction<T, V>(objectClass, sessionDelegate));
		checkConstructorFields(objectClass, sessionDelegate, nullIdHandler);
		this.objectClass = objectClass;
		this.createNewObjectWhenIdIsNull = false; // we use the prodived
		// nullIdHandler
		this.nullIdHandler = nullIdHandler;
	}

	/**
	 * Check whether the fields are not null.
	 * 
	 * @param objectClass
	 *            the object class
	 * @param sessionDelegate
	 *            the session delegate
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
	 * 
	 * @param objectClass
	 *            the object class
	 * @param sessionDelegate
	 *            the session delegate
	 * @param nullIdHandler
	 *            the null id handler
	 */
	private void checkConstructorFields(Class objectClass,
			IHibernateSessionDelegate sessionDelegate, INullIdHandler nullIdHandler)
	{
		checkConstructorFields(objectClass, sessionDelegate);
		if (nullIdHandler == null)
		{
			throw new NullPointerException("nullIdHandler is not allowed to be null");
		}
	}

	/**
	 * Loads an object using the SelectObjectAction and the id. If the id is
	 * null, the set nullIdHandler will be used to get an object.
	 * 
	 * @see PersistentObjectModel#loadObject(java.io.Serializable)
	 */
	@SuppressWarnings("unchecked")
	public T loadObject(V id)
	{
		if (id == null)
		{
			return getNullIdHandler().getObjectForNullId();
		}
		else
		{
			T loaded = getSelectObjectAction().execute(id);

			if (getUnproxy() && loaded instanceof HibernateProxy)
			{
				HibernateProxy proxy = (HibernateProxy) loaded;
				LazyInitializer lazyInitializer = proxy.getHibernateLazyInitializer();
				T implementation = (T)lazyInitializer.getImplementation();
				return implementation;
			}

			return loaded;
		}
	}

	/**
	 * Gets the class of the Hibernate object to load.
	 * 
	 * @return the class of the Hibernate object to load
	 */
	public Class getObjectClass()
	{
		return objectClass;
	}

	/**
	 * Gets the handler that is called when the id is null.
	 * 
	 * @return the handler that is called when the id is null
	 */
	public INullIdHandler<T> getNullIdHandler()
	{
		return nullIdHandler;
	}

	/**
	 * Sets the handler that is called when the id is null.
	 * 
	 * @param nullIdHandler
	 *            the handler that is called when the id is null
	 */
	public void setNullIdHandler(INullIdHandler<T> nullIdHandler)
	{
		this.nullIdHandler = nullIdHandler;
	}

	/**
	 * Gets whether the loaded object should be unproxied.
	 * 
	 * @return whether the loaded object should be unproxied
	 */
	public boolean getUnproxy()
	{
		return unproxy;
	}

	/**
	 * Sets whether the loaded object should be unproxied.
	 * 
	 * @param unproxy
	 *            whether the loaded object should be unproxied
	 * @return This
	 */
	public HibernateObjectModel setUnproxy(boolean unproxy)
	{
		this.unproxy = unproxy;
		return this;
	}

	/**
	 * The default handler; if property createNewObjectWhenIdIsNull of
	 * HibernateObjectModel is true, a new instance is created using it's
	 * default constructor and returned, if createNewObjectWhenIdIsNull is
	 * false, null is returned.
	 */
	private final class DefaultNullIdHandler extends NullIdHandler<T>
	{

		/**
		 * Gets the object that is to be used when the id is null.
		 * 
		 * @return the object that is to be used when the id is null
		 */
		@SuppressWarnings("unchecked")
		public T doGetObjectForNullId()
		{
			if (createNewObjectWhenIdIsNull)
			{
				try
				{
					if (!Serializable.class.isAssignableFrom(objectClass))
					{
						throw new WicketRuntimeException(
								"only serializable classes can be used with this strategy");
					}
					return (T) objectClass.newInstance();
				}
				catch (InstantiationException e)
				{
					throw new WicketRuntimeException(
							"unable to create an object of type " + objectClass + ": ", e);
				}
				catch (IllegalAccessException e)
				{
					throw new WicketRuntimeException(e);
				}
			}
			else
			{
				return null;
			}
		}
	}

	/**
	 * Handler that is called when the id is null.
	 */
	public static interface INullIdHandler<T> extends Serializable
	{
		/**
		 * Gets the object that is to be used when the id is null.
		 * 
		 * @return the object that is to be used when the id is null
		 */
		T getObjectForNullId();
	}

	/**
	 * Abstract implementation that caches the transient object once it is
	 * created.
	 */
	public static abstract class NullIdHandler<T> implements INullIdHandler<T>
	{

		/** the cached object. */
		private T object;

		/**
		 * @see wicket.contrib.data.model.hibernate.HibernateObjectModel.INullIdHandler#getObjectForNullId()
		 */
		public final T getObjectForNullId()
		{
			if (object == null)
			{
				object = doGetObjectForNullId();
			}
			return object;
		}

		/**
		 * Gets the object that is to be used when the id is null. This will be
		 * called whenever there is no cached object.
		 * 
		 * @return the object that is to be used when the id is null
		 */
		protected abstract T doGetObjectForNullId();
	}
}