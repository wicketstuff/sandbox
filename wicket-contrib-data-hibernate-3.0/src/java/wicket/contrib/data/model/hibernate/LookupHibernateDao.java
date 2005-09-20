package wicket.contrib.data.model.hibernate;

/**
 * Use this class if your IHibernateDao implementation cannot be serialized.
 * For example, if you keep in in the Spring Application Context. This way
 * you can look it up when it's needed.
 * 
 * @author Phil Kulak
 */
public abstract class LookupHibernateDao implements IHibernateDao
{
	public Object execute(IHibernateCallback callback)
	{
		return getHibernateDao().execute(callback);
	}
	
	public abstract IHibernateDao getHibernateDao();
}
