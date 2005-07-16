package wicket.contrib.data.model.hibernate.binding;


import org.hibernate.Session;

import wicket.IFeedback;
import wicket.contrib.data.model.hibernate.sandbox.IHibernateDao;
import wicket.contrib.data.model.hibernate.sandbox.IHibernateDao.IHibernateCallback;
import wicket.markup.html.form.Form;
import wicket.markup.html.list.ListItem;
import wicket.model.IModel;
import wicket.model.Model;

/**
 * A form that contains a list view. The form's model is always the item
 * that's currently being worked on in the list view, or an empty model
 * if no item is being edited. To allow this grid to support edits, each
 * list item must contain an InlineEditLink as well as an InlineSubmitLink.
 * To support deletions, each ListItem must contain an InlineDeleteLink.
 * 
 * @author Phil Kulak
 */
public abstract class HibernateGridView extends Form {	
	public static final IModel EMPTY_MODEL = new Model();
	private FormList listView;
	
	public HibernateGridView(String id, IModel list, IFeedback feedback, int perPage, 
			IHibernateDao dao) {
		super(id, EMPTY_MODEL, feedback);
		listView = new FormList(list, perPage, dao);
		add(listView);
	}
	
	/**
	 * Returns true if the given model is the one currently beind edited.
	 */
	public final boolean isEditModel(IModel model) {
		if (getModelObject() == null) {
			return false;
		}
		return model.getObject(this).equals(getModelObject());
	}
	
	/**
	 * Sets the model as the edit.
	 */
	public final void setEditModel(IModel model) {
		setModel(model);
	}
	
	/**
	 * Sets the form's model object to null, removing the edit.
	 */
	public final void removeEditModel() {
		setModel(EMPTY_MODEL);
	}
	
	/**
	 * Returns the list being used internally by this form.
	 */
	public HibernateListView getListView() {
		return listView;
	}
	
	/**
	 * Saves the object. Override this to provide custom processing.
	 */
	public void onSubmit() {
		getListView().getHibernateDao().execute(new IHibernateCallback() {
			public Object execute(Session session) {
				session.update(getModelObject());
				return null;
			}
		});
		removeEditModel();
	}
	
	/**
	 * Delegated to from the internal list view.
	 */
	protected abstract void populateItem(ListItem item);
	
	/**
	 * This class is private because it delegates all it's functionality
	 * to the ListViewForm so that users don't even have to be aware of it.
	 */
	private class FormList extends HibernateListView {
		public FormList(IModel model, int perPage, IHibernateDao dao) {
			super("rows", model, perPage, dao);
			setOptimizeItemRemoval(true);
		}

		protected void populateItem(ListItem item) {
			HibernateGridView.this.populateItem(item);
		}
	}
}
