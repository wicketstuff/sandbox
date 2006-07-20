package wicket.contrib.data.model;

import wicket.model.IDetachable;
import wicket.model.IModel;

/**
 * A detachable wrapper for an OrderedList.
 * 
 * @author Phil Kulak
 */
public class DetachableList implements IModel<IDetachable>
{
	private static final long serialVersionUID = 1L;

	private IDetachable object;

	/**
	 * Wraps an {@link wicket.contrib.data.model.OrderedPageableList}.
	 * 
	 * @param list
	 *            the list to wrap
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

	public IDetachable getObject()
	{
		return object;
	}

	public void setObject(IDetachable object)
	{
		this.object = object;
	}
}
