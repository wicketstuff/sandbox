package wicket.contrib.data.model.hibernate.binding;

import org.hibernate.Session;

import wicket.IFeedback;
import wicket.contrib.data.model.hibernate.sandbox.HibernateModel;
import wicket.contrib.data.model.hibernate.sandbox.IHibernateDao;
import wicket.contrib.data.model.hibernate.sandbox.IHibernateDao.IHibernateCallback;
import wicket.model.IModel;

public abstract class HibernateGridView extends GridView
{
    private IHibernateDao dao;
    
    public HibernateGridView(String id, IModel list, IFeedback feedback,
            int perPage, IHibernateDao dao) {
        super(id, list, feedback, perPage);
        this.dao = dao;
    }
    
	protected IModel getListItemModel(Object object)
	{
		return new HibernateModel(object, dao);
	}

	public void onSubmit()
	{
        dao.execute(new IHibernateCallback()
		{
			public Object execute(Session session)
			{
				session.update(getModelObject());
				return null;
			}
		});
		removeEditModel();
	}

	protected void delete(final Object object)
	{
        dao.execute(new IHibernateCallback()
		{
			public Object execute(Session session)
			{
                session.delete(object);
                return null;
			}
		});
        removeEditModel();
	}
}
