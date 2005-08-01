package wicket.contrib.data.model.hibernate;

import java.util.Iterator;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;

import wicket.contrib.dataview.providers.SortParam;

/**
 * A {@Link wicket.contrib.dataview.IDataProvider} with support for criteria
 * queries. 
 * 
 * @author Phil Kulak
 */
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
	
	/**
	 * Returns a criteria query for a list of the objects. Do not set first or 
	 * max results or any or the orderings, that will be taken care of later.
	 * 
	 * @param session
	 * @return a list of objects
	 */
	protected abstract Criteria list(Session session);

	/**
	 * Returns a criteria query with as many rows as are present when 
	 * {@Link #list(Session)} is called. Do not set any projections, that will
	 * be taken care of later.
	 * 
	 * @param session
	 * @return a list of objects
	 */
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
