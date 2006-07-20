package wicket.contrib.data.model.hibernate;

import java.util.List;

import org.hibernate.Session;

import wicket.contrib.data.model.QueryList;
import wicket.contrib.data.model.hibernate.IHibernateDao.IHibernateCallback;

/**
 * A list class that can be instantiated using HQL.
 * 
 * @author Phil Kulak
 */
public class HibernateQueryList<E> extends QueryList<E>
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String listQuery;

	private String countQuery;

	private boolean useQueryCache = true;

	private IHibernateDao dao;

	/**
	 * Creates a new list using the Hibernate query cache.
	 * 
	 * @param listQuery
	 *            a string query that will return the full list of items
	 * @param countQuery
	 *            a string query that will return a single integer for the total
	 *            number of items
	 * @param dao
	 *            the Hibernate dao
	 */
	public HibernateQueryList(String listQuery, String countQuery, IHibernateDao dao)
	{
		this.listQuery = listQuery;
		this.countQuery = countQuery;
		this.dao = dao;
	}

	/**
	 * Creates a new list by extrapolating the count query from the list query.
	 * This simply creates a select clause ("SELECT COUNT(*) ") and appends it
	 * to the front of the list query or replaces the current select clause if
	 * it exists. This is apropriate for most queries.
	 * 
	 * @param listQuery
	 *            a string query that will return the full list of items
	 * @param dao
	 *            the Hibernate dao
	 */
	public HibernateQueryList(String listQuery, IHibernateDao dao)
	{
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
		this.dao = dao;
	}

	/**
	 * Creates a new list.
	 * 
	 * @param listQuery
	 *            a string query that will return the full list of items
	 * @param countQuery
	 *            a string query that will return a single integer for the total
	 *            number of items
	 * @param dao
	 *            the Hibernate dao
	 * @param useQueryCache
	 *            use the query cache for each query?
	 */
	public HibernateQueryList(String listQuery, String countQuery, IHibernateDao dao,
			boolean useQueryCache)
	{
		this(listQuery, countQuery, dao);
		this.useQueryCache = useQueryCache;
	}

	@Override
	@SuppressWarnings("unchecked")
	protected List<E> getItems(final int start, final int max, final String orderBy)
	{
		return dao.execute(new IHibernateCallback<List<E>>()
		{
			public List<E> execute(Session session)
			{
				return session.createQuery(listQuery + orderBy).setFirstResult(start)
						.setMaxResults(max).setCacheable(useQueryCache).list();
			}
		});
	}

	@Override
	@SuppressWarnings("unchecked")
	protected List<E> getAllItems(final String orderBy)
	{
		return dao.execute(new IHibernateCallback<List<E>>()
		{
			public List<E> execute(Session session)
			{
				return session.createQuery(listQuery + orderBy).setCacheable(
						useQueryCache).list();
			}
		});
	}

	@Override
	protected int getCount()
	{
		return (dao.execute(new IHibernateCallback<Integer>()
		{
			public Integer execute(Session session)
			{
				return (Integer) session.createQuery(countQuery).setCacheable(
						useQueryCache).uniqueResult();
			}
		})).intValue();
	}
}
