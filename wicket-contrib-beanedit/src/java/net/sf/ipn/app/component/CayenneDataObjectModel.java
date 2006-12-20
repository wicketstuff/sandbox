/*
 * Created on Jun 9, 2005
 */
package net.sf.ipn.app.component;

import java.util.List;

import org.objectstyle.cayenne.CayenneDataObject;
import org.objectstyle.cayenne.access.DataContext;

/**
 * @author Jonathan Carlson Provides a Model for one persistent (or new) Cayenne
 *         DataObject subclass. When the newDataObject fields is true, a new instance of
 *         the persistent class will be returned.
 */
public class CayenneDataObjectModel extends CayenneQueryModel
{
	private Class dataObjectClass;
	private boolean newDataObject = false;

	/**
	 * @param dataCtx - an instance of org.objectstyle.cayenne.DataContext
	 * @param queryBuilder - an instance of CayenneQueryBuilder
	 */
	public CayenneDataObjectModel(DataContext dataCtx, CayenneQueryBuilder queryBuilder,
			Class doClass)
	{
		super(dataCtx, queryBuilder);
		this.dataObjectClass = doClass;
	}

	protected void onAttach()
	{
		if (isNewDataObject())
		{
			System.out.println("CayenneDataObjectModel#onAttach creating new DataObject");
			this.object = this.dataContext.createAndRegisterNewObject(this.dataObjectClass);
		}
		else
		{
			super.onAttach();
			if (this.object instanceof List)
			{
				this.object = ((List)this.object).get(0);
				((CayenneDataObject)this.object).resolveFault();
				System.out.println(" attached object=" + this.object);
			}
		}
	}


	public boolean isNewDataObject()
	{
		return this.newDataObject;
	}

	public void setNewDataObject(boolean bool)
	{
		this.newDataObject = bool;
	}

}
