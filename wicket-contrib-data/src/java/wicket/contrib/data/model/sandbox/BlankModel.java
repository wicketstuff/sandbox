package wicket.contrib.data.model.sandbox;

import wicket.Component;
import wicket.WicketRuntimeException;
import wicket.model.AbstractDetachableModel;
import wicket.model.IModel;

/**
 * A simple detachable model that creates a new bean instance on every attach.
 * This is good for forms where the data gets reinput on every submit and
 * there's no reason to hold it all in the session. The model object must be
 * have a no-arg constructor.
 * 
 * @author Phil Kulak
 */
public class BlankModel extends AbstractDetachableModel
{
	private Object object;

	private Class objectClass;
	
	/**
	 * Constructor
	 * 
	 * @param objectClass the class that will be used to create new beans
	 */
	public BlankModel(Class objectClass)
	{
		this.objectClass = objectClass;
	}
	
	/**
	 * @see wicket.model.AbstractDetachableModel#getNestedModel()
	 */
	public IModel getNestedModel()
	{
		return null;
	}

	protected void onAttach()
	{
		try
		{
			object = objectClass.newInstance();
		}
		catch (Exception e)
		{
			throw new WicketRuntimeException("Could not create new instance: "
					+ e.getMessage());
		}
	}

	protected void onDetach()
	{
		object = null;
	}

	protected Object onGetObject(Component component)
	{
		return object;
	}

	protected void onSetObject(Component component, Object object)
	{
		this.object = object;
		this.objectClass = object.getClass();
	}
}
