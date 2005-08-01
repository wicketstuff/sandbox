package wicket.contrib.data.model.hibernate;

import java.util.Iterator;

import org.hibernate.Session;

import wicket.contrib.data.model.hibernate.IHibernateDao.IHibernateCallback;
import wicket.contrib.dataview.providers.SortableDataProvider;
import wicket.model.IModel;

/**
 * A data provider for working with Hibernate 3.
 * 
 * @author Phil Kulak
 */
public abstract class HibernateDataProvider extends SortableDataProvider
{
	IHibernateDao hibernateDao;

	public HibernateDataProvider(IHibernateDao hibernateDao)
	{
		this.hibernateDao = hibernateDao;
	}

	public IModel model(Object object)
	{
		return new HibernateModel(object, hibernateDao);
	}

	public String primaryKey(Object object)
	{
		return ((HibernateModel) model(object)).getId().toString();
	}

	public Iterator iterator(final int first, final int count)
	{
		return (Iterator) hibernateDao.execute(new IHibernateCallback()
		{
			public Object execute(Session session)
			{
				return iterator(first, count, session);
			}
		});
	}

	public int size()
	{
		Integer result = (Integer) hibernateDao.execute(new IHibernateCallback()
		{
			public Object execute(Session session)
			{
				return size(session);
			}
		});

		return result.intValue();
	}

	protected abstract Iterator iterator(int first, int count, Session session);

	protected abstract Object size(Session session);
}
