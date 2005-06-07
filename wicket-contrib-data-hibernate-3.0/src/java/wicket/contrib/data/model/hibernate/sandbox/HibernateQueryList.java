package wicket.contrib.data.model.hibernate.sandbox;

import java.util.List;

import org.hibernate.SessionFactory;

import wicket.contrib.data.model.hibernate.IHibernateSessionDelegate;
import wicket.contrib.data.model.sandbox.QueryList;

/**
 * A list class that can be instantiated using HQL.
 * 
 * @author Phil Kulak
 */
public class HibernateQueryList extends QueryList
{
	private IHibernateSessionDelegate sessionDelegate;
	private String listQuery;
	private String countQuery;
	
	/**
	 * Creates a new list.
	 * 
	 * @param sessionDelegate the session source for the list
	 * @param listQuery a string query that will return the full list of items
	 * @param countQuery a string query that will return a single integer
	 *                   for the total number of items
	 */
	public HibernateQueryList(IHibernateSessionDelegate sessionDelegate, 
			String listQuery, String countQuery)
	{
		this.sessionDelegate = sessionDelegate;
		this.listQuery = listQuery;
		this.countQuery = countQuery;
	}

	/**
	 * Creates a new list.
	 * 
	 * @param sessionFactory a factory to be wrapped in a delegate
	 * @param listQuery a string query that will return the full list of items
	 * @param countQuery a string query that will return a single integer
	 *                   for the total number of items
	 */
	public HibernateQueryList(SessionFactory sessionFactory, 
			String listQuery, String countQuery)
	{
		this(new HibernateSessionDelegate(sessionFactory), listQuery, countQuery);
	}

	protected List getItems(int start, int max, String orderBy)
	{
		return sessionDelegate.getSession()
				.createQuery(listQuery + orderBy)
				.setFirstResult(start)
				.setMaxResults(max)
				.list();
	}

	protected int getCount()
	{
		return ((Integer) sessionDelegate.getSession()
				.createQuery(countQuery)
				.uniqueResult())
				.intValue();
	}
}
