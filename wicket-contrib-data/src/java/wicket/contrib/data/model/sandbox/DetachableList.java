package wicket.contrib.data.model.sandbox;

import wicket.Component;
import wicket.model.AbstractDetachableModel;
import wicket.model.IModel;

/**
 * A detachable wrapper for a OrderedPageableList.
 * 
 * @author Phil Kulak
 */
public class DetachableList extends AbstractDetachableModel
{
	private OrderedPageableList list;

	/**
	 * Wraps an [@link wicket.contrib.data.model.sandbox.OrderedPageableList}.
	 * 
	 * @param list the list to wrap
	 */
	public DetachableList(OrderedPageableList list)
	{
		this.list = list;
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
	}

	protected void onDetach()
	{
		list.onDetach();
	}

	protected Object onGetObject(Component component)
	{
		return list;
	}

	protected void onSetObject(Component component, Object object)
	{
		throw new UnsupportedOperationException(
				"setting the internal object is not supported");
	}
}
