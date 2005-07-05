package wicket.contrib.data.model.sandbox;

import wicket.AttributeModifier;
import wicket.Component;
import wicket.contrib.data.model.sandbox.OrderedPageableList;
import wicket.markup.html.WebMarkupContainer;
import wicket.markup.html.link.Link;
import wicket.markup.html.list.ListView;
import wicket.model.AbstractModel;
import wicket.model.IModel;
import wicket.model.Model;

/**
 * A link that changes the ordering on a field of an OrderedPageableList.
 * 
 * @author Phil Kulak
 */
public class OrderByLink extends Link
{
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
	 * @see wicket.markup.html.link.Link
	 */
	public void onClick()
	{
        OrderedPageableList listModel = (OrderedPageableList) list.getModelObject();
		
        // Add the ordering to the list.
        listModel.addOrder(field);
        
        // Clear the items so they get redrawn.
        list.removeAll();

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
		setModelObject(NONE);
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
			Integer state = (Integer) OrderByLink.this.getModelObject();
			if (state.equals(UP))
				return "orderUp";
			if (state.equals(DOWN))
				return "orderDown";
			else
				return "orderNone";
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
