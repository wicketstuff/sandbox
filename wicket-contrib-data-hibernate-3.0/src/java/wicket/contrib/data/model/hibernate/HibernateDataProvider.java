package wicket.contrib.data.model.hibernate;

import java.util.Iterator;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

import wicket.contrib.data.model.hibernate.IHibernateDao.IHibernateCallback;
import wicket.extensions.markup.html.repeater.util.SortParam;
import wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import wicket.model.IModel;
import wicket.model.Model;

/**
 * A data provider for working with Hibernate 3.
 * 
 * @author Phil Kulak
 */
public abstract class HibernateDataProvider extends SortableDataProvider
{
	private IModel hibernateDao;

	private boolean unproxy = false;

	private boolean evict = false;

	public HibernateDataProvider(IHibernateDao hibernateDao)
	{
		this.hibernateDao = new Model(hibernateDao);
	}

	public HibernateDataProvider(IModel hibernateDao)
	{
		this.hibernateDao = hibernateDao;
	}

	public Iterator iterator(final int first, final int count)
	{
		return (Iterator) getHibernateDao().execute(new IHibernateCallback()
		{
			public Object execute(Session session)
			{
				return iterator(first, count, session);
			}
		});
	}

	public IModel model(Object object)
	{
		HibernateModel ret = new HibernateModel(object, hibernateDao, unproxy);
		ret.setEvict(evict);
		return ret;
	}

	/**
	 * Set to true to have all models evicted from the session before they are
	 * returned.
	 */
	public void setEvict(boolean evict)
	{
		this.evict = evict;
	}

	/**
	 * Set to true to have all models unproxied.
	 */
	public void setUnproxy(boolean unproxy)
	{
		this.unproxy = unproxy;
	}

	public int size()
	{
		Integer result = (Integer) getHibernateDao().execute(new IHibernateCallback()
		{
			public Object execute(Session session)
			{
				return size(session);
			}
		});

		return result.intValue();
	}

	private IHibernateDao getHibernateDao()
	{
		return (IHibernateDao) hibernateDao.getObject();
	}

	private Order makeOrder(SortParam param)
	{
		return param.isAscending() ? Order.asc(param.getProperty()) : Order.desc(param
				.getProperty());
	}

	/**
	 * Adds all the current orderings to the criteria.
	 * 
	 * @param criteria
	 *            the criteria to add the orderings to
	 * @return the criteria with the added orderings
	 */
	protected final Criteria addOrdering(Criteria criteria)
	{
		SortParam sp = getSort();
		if (sp != null)
		{
			criteria.addOrder(makeOrder(sp));
		}
		return criteria;
	}

	/**
	 * Formats the current sort states an a query string suitable for appending
	 * at the end of SQL, HQL or other queries.
	 * 
	 * @return an ORDER BY query fragment
	 */
	protected String getSqlOrderBy()
	{
		return getSqlOrderBy(null);
	}

	/**
	 * Formats the current sort states an a query string suitable for appending
	 * at the end of SQL, HQL or other queries.
	 * 
	 * @param alias
	 *            the column alias each sort field will reference
	 * @return an ORDER BY query fragment
	 */
	protected String getSqlOrderBy(String alias)
	{
		StringBuffer orderBy = new StringBuffer();

		SortParam sp = getSort();
		if (sp != null)
		{
			orderBy.append(" ORDER BY ");
			if (alias != null)
			{
				orderBy.append(alias + ".");
			}
			orderBy.append(sp.getProperty());
			orderBy.append(sp.isAscending() ? " ASC" : " DESC");

		}

		return orderBy.toString();
	}

	protected abstract Iterator iterator(int first, int count, Session session);

	protected abstract Object size(Session session);
}
