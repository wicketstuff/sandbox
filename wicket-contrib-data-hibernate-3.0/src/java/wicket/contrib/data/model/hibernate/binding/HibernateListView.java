package wicket.contrib.data.model.hibernate.binding;

import java.util.List;

import wicket.contrib.data.model.hibernate.sandbox.HibernateModel;
import wicket.contrib.data.model.hibernate.sandbox.IHibernateDao;
import wicket.markup.html.list.PageableListView;
import wicket.model.IModel;

/**
 * Wraps the list item in a Hibernate model.
 * 
 * @author Phil Kulak
 */
public abstract class HibernateListView extends PageableListView
{
	private IHibernateDao dao;

	/**
	 * @param id
	 *            the id of the list view
	 * @param model
	 *            the list wrapped in a model
	 * @param rowsPerPage
	 *            the number of rows to display per page
	 * @param dao
	 *            the dao to use to interact with Hibernate
	 */
	public HibernateListView(String id, IModel model, int rowsPerPage, IHibernateDao dao)
	{
		super(id, model, rowsPerPage);
		this.dao = dao;
	}

	public final IModel getListItemModel(IModel listViewModel, int index)
	{
		Object object = ((List) listViewModel.getObject(this)).get(index);
		return new HibernateModel(object, dao);
	}

	/**
	 * @return the Hibernate dao currently being used by this list view
	 */
	public IHibernateDao getHibernateDao()
	{
		return dao;
	}
}
