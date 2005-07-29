package wicket.contrib.data.model;

import wicket.Component;
import wicket.model.IDetachable;
import wicket.model.IModel;

/**
 * A detachable wrapper for an OrderedList.
 * 
 * @author Phil Kulak
 */
public class DetachableList implements IModel
{
	private IDetachable object;

	/**
	 * Wraps an {@link wicket.contrib.data.model.OrderedPageableList}.
	 * 
	 * @param list the list to wrap
	 */
	public DetachableList(IDetachable object)
	{
		this.object = object;
	}
	
	public IModel getNestedModel()
	{
		return null;
	}

	public void detach()
	{
		object.detach();
	}

	public Object getObject(Component component)
	{
		return object;
	}

	public void setObject(Component component, Object object)
	{
		this.object = (IDetachable) object;
	}
}
