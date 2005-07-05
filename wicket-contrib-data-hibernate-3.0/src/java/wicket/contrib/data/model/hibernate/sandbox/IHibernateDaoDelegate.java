package wicket.contrib.data.model.hibernate.sandbox;

import wicket.contrib.data.model.hibernate.IHibernateSessionDelegate;

/**
 * Used for write operations.
 */
public interface IHibernateDaoDelegate extends IHibernateSessionDelegate
{
	/**
	 * @see org.hibernate.Session#delete(java.lang.Object)
	 * @param entity the object to delete
	 */
	public void delete(Object entity);
}
