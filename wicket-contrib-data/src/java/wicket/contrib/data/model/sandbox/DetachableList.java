package wicket.contrib.data.model.sandbox;

import java.util.ArrayList;

import wicket.Component;
import wicket.model.IModel;
import wicket.model.Model;

/**
 * A detachable wrapper for an OrderedList.
 * 
 * @author Phil Kulak
 */
public class DetachableList implements IModel
{
	/** Useful for building an empty ListView **/
	public final static IModel EMPTY_LIST = new Model(new ArrayList(0));
	private OrderedList list;

	/**
	 * Wraps an {@link wicket.contrib.data.model.sandbox.OrderedPageableList}.
	 * 
	 * @param list the list to wrap
	 */
	public DetachableList(OrderedList list)
	{
		this.list = list;
	}
	
	public IModel getNestedModel()
	{
		return null;
	}

	public void detach()
	{
		list.detach();
	}

	public Object getObject(Component component)
	{
		return list;
	}

	public void setObject(Component component, Object object)
	{
		throw new UnsupportedOperationException(
				"setting the internal object is not supported");
	}
	
	/**
	 * A convenience method for gettign the underlying list.
	 * 
	 * @return the backing list
	 */
	public OrderedList getList() {
		return list;
	}
}
