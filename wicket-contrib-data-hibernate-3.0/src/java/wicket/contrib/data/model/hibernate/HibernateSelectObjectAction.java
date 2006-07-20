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
import wicket.contrib.data.model.ISelectObjectAction;

/**
 * Loads Hibernate objects based on the given object class using the given
 * session delegate.
 * 
 * @author Eelco Hillenius
 */
public final class HibernateSelectObjectAction<T, V> implements ISelectObjectAction<T, V>
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** delegate that provides instances of {@link org.hibernate.Session}. */
	private final IHibernateSessionDelegate sessionDelegate;

	/** class of the Hibernate object to load. */
	private final Class<T> objectClass;

	/**
	 * Construct.
	 * 
	 * @param objectClass
	 *            class of the Hibernate object to load
	 * @param sessionDelegate
	 *            delegate that provides instances of
	 *            {@link org.hibernate.Session}.
	 */
	public HibernateSelectObjectAction(Class<T> objectClass,
			IHibernateSessionDelegate sessionDelegate)
	{
		this.sessionDelegate = sessionDelegate;
		this.objectClass = objectClass;
	}

	/**
	 * @see wicket.contrib.data.model.ISelectObjectAction#execute(java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	public T execute(V queryObject)
	{
		if (!(queryObject instanceof Serializable))
		{
			throw new IllegalArgumentException(
					"queryObject must be the (serializable) id of the object");
		}
		return (T) sessionDelegate.getSession().load(objectClass,
				(Serializable) queryObject);
	}

	/**
	 * Exception that is thrown when an object is not found in the database.
	 */
	private static final class ObjectNotFoundException extends WicketRuntimeException
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * Construct.
		 */
		public ObjectNotFoundException()
		{
			super();
		}

		/**
		 * Construct.
		 * 
		 * @param message
		 * @param cause
		 */
		public ObjectNotFoundException(String message, Throwable cause)
		{
			super(message, cause);
		}

		/**
		 * Construct.
		 * 
		 * @param message
		 */
		public ObjectNotFoundException(String message)
		{
			super(message);
		}

		/**
		 * Construct.
		 * 
		 * @param cause
		 */
		public ObjectNotFoundException(Throwable cause)
		{
			super(cause);
		}
	}
}
