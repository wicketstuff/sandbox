package wicket.contrib.data.model.hibernate;

import org.hibernate.Session;

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
	 * 
	 * @return a hibernate session
	 */
	protected abstract Session getSession();

	/**
	 * @see wicket.contrib.data.model.hibernate.IHibernateDao#execute(IHibernateCallback)
	 */
	public <T> T execute(IHibernateCallback<T> callback)
	{
		return callback.execute(getSession());
	}
}
