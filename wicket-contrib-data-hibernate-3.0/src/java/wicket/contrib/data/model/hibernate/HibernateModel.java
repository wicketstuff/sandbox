package wicket.contrib.data.model.hibernate;

import java.io.Serializable;

import org.hibernate.EntityMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.proxy.HibernateProxy;
import org.hibernate.proxy.LazyInitializer;

import wicket.Component;
import wicket.WicketRuntimeException;
import wicket.contrib.data.model.hibernate.IHibernateDao;
import wicket.contrib.data.model.hibernate.IHibernateDao.IHibernateCallback;
import wicket.model.IModel;
import wicket.model.Model;

/**
 * Wraps a Hibernate object.
 * 
 * @author Phil Kulak
 */
public class HibernateModel implements IModel, Comparable
{
	private IModel dao;

	private Class clazz;

	private Serializable id;
	
	private boolean unproxy;
	
	private boolean evict;

	private transient Object model;
	
	/**
	 * Constructor that wraps any mapped Hibernate class.
	 * 
	 * @param model the object to wrap
	 * @param dao the data access object for this model to use
	 */
	public HibernateModel(Object model, IHibernateDao dao) {
		this(model, new Model(dao), false);
	}
	
	/**
	 * Constructor that wraps any mapped Hibernate class.
	 * 
	 * @param model the object to wrap
	 * @param dao the data access object for this model to use
	 */
	public HibernateModel(Object model, IModel dao) {
		this(model, dao, false);
	}
	
	/**
	 * Constructor that wraps any mapped Hibernate class.
	 * 
	 * @param model the object to wrap
	 * @param dao the data access object for this model to use
	 * @param unproxy whether or not to unproxy object on getObject()
	 */
	public HibernateModel(Object model, IHibernateDao dao, boolean unproxy)
	{
		this(model, new Model(dao), unproxy);
	}
	
	/**
	 * Constructor that wraps any mapped Hibernate class.
	 * 
	 * @param model the object to wrap
	 * @param dao the data access object for this model to use
	 * @param unproxy whether or not to unproxy object on getObject()
	 */
	public HibernateModel(Object model, IModel dao, boolean unproxy)
	{
		this.unproxy = unproxy;
		this.dao = dao;
		
		// Get the name of the object.
		String entityName = "";
		Object unproxiedModel = model;
		if (model instanceof HibernateProxy)
		{
			HibernateProxy proxy = (HibernateProxy) model;
			LazyInitializer lazyInitializer = proxy.getHibernateLazyInitializer();
			entityName = lazyInitializer.getEntityName();
			unproxiedModel = lazyInitializer.getImplementation();
		}
		else
		{
			entityName = model.getClass().getName();
		}
		
		SessionFactory factory = (SessionFactory) getDao().execute(new IHibernateCallback()
		{
			public Object execute(Session session)
			{
				return session.getSessionFactory();
			}
		});

		ClassMetadata meta = factory.getClassMetadata(entityName);
		
		if (meta == null) {
			throw new WicketRuntimeException("A mapping for the class: " + 
				entityName + " could not be found.");
		}

		// Set all the values.
		this.clazz = meta.getMappedClass(EntityMode.POJO);
		this.id = meta.getIdentifier(unproxiedModel, EntityMode.POJO);
		
		if (id == null)
		{
			 throw new WicketRuntimeException("Could not get primary key for: "  +
				 entityName);
		}
		
		if (unproxy)
		{
			this.model = unproxiedModel;
		}
		else
		{
			this.model = model;
		}
	}
	
	/**
	 * Set to true to have the object evicted from the session before it's
	 * returned from {@link #getObject(Component)}.
	 */
	public void setEvict(boolean evict)
	{
		this.evict = evict;
	}
	
	public Serializable getId()
	{
		return id;
	}
	
	public Class getClazz()
	{
		return clazz;
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
		
		if (evict)
		{
			getDao().execute(new IHibernateCallback()
			{
				public Object execute(Session session)
				{
					session.evict(model);
					return null;
				}
			});
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
		model = getDao().execute(new IHibernateCallback()
		{
			public Object execute(Session session)
			{
				return session.load(clazz, id);
			}
		});
		
		if (unproxy && model instanceof HibernateProxy)
		{
			HibernateProxy proxy = (HibernateProxy) model;
			model = proxy.getHibernateLazyInitializer().getImplementation();
		}
	}
	
	public int compareTo(Object rhs)
	{
		HibernateModel model = (HibernateModel) rhs;
		
		if (!getId().equals(model.getId())) {
			return ((Comparable) getId()).compareTo((Comparable) model.getId());
		}
		
		return getClazz().getName().compareTo(model.getClazz().getName());
	}
	
	public boolean equals(Object rhs)
	{
		if (this == rhs)
		{
			return true;
		}
		
		if (rhs instanceof HibernateModel)
		{
			HibernateModel hm = (HibernateModel) rhs;
			return hm.getClazz().equals(getClazz()) && hm.getId().equals(getId());
		}
		
		return false;
	}
	
	public int hashCode()
	{
		return getClazz().getName().hashCode() ^ getId().hashCode();
	}
	
	private IHibernateDao getDao() {
		return (IHibernateDao) dao.getObject(null);
	}
}
