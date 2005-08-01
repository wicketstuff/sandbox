package wicket.contrib.data.model.hibernate;

import java.util.Iterator;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;

import wicket.contrib.dataview.providers.SortParam;

public abstract class CriteriaDataProvider extends HibernateDataProvider
{

	public CriteriaDataProvider(IHibernateDao hibernateDao)
	{
		super(hibernateDao);
	}

	protected Iterator iterator(int first, int count, Session session)
	{
		return addOrdering(list(session)).setFirstResult(first).setMaxResults(count)
				.list().iterator();
	}

	protected Object size(Session session)
	{
		return count(session).setProjection(Projections.rowCount()).uniqueResult();
	}

	protected abstract Criteria list(Session session);

	protected abstract Criteria count(Session session);

	private Criteria addOrdering(Criteria criteria)
	{
		for (Iterator i = getSortList().iterator(); i.hasNext();)
		{
			criteria.addOrder(makeOrder((SortParam) i.next()));
		}
		return criteria;
	}

	private Order makeOrder(SortParam param)
	{
		return param.isAscending() ? Order.asc(param.getProperty()) : Order.desc(param
				.getProperty());
	}
}
