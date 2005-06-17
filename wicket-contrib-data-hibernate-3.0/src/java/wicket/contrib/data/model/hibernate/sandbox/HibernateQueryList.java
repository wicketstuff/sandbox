package wicket.contrib.data.model.hibernate.sandbox;

import java.util.List;

import org.hibernate.Session;

import wicket.contrib.data.model.sandbox.QueryList;

/**
 * A list class that can be instantiated using HQL.
 * 
 * @author Phil Kulak
 */
public abstract class HibernateQueryList extends QueryList
{
	private String listQuery;
	private String countQuery;
	private boolean useQueryCache;
	
	/**
	 * Creates a new list.
	 * 
	 * @param listQuery a string query that will return the full list of items
	 * @param countQuery a string query that will return a single integer
	 *                   for the total number of items
	 * @param useQueryCache use the query cache for each query?
	 */
	public HibernateQueryList(String listQuery, String countQuery, 
			boolean useQueryCache)
	{
		this.listQuery = listQuery;
		this.countQuery = countQuery;
		this.useQueryCache = useQueryCache;
	}

	protected List getItems(int start, int max, String orderBy)
	{
		return getSession()
				.createQuery(listQuery + orderBy)
				.setFirstResult(start)
				.setMaxResults(max)
				.setCacheable(useQueryCache)
				.list();
	}

	protected int getCount()
	{
		return ((Integer) getSession()
				.createQuery(countQuery)
				.setCacheable(useQueryCache)
				.uniqueResult())
				.intValue();
	}
	
	/**
	 * Override this to provide a session that queries can be run against.
	 * 
	 * @return a Hibernate session
	 */
	protected abstract Session getSession();
}
