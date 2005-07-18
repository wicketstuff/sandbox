package wicket.contrib.data.model.hibernate.sandbox;

import java.io.Serializable;

import org.hibernate.EntityMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.proxy.HibernateProxy;
import org.hibernate.proxy.LazyInitializer;

import wicket.Component;
import wicket.contrib.data.model.hibernate.sandbox.IHibernateDao;
import wicket.contrib.data.model.hibernate.sandbox.IHibernateDao.IHibernateCallback;
import wicket.model.IModel;

/**
 * Wraps a Hibernate object.
 * 
 * @author Phil Kulak
 */
public class HibernateModel implements IModel
{
	private IHibernateDao dao;

	private Class clazz;

	private Serializable id;

	private transient Object model;
	
	/**
	 * Constructor that wraps any mapped Hibernate class.
	 * 
	 * @param model the object to wrap
	 * @param dao the data access object for this model to use
	 */
	public HibernateModel(Object model, IHibernateDao dao)
	{
		// Get the name of the object.
		String entityName = "";
		if (model instanceof HibernateProxy)
		{
			HibernateProxy proxy = (HibernateProxy) model;
			LazyInitializer lazyInitializer = proxy.getHibernateLazyInitializer();
			entityName = lazyInitializer.getEntityName();
		}
		else
		{
			entityName = model.getClass().getName();
		}
		
		SessionFactory factory = (SessionFactory) dao.execute(new IHibernateCallback()
		{
			public Object execute(Session session)
			{
				return session.getSessionFactory();
			}
		});

		ClassMetadata meta = factory.getClassMetadata(entityName);

		// Set all the values.
		this.dao = dao;
		this.clazz = meta.getMappedClass(EntityMode.POJO);
		this.id = meta.getIdentifier(model, EntityMode.POJO);
		this.model = model;
	}

	/**
	 * @see wicket.model.IModel#getNestedModel()
	 */
	public IModel getNestedModel()
	{
		return null;
	}
	
	/**
	 * @see wicket.model.IModel#getObject(Component)
	 */
	public Object getObject(Component component)
	{
		if (model == null)
		{
			attach();
		}
		return model;
	}

	/**
	 * @see wicket.model.IModel#setObject(Component, Object)
	 */
	public void setObject(Component component, Object object)
	{
		throw new UnsupportedOperationException("setObject(Component, Object) "
				+ "is not supported on Hibernate models.");
	}
	
	/**
	 * @see wicket.model.IDetachable#detach()
	 */
	public void detach()
	{
		model = null;
	}

	private void attach()
	{
		model = dao.execute(new IHibernateCallback()
		{
			public Object execute(Session session)
			{
				return session.load(clazz, id);
			}
		});
	}
}
