package wicket.contrib.data.model;

import org.apache.wicket.model.IDetachable;
import org.apache.wicket.model.IModel;

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
	 * @param list
	 *            the list to wrap
	 */
	public DetachableList(IDetachable object)
	{
		this.object = object;
	}

	public void detach()
	{
		object.detach();
	}

	public IModel getNestedModel()
	{
		return null;
	}

	public Object getObject()
	{
		return object;
	}

	public void setObject(Object object)
	{
		this.object = (IDetachable) object;
	}
}
