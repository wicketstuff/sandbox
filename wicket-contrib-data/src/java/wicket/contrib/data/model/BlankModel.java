package wicket.contrib.data.model;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.model.IModel;

/**
 * A simple detachable model that creates a new bean instance on every attach.
 * This is good for forms where the data gets reinput on every submit and
 * there's no reason to hold it all in the session. The model object must be
 * have a no-arg constructor.
 * 
 * @author Phil Kulak
 */
public class BlankModel implements IModel
{
	private Object object;

	private Class objectClass;

	private transient boolean attached = false;

	/**
	 * Constructor
	 * 
	 * @param objectClass
	 *            the class that will be used to create new beans
	 */
	public BlankModel(Class objectClass)
	{
		this.objectClass = objectClass;
	}

	/**
	 * Attach.
	 */
	public void attach()
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

	public void detach()
	{
		object = null;
		attached = false;
	}

	public Object getObject()
	{
		if (!attached)
		{
			attach();
			attached = true;
		}
		return object;
	}

	/**
	 * Whether this model has been attached to the current request.
	 * 
	 * @return whether this model has been attached to the current request
	 */
	public final boolean isAttached()
	{
		return attached;
	}

	public void setObject(Object object)
	{
		this.object = object;
		this.objectClass = object.getClass();
	}
}
