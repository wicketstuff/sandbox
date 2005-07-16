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
public abstract class InlineComponent extends WebMarkupContainer {
	
	public InlineComponent(String id) {
		super(id);
	}

	protected boolean isEdit() {
		return isEdit(this);
	}
	
	public static boolean isEdit(Component component) {
		return findForm(component).isEditModel(findListItem(component).getModel());
	}
	
	public static HibernateGridView findForm(Component component) {
		HibernateGridView form = (HibernateGridView) component.findParent(HibernateGridView.class);
		if (form == null) {
			throw new WicketRuntimeException("An inline component must be a " +
				"child of a ListViewForm.");
		}
		return form;
	}
	
	public static ListItem findListItem(Component component) {
		ListItem item = findListItemRec(component);
		
		if (item == null) {
			throw new WicketRuntimeException("A suitable ListItem could not " +
				"be found in the component tree.");
		}
		return item;
	}
	
	private static ListItem findListItemRec(Component component) {
		ListItem item = null;
		
		while ((component = component.getParent()) != null) {
			if (component instanceof ListItem) {
				item = (ListItem) component;
			}
			else if (component instanceof HibernateGridView) {
				return item;
			}
		}
		return null;
	}
}
