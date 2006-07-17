package wicket.contrib.data.model.hibernate;

import java.io.Serializable;

import org.hibernate.Session;

/**
 * An interface to support interaction with Hibernate.
 * 
 * @author Phil Kulak
 */
public interface IHibernateDao extends Serializable
{
	/**
	 * Executes the callback and returns the result.
	 * 
	 * @param callback
	 *            the callback to execute
	 * @return the object returned by HibernateCallback.execute
	 */
	public <T> T execute(IHibernateCallback<T> callback);

	/**
	 * Passed into the execute method of IHibernateDao.
	 */
	public interface IHibernateCallback<E>
	{
		/**
		 * Uses the provided session to perform and reading from or writing to
		 * the database.
		 * 
		 * @param session
		 * @return the result of any queries executed
		 */
		public E execute(Session session);
	}
}
