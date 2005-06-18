package wicket.contrib.data.model.sandbox;

import wicket.Component;
import wicket.model.IModel;

/**
 * A detachable wrapper for a OrderedPageableList.
 * 
 * @author Phil Kulak
 */
public class DetachableList implements IModel
{
	private OrderedPageableList list;

	/**
	 * Wraps an {@link wicket.contrib.data.model.sandbox.OrderedPageableList}.
	 * 
	 * @param list the list to wrap
	 */
	public DetachableList(OrderedPageableList list)
	{
		this.list = list;
	}
	
	public IModel getNestedModel()
	{
		return null;
	}

	public void detach()
	{
		list.onDetach();
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
	 * Wrapper for {@link wicket.contrib.data.model.sandbox.OrderedPageableList#addOrder(String)}
	 * 
	 * @param field the field to order by
	 * @return itself to support chaining
	 */
	public DetachableList addOrder(String field) {
		list.addOrder(field);
		return this;
	}
}
