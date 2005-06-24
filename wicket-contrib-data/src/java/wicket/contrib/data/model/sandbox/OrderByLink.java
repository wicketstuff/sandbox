package wicket.contrib.data.model.sandbox;

import wicket.AttributeModifier;
import wicket.Component;
import wicket.contrib.data.model.sandbox.OrderedPageableList;
import wicket.markup.html.WebMarkupContainer;
import wicket.markup.html.link.Link;
import wicket.model.AbstractModel;
import wicket.model.IModel;
import wicket.model.Model;

/**
 * A link that changes the ordering on a field of an
 * 
 * @{link wicket.contrib.data.model.sandbox.OrderedPageableList}
 * @author Phil Kulak
 */
public class OrderByLink extends Link
{
	private static final int UP = 0;

	private static final int DOWN = 1;

	private static final int NONE = 2;

	private String field;

	private int state = NONE;

	/**
	 * Constructor. The list will be wrapped in a simple model.
	 * 
	 * @param id
	 *            the id of the link
	 * @param list
	 *            the list to change ordering on
	 * @param field
	 *            the field of the list
	 */
	public OrderByLink(String id, OrderedPageableList list, String field)
	{
		super(id, new Model(list));
		this.field = field;
		add(new AttributeModifier("class", true, new AttribModel()));
	}

	/**
	 * Constructor.
	 * 
	 * @param id
	 *            the id of the link
	 * @param listModel
	 *            the model that contains the list to change ordering on
	 * @param field
	 *            the field of the list
	 */
	public OrderByLink(String id, IModel listModel, String field)
	{
		super(id, listModel);
		this.field = field;
		add(new AttributeModifier("class", true, new AttribModel()));
	}

	/**
	 * @see wicket.markup.html.link.Link
	 */
	public void onClick()
	{
		((OrderedPageableList) getModelObject()).addOrder(field);

		// Switch our state.
		switchState();

		// Reset all other links in this container.
		WebMarkupContainer parent = (WebMarkupContainer) getParent();

		parent.visitChildren(OrderByLink.class, new IVisitor()
		{
			public Object component(Component child)
			{
				OrderByLink link = (OrderByLink) child;
				if (link != OrderByLink.this)
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
		this.state = NONE;
	}

	/**
	 * Switches the state of this link.
	 */
	private void switchState()
	{
		if (state == NONE || state == UP)
		{
			state = DOWN;
		}
		else
		{
			state = UP;
		}
	}

	/**
	 * The class attribute of a order by link.
	 */
	private class AttribModel extends AbstractModel
	{
		/**
		 * @see wicket.model.IModel
		 */
		public IModel getNestedModel()
		{
			return null;
		}

		/**
		 * @see wicket.model.IModel
		 */
		public Object getObject(Component arg0)
		{
			switch (state)
			{
				case UP:
					return "orderUp";
				case DOWN:
					return "orderDown";
				default:
					return "orderNone";
			}
		}

		/**
		 * @see wicket.model.IModel
		 */
		public void setObject(Component component, Object object)
		{
			throw new UnsupportedOperationException();
		}
	}
}
