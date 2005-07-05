package wicket.contrib.data.model.hibernate.sandbox;

import java.util.List;

import wicket.contrib.data.model.hibernate.IHibernateSessionDelegate;
import wicket.contrib.data.model.sandbox.QueryList;

/**
 * A list class that can be instantiated using HQL. If the session delegate you
 * use implements IHibernateDaoDelegate, this list will support remove
 * operations.
 * 
 * @author Phil Kulak
 */
public class HibernateQueryList extends QueryList
{
	private String listQuery;
	private String countQuery;
	private boolean useQueryCache = true;
    private IHibernateSessionDelegate sessionDelegate;
    
	/**
	 * Creates a new list using the Hibernate query cache.
	 * 
	 * @param listQuery a string query that will return the full list of items
	 * @param countQuery a string query that will return a single integer
	 *                   for the total number of items
	 * @param sessionDelegate the session delegate
	 */
	public HibernateQueryList(String listQuery, String countQuery, 
            IHibernateSessionDelegate sessionDelegate)
	{
		this.listQuery = listQuery;
		this.countQuery = countQuery;
		this.sessionDelegate = sessionDelegate;
	}
	
	/**
	 * Creates a new list by extrapolating the count query from the list query. This
	 * simply creates a select clause ("SELECT COUNT(*) ") and appends it to the 
	 * from of the list query or replaces the current select clause if it exists. This
	 * is apropriate for most queries. 
	 * 
	 * @param listQuery a string query that will return the full list of items
	 * @param sessionDelegate the session delegate 	 
	 */
	public HibernateQueryList(String listQuery, IHibernateSessionDelegate sessionDelegate) {
		String lowerListQuery = listQuery.toLowerCase();
		String trimmedListQuery = listQuery;
		
		// Remove the select clause if needed.
		if (lowerListQuery.trim().startsWith("select"))
		{
			int fromIndex = lowerListQuery.indexOf("from");
			if (fromIndex == -1)
			{
				throw new IllegalArgumentException("Invalid HQL: " + listQuery);
			}
			trimmedListQuery = listQuery.substring(fromIndex);
		}
		
		this.listQuery = listQuery;
		this.countQuery = "SELECT COUNT(*) " + trimmedListQuery;
		this.sessionDelegate = sessionDelegate;
	}
	
	/**
	 * Creates a new list.
	 * 
	 * @param listQuery a string query that will return the full list of items
	 * @param countQuery a string query that will return a single integer
	 *                   for the total number of items
	 * @param sessionDelegate the session delegate
	 * @param useQueryCache use the query cache for each query?
	 */
	public HibernateQueryList(String listQuery, String countQuery, 
            IHibernateSessionDelegate sessionDelegate, boolean useQueryCache)
	{
        this(listQuery, countQuery, sessionDelegate);
        this.useQueryCache = useQueryCache;
	}

	protected List getItems(int start, int max, String orderBy)
	{
		return sessionDelegate.getSession()
				.createQuery(listQuery + orderBy)
				.setFirstResult(start)
				.setMaxResults(max)
				.setCacheable(useQueryCache)
				.list();
	}

	protected int getCount()
	{
		return ((Integer) sessionDelegate.getSession()
				.createQuery(countQuery)
				.setCacheable(useQueryCache)
				.uniqueResult())
				.intValue();
	}
}
