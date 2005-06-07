package wicket.contrib.data.model.hibernate.sandbox;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;

import wicket.contrib.data.model.sandbox.OrderedPageableList;
import wicket.contrib.data.model.sandbox.ListOrder;

import wicket.contrib.data.model.hibernate.IHibernateSessionDelegate;

/**
 * A scrolling list that builds on a DetatchedCriteria query provided by a base
 * class.  
 * 
 * @author Phil Kulak
 */
public abstract class HibernateCriteriaList extends OrderedPageableList
{
	private IHibernateSessionDelegate sessionDelegate;
	
	/**
	 * Creates a HibernateCriteriaList using the session delegate.
	 * 
	 * @param sessionDelegate the session source
	 */
	public HibernateCriteriaList(IHibernateSessionDelegate sessionDelegate)
	{
		this.sessionDelegate = sessionDelegate;
	}
	
	/**
	 * Creates a HibernateCriteriaList using the session factory.
	 * 
	 * @param factory used to create a session delegate
	 */
	public HibernateCriteriaList(SessionFactory factory)
	{
		this.sessionDelegate = new HibernateSessionDelegate(factory);
	}
	
	/**
	 * @see wicket.contrib.data.model.sandbox.OrderedPageableList#getCount()
	 */
	public int getCount()
	{
		return ((Integer) getBaseCriteria(sessionDelegate.getSession())
				.setProjection(Projections.rowCount())
				.uniqueResult()).intValue();
	}

	/**
	 * @see wicket.contrib.data.model.sandbox.OrderedPageableList#getItems(int, int, List)
	 */
	public List getItems(int start, int max, List ordering)
	{
		return getOrderedCriteria(sessionDelegate.getSession(), ordering)
				.setFirstResult(start)
				.setMaxResults(max).list();
	}

	/**
	 * Override this to return a criteria query with no agregation or ordering
	 * and all parameters set.
	 * 
	 * @param session the session to use to build the criteria
	 * @return a base query for the list
	 */
	protected abstract Criteria getBaseCriteria(Session session);

	/**
	 * Adds the orderings to the base query.
	 */
	private Criteria getOrderedCriteria(Session session, List ordering)
	{
		Criteria ret = getBaseCriteria(session);

		for (Iterator i = ordering.iterator(); i.hasNext();)
		{
			ListOrder order = (ListOrder) i.next();
			ret.addOrder(makeHibernateOrder(order));
		}
		return ret;
	}
	
	/**
	 * Uses a ListOrder to create a Hibernate Order object.
	 */
	private Order makeHibernateOrder(ListOrder order)
	{
		if (order.isAscending())
		{
			return Order.asc(order.getField());
		}
		return Order.desc(order.getField());
	}
}