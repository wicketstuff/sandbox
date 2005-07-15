package wicket.contrib.data.model.hibernate.sandbox;

import java.io.Serializable;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

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
	 * @param callback the callback to execute
	 * @return the object returned by HibernateCallback.execute
	 */
	public Object execute(IHibernateCallback callback);
	
	/**
	 * This method is NOT used to get a session. Any reading or writing to
	 * the database is handled through execute so that individual
	 * implementations can handle all the session and exception handling.
	 * 
	 * @return a session factory
	 */
	public SessionFactory getSessionFactory();
	
	/**
	 * Passed into the execute method of IHibernateDao.
	 */
	public interface IHibernateCallback
	{
		/**
		 * Uses the provided session to perform and reading from or writing to
		 * the database.
		 * 
		 * @param session
		 * @return the result of any queries executed
		 */
		public Object execute(Session session);
	}
}
