/*
 * Created on May 18, 2005
 */
package net.sf.ipn.app.component;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.objectstyle.cayenne.exp.Expression;
import org.objectstyle.cayenne.exp.ExpressionFactory;
import org.objectstyle.cayenne.query.Ordering;
import org.objectstyle.cayenne.query.SelectQuery;

/**
 * @author Jonathan Carlson Provides a way for a Cayenne query to be dynamically
 *         "specialized". In other words, filtered and ordered (sorted) via UI actions.
 *         <p>
 *         This query builder allows Cayenne to do all the data filtering and ordering,
 *         which requires more trips to the database, but limits memory usage if query
 *         paging is used. Any qualifiers on the Query built by the buildBaseQuery()
 *         method will be preserved as part of the unfiltered Query. (For example, you
 *         want the "unfiltered" query to show only the current user's email addresses,
 *         not all users e-mail addresses)
 *         <p>
 *         The UI page (or panel or border) adds QueryFilters and QueryOrderings (public
 *         inner interfaces of this class) to myself. As links are clicked or dropdowns
 *         are changed these QueryFilters and QueryOrders are to be updated (your
 *         responsibility to do this) to return a Cayenne Expression, a Cayenne Ordering
 *         or null if nothing is to be done with this QueryFilter or QueryOrdering.
 */
public abstract class CayenneQueryBuilder
{
	private transient SelectQuery query;
	private transient Expression baseQualifier;

	private List filters = new LinkedList();
	private LinkedList orderings = new LinkedList();

	/**
	 * @param dataCtx - a Cayenne DataContext instance
	 * @param query - a base Cayenne SelectQuery which can be specialized by the UI
	 */
	public CayenneQueryBuilder()
	{
	}

	/**
	 * Subclasses must implement this method to use this builder.
	 * @return the base Cayenne SelectQuery which may be further specialized
	 */
	public abstract SelectQuery buildBaseQuery();

	public SelectQuery getQuery()
	{
		if (this.query == null)
		{
			this.query = buildBaseQuery();
			this.baseQualifier = this.query.getQualifier();
		}
		filterQuery();
		orderQuery();
		return this.query;
	}

	public void addFilter(QueryFilter filter)
	{
		this.filters.add(filter);
	}

	public void addOrdering(QueryOrdering ordering)
	{
		this.orderings.add(ordering);
	}

	public void moveToFront(QueryOrdering ordering)
	{
		if (!this.orderings.contains(ordering))
			throw new IllegalStateException("This ordering not found in the list of orderings: "
					+ ordering);
		this.orderings.remove(ordering);
		this.orderings.addFirst(ordering);
	}

	/**
	 * Creates and returns a new default QueryOrdering object.
	 * @param property Example: "status.name" - an element property name with which to
	 *            sort the results.
	 * @return an instance of QueryOrdering
	 */
	public QueryOrdering newQueryOrdering(String property)
	{
		return new DefaultQueryOrdering(property);
	}

	/**
	 * Creates and returns a new default QueryFiltering object.
	 * @param property - an element property name with which to filter the results.
	 *            Example: "type.name" assuming #getType() is a method on each element in
	 *            the list and getName() is a method on the returned type.
	 * @return an instance of QueryFilter
	 */
	public QueryFilter newDiscreteQueryFilter(String property)
	{
		return new DefaultDiscreteQueryFilter(property);
	}

	/**
	 * Creates and returns a new default QueryFiltering object.
	 * @param property - an element property name with which to filter the results.
	 *            Example: "type.name" assuming #getType() is a method on each element in
	 *            the list and getName() is a method on the returned type.
	 * @return an instance of QueryFilter
	 */
	public QueryFilter newLikeQueryFilter(String property)
	{
		return new DefaultLikeQueryFilter(property);
	}

	/** Replace filters (AKA Expressions, qualifiers) on the query */
	private void filterQuery()
	{
		Expression qualifier = this.baseQualifier;
		for (Iterator iter = this.filters.iterator(); iter.hasNext();)
		{
			Expression exp = ((QueryFilter)iter.next()).getQualifier();
			if (exp == null)
				continue;
			if (qualifier == null)
				qualifier = exp;
			else
				qualifier = qualifier.andExp(exp);
		}
		this.query.setQualifier(qualifier);
	}

	/** Replace the orderings on the query */
	private void orderQuery()
	{
		this.query.clearOrderings();
		for (Iterator iter = this.orderings.iterator(); iter.hasNext();)
		{
			Ordering ordering = ((QueryOrdering)iter.next()).getOrdering();
			if (ordering != null)
				getQuery().addOrdering(ordering);
		}
	}

	/**
	 * Provides a basic implementation of the QueryOrdering interface.
	 */
	public class DefaultQueryOrdering implements QueryOrdering
	{
		private String property;
		private boolean ascending = false;
		private boolean on = false;

		public DefaultQueryOrdering(String property)
		{
			this.property = property;
		}

		public Ordering getOrdering()
		{
			if (!this.on)
				return null;
			return new Ordering(this.property, this.ascending);
		}

		/**
		 * Provides a method to be called be called by a UI action -- like clicking on a
		 * table header.
		 */
		public void onClick()
		{
			this.ascending = !this.ascending;
			this.on = true;
			CayenneQueryBuilder.this.moveToFront(this);
		}
	}

	/**
	 * Provides a basic implementation of the QueryFilter interface for a DropDown list of
	 * filter values.
	 */
	public static class DefaultDiscreteQueryFilter implements QueryFilter
	{
		String filterProperty;
		Object filterValue = "Any";

		private DefaultDiscreteQueryFilter()
		{
		}

		/**
		 * Constructs an instance with all it needs to get started.
		 * @param property - a property of the data objects. Example: "status.name"
		 */
		public DefaultDiscreteQueryFilter(String property)
		{
			this.filterProperty = property;
			this.filterValue = "Any";
		}

		public Object getFilterValue()
		{
			return this.filterValue;
		}

		public void setFilterValue(Object o)
		{
			this.filterValue = o;
		}

		public Expression getQualifier()
		{
			if ("Any".equals(this.filterValue))
				return null;
			return ExpressionFactory.matchExp(this.filterProperty, this.filterValue);
		}
	}

	/**
	 * Provides a way to do a sql LIKE filter on a String value in the query
	 * @author Jonathan Carlson
	 */
	public static class DefaultLikeQueryFilter extends DefaultDiscreteQueryFilter
	{
		public DefaultLikeQueryFilter(String property)
		{
			super(property);
			this.filterValue = null;
		}

		public Expression getQualifier()
		{
			if (this.filterValue == null)
				return null;
			String temp = this.filterValue.toString();
			if (temp.trim().length() == 0)
				return null;
			if (temp.indexOf("*") >= 0)
				temp = temp.replaceAll("\\*", "%");
			else
				temp = "%" + temp + "%";
			return ExpressionFactory.likeIgnoreCaseExp(this.filterProperty, temp);
		}
	}

}