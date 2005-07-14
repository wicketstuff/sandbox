/*
 * Created on May 12, 2005
 */
package net.sf.ipn.app.component;

import org.objectstyle.cayenne.access.DataContext;
import org.objectstyle.cayenne.query.SelectQuery;

import wicket.Component;
import wicket.model.AbstractReadOnlyDetachableModel;
import wicket.model.IModel;

/**
 * @author Jonathan Carlson Cayenne queries are quite powerful and have lots of efficiency
 *         options, which means this model class can be rather simple. For example, a
 *         query like the one below creates a List that provides caching, paging, and
 *         ordering. <code>
 * SelectQuery query = new SelectQuery(Employee.class);
 * query.setPageSize(50);
 * query.setQualifier(Expression.fromString("manager = ${manager}");
 * query.addOrdering("lastName", true); 
 * IModel model = new CayenneQueryModel(getDataContext(), query);
 * ...
 * </code>
 *         or the model creation could look like this if you want to be able to
 *         dynamically specialize query at runtime. (see CayenneQueryBuilder for more
 *         information): <code>
 * IModel model = new CayenneQueryModel(getDataContext(),
 *    new CayenneQueryBuilder() 
 *    {
 *      public SelectQuery buildBaseQuery()
 *      {
 *        SelectQuery query = new SelectQuery(Employee.class);
 *        query.setPageSize(50);
 *        query.setQualifier(Expression.fromString("manager = ${manager}");
 *        query.addOrdering("lastName", true);
 *        return query;
 *      }
 *    });
 * </code>
 * @see CayenneQueryBuilder
 */
public class CayenneQueryModel extends AbstractReadOnlyDetachableModel
{
	protected DataContext dataContext;
	protected SelectQuery query;
	protected CayenneQueryBuilder queryBuilder;
	protected transient Object object;

	public CayenneQueryModel(DataContext dataCtx, SelectQuery query)
	{
		this.dataContext = dataCtx;
		this.query = query;
	}

	public CayenneQueryModel(DataContext dataCtx, CayenneQueryBuilder queryBuilder)
	{
		this.dataContext = dataCtx;
		this.queryBuilder = queryBuilder;
	}

	/**
	 * @see wicket.model.IModel#getNestedModel()
	 */
	public IModel getNestedModel()
	{
		return null;
	}

	/**
	 * @see wicket.model.AbstractDetachableModel#onAttach()
	 */
	protected void onAttach()
	{
		System.out.println("CayenneQueryModel#onAttach()...");
		this.object = this.dataContext.performQuery(getQuery());
	}

	/**
	 * @see wicket.model.AbstractDetachableModel#onDetach()
	 */
	protected void onDetach()
	{
		System.out.println("CayenneQueryModel#onDetach()");
		this.object = null;
	}

	/**
	 * @see wicket.model.AbstractDetachableModel#onGetObject(wicket.Component)
	 */
	protected Object onGetObject(Component component)
	{
		return this.object;
	}

	protected SelectQuery getQuery()
	{
		if (this.query == null) // Leave query null
			return this.queryBuilder.getQuery();
		return this.query;
	}

}