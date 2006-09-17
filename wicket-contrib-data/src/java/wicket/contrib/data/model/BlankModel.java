package wicket.contrib.data.model;

import wicket.WicketRuntimeException;
import wicket.model.AbstractDetachableModel;

/**
 * A simple detachable model that creates a new bean instance on every attach.
 * This is good for forms where the data gets reinput on every submit and
 * there's no reason to hold it all in the session. The model object must be
 * have a no-arg constructor.
 * 
 * @author Phil Kulak
 */
public class BlankModel<T> extends AbstractDetachableModel<T>
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private T object;

	private Class<T> objectClass;

	/**
	 * Constructor
	 * 
	 * @param objectClass
	 *            the class that will be used to create new beans
	 */
	public BlankModel(Class<T> objectClass)
	{
		this.objectClass = objectClass;
	}

	/**
	 * @see wicket.model.AbstractDetachableModel#onAttach()
	 */
	@Override
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

	/**
	 * @see wicket.model.AbstractDetachableModel#onDetach()
	 */
	@Override
	protected void onDetach()
	{
		object = null;
	}

	/**
	 * @see wicket.model.AbstractDetachableModel#onGetObject()
	 */
	@Override
	protected T onGetObject()
	{
		return object;
	}

	/**
	 * @see wicket.model.AbstractDetachableModel#onSetObject(java.lang.Object)
	 */
	@Override
	@SuppressWarnings("unchecked")
	protected void onSetObject(T object)
	{
		this.object = object;
		this.objectClass = (Class<T>) object.getClass();
	}
}
