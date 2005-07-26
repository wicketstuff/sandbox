/**
 * Copyright (C) 2005, Jonathan W. Locke. All Rights Reserved.
 */

package wicket.contrib.database;

import wicket.PageParameters;
import wicket.markup.html.WebPage;
import wicket.model.IModel;
import wicket.util.string.StringValueConversionException;

/**
 * Base class for pages in a database driven web application.
 * 
 * @author Jonathan Locke
 */
public class DatabaseWebPage extends WebPage
{
	private final PageParameters parameters;	
	
	public DatabaseWebPage()
	{
		super();
		this.parameters = null;
	}
	
	public DatabaseWebPage(PageParameters parameters)
	{
		this.parameters = parameters;
	}

	public DatabaseWebPage(IModel model)
	{
		super(model);
		this.parameters = null;
	}
	
	protected Database getDatabase()
	{
		return ((IDatabaseApplication)getApplication()).getDatabase(); 
	}
	
	protected DatabaseSession getDatabaseSession()
	{
		DatabaseWebRequestCycle cycle = (DatabaseWebRequestCycle)getRequestCycle();
		return cycle.getDatabaseSession();
	}
	
	protected IModel newModel(Class c, Long id)
	{
		return new DatabaseObjectModel(getDatabaseSession(), c, id);
	}
	
	protected IModel newModel(Class c)
	{
		try
		{
			return newModel(c, new Long(parameters.getLong("id")));
		}
		catch (StringValueConversionException e)
		{
			throw new DatabaseException(e);
		}
	}
	
	protected Object load(Class c, Long id)
	{
		return getDatabaseSession().load(c, id);
	}
	
	protected Object load(Class c)
	{
		try
		{
			return load(c, new Long(parameters.getLong("id")));
		}
		catch (StringValueConversionException e)
		{
			throw new DatabaseException(e);
		}
	}
	
	protected void delete(Class c, Long id)
	{
		getDatabaseSession().delete(c, id);
	}
	
	protected void delete(Object object)
	{
		getDatabaseSession().save(object);
	}
	
	protected void evict(Object object)
	{
		getDatabaseSession().evict(object);
	}
	
	protected void save(Object object)
	{
		getDatabaseSession().save(object);
	}
}
