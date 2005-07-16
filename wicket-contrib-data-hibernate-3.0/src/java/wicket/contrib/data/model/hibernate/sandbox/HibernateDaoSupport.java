package wicket.contrib.data.model.hibernate.sandbox;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * A support class if you don't want to worry about handling execute callbacks
 * yourself and would prefer to merely supply the session.
 * 
 * @author Phil Kulak
 */
public abstract class HibernateDaoSupport implements IHibernateDao
{
	/**
	 * Returns a session that will be passed to any hibernate callbacks.
	 * @return a hibernate session
	 */
	public abstract Session getSession();
	
	/**
	 * @see wicket.contrib.data.model.hibernate.sandbox.IHibernateDao#execute(IHibernateCallback)
	 */
	public Object execute(IHibernateCallback callback)
	{
		return callback.execute(getSession());
	}

	/**
	 * @see wicket.contrib.data.model.hibernate.sandbox.IHibernateDao#getSessionFactory()
	 */
	public SessionFactory getSessionFactory()
	{
		return getSession().getSessionFactory();
	}
}
