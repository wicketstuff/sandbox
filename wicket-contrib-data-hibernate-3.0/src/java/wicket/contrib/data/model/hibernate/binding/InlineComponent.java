package wicket.contrib.data.model.hibernate.binding;

import wicket.Component;
import wicket.WicketRuntimeException;
import wicket.markup.html.WebMarkupContainer;
import wicket.markup.html.list.ListItem;

/**
 * A convenient base class for inline components. This is also where all the
 * static methods are stored for components that cannot directly subclass.
 * 
 * @author Phil Kulak
 */
public abstract class InlineComponent extends WebMarkupContainer
{

	/**
	 * @param id
	 *            the id of this component
	 */
	public InlineComponent(String id)
	{
		super(id);
	}

	/**
	 * @return true if this component is currently being edited
	 */
	protected boolean isEdit()
	{
		return isEdit(this);
	}

	/**
	 * @param component
	 *            the component to check
	 * @return true if the component is currently being edited
	 */
	public static boolean isEdit(Component component)
	{
		return findForm(component).isEditModel(findListItem(component).getModel());
	}

	/**
	 * @param component
	 *            the component to start the search from
	 * @return the first HibernateGridView parent found
	 */
	public static HibernateGridView findForm(Component component)
	{
		HibernateGridView form = (HibernateGridView) component
				.findParent(HibernateGridView.class);
		if (form == null)
		{
			throw new WicketRuntimeException("An inline component must be a "
					+ "child of a ListViewForm.");
		}
		return form;
	}

	/**
	 * This method finds the parent ListView that is closest to the first parent
	 * HibernateGridView. This allows a component to be nested arbitrarilly deep
	 * in lists, but still be aware of its edit status.
	 * 
	 * @param component
	 *            the component to start the search from
	 * @return the ListItem containing the model for this row
	 */
	public static ListItem findListItem(Component component)
	{
		ListItem item = findListItemRec(component);

		if (item == null)
		{
			throw new WicketRuntimeException("A suitable ListItem could not "
					+ "be found in the component tree.");
		}
		return item;
	}

	private static ListItem findListItemRec(Component component)
	{
		ListItem item = null;

		while ((component = component.getParent()) != null)
		{
			if (component instanceof ListItem)
			{
				item = (ListItem) component;
			}
			else if (component instanceof HibernateGridView)
			{
				return item;
			}
		}
		return null;
	}
}
