package wicket.contrib.data.model.bind;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import wicket.contrib.data.model.OrderedPageableList;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

/**
 * A link that changes the ordering on a field of an OrderedPageableList.
 * 
 * @author Phil Kulak
 */
public class OrderByLink extends Link
{
	/**
	 * The class attribute of a order by link.
	 */
	private class AttribModel implements IModel
	{
		public void detach()
		{
		}

		/**
		 * @see wicket.model.IModel
		 */
		public Object getObject()
		{
			Integer state = (Integer) OrderByLink.this.getModelObject();
			if (state.equals(UP))
				return "wicket_orderUp";
			if (state.equals(DOWN))
				return "wicket_orderDown";
			else
				return "wicket_orderNone";
		}

		/**
		 * @see wicket.model.IModel
		 */
		public void setObject(Object object)
		{
			throw new UnsupportedOperationException();
		}
	}

	private static final Integer UP = new Integer(0);

	private static final Integer DOWN = new Integer(1);

	private static final Integer NONE = new Integer(2);

	private String field;

	private ListView list;

	/**
	 * Constructor.
	 * 
	 * @param id
	 *            the id of the link
	 * @param field
	 *            the field of the list
	 * @param list
	 *            the ListView that contains an OrderedPageableList
	 */
	public OrderByLink(String id, String field, ListView list)
	{
		super(id, new Model(NONE));
		this.field = field;
		this.list = list;
		add(new AttributeModifier("class", true, new AttribModel()));
	}

	/**
	 * Returns the list this link operates on.
	 * 
	 * @return the ListView used by this link
	 */
	public ListView getList()
	{
		return list;
	}

	/**
	 * @see wicket.markup.html.link.Link
	 */
	public void onClick()
	{
		OrderedPageableList listModel = (OrderedPageableList) list.getModelObject();

		// Add the ordering to the list.
		list.modelChanging();
		listModel.addOrder(field);
		list.modelChanged();

		// Clear the items so they get redrawn.
		list.removeAll();

		// Switch our state.
		switchState();

		// Reset all other links that use the same list.
		MarkupContainer parent = getPage();

		parent.visitChildren(OrderByLink.class, new IVisitor()
		{
			public Object component(Component child)
			{
				OrderByLink link = (OrderByLink) child;
				if (link != OrderByLink.this && OrderByLink.this.usesSameList(link))
				{
					link.reset();
				}
				return null;
			}
		});
	}

	/**
	 * Sets the state for this link back to NONE.
	 */
	public void reset()
	{
		setModelObject(NONE);
	}

	/**
	 * Does this link point to the same list?
	 * 
	 * @param rhs
	 *            the link to check
	 * @return true if rhs operates on the same list as this link
	 */
	public boolean usesSameList(OrderByLink rhs)
	{
		return list == rhs.getList();
	}

	/**
	 * Switches the state of this link.
	 */
	private void switchState()
	{
		Integer state = (Integer) getModelObject();

		if (state.equals(NONE) || state.equals(UP))
		{
			setModelObject(DOWN);
		}
		else
		{
			setModelObject(UP);
		}
	}
}
