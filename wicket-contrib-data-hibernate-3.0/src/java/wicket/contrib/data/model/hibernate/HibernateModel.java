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
import wicket.contrib.data.model.hibernate.IHibernateDao.IHibernateCallback;
import wicket.model.IModel;
import wicket.model.Model;

/**
 * Wraps a Hibernate object.
 * 
 * @author Phil Kulak
 */
public class HibernateModel<T, V> implements IModel<T>, Comparable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private IModel<IHibernateDao> dao;

	private Class<T> clazz;

	private V id;

	private boolean unproxy;

	private boolean evict;

	private transient T model;

	/**
	 * Constructor that wraps any mapped Hibernate class.
	 * 
	 * @param model
	 *            the object to wrap
	 * @param dao
	 *            the data access object for this model to use
	 */
	public HibernateModel(T model, IHibernateDao dao)
	{
		this(model, new Model<IHibernateDao>(dao), false);
	}

	/**
	 * Constructor that wraps any mapped Hibernate class.
	 * 
	 * @param model
	 *            the object to wrap
	 * @param dao
	 *            the data access object for this model to use
	 */
	public HibernateModel(T model, IModel<IHibernateDao> dao)
	{
		this(model, dao, false);
	}

	/**
	 * Constructor that wraps any mapped Hibernate class.
	 * 
	 * @param model
	 *            the object to wrap
	 * @param dao
	 *            the data access object for this model to use
	 * @param unproxy
	 *            whether or not to unproxy object on getObject()
	 */
	public HibernateModel(T model, IHibernateDao dao, boolean unproxy)
	{
		this(model, new Model<IHibernateDao>(dao), unproxy);
	}

	/**
	 * Constructor that wraps any mapped Hibernate class.
	 * 
	 * @param model
	 *            the object to wrap
	 * @param dao
	 *            the data access object for this model to use
	 * @param unproxy
	 *            whether or not to unproxy object on getObject()
	 */
	@SuppressWarnings("unchecked")
	public HibernateModel(T model, IModel<IHibernateDao> dao, boolean unproxy)
	{
		this.unproxy = unproxy;
		this.dao = dao;

		// Get the name of the object.
		String entityName = "";
		T unproxiedModel = model;
		if (model instanceof HibernateProxy)
		{
			HibernateProxy proxy = (HibernateProxy) model;
			LazyInitializer lazyInitializer = proxy.getHibernateLazyInitializer();
			entityName = lazyInitializer.getEntityName();
			unproxiedModel = (T) lazyInitializer.getImplementation();
		}
		else
		{
			entityName = model.getClass().getName();
		}

		SessionFactory factory = (SessionFactory) getDao().execute(
				new IHibernateCallback()
				{
					public Object execute(Session session)
					{
						return session.getSessionFactory();
					}
				});

		ClassMetadata meta = factory.getClassMetadata(entityName);

		if (meta == null)
		{
			throw new WicketRuntimeException("A mapping for the class: "
					+ entityName + " could not be found.");
		}

		// Set all the values.
		this.clazz = meta.getMappedClass(EntityMode.POJO);
		this.id = (V) meta.getIdentifier(unproxiedModel, EntityMode.POJO);

		if (id == null)
		{
			throw new WicketRuntimeException("Could not get primary key for: "
					+ entityName);
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

	public V getId()
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
	 * @see wicket.model.IModel#getObject()
	 */
	public T getObject()
	{
		if (model == null)
		{
			attach();
		}

		if (evict)
		{
			getDao().execute(new IHibernateCallback<T>()
			{
				public T execute(Session session)
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
	public void setObject(T object)
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

	@SuppressWarnings("unchecked")
	private void attach()
	{
		model = getDao().execute(new IHibernateCallback<T>()
		{
			@SuppressWarnings("unchecked")
			public T execute(Session session)
			{
				return (T) session.load(clazz, (Serializable) id);
			}
		});

		if (unproxy && model instanceof HibernateProxy)
		{
			HibernateProxy proxy = (HibernateProxy) model;
			model = (T) proxy.getHibernateLazyInitializer().getImplementation();
		}
	}

	@SuppressWarnings("unchecked")
	public int compareTo(Object rhs)
	{
		HibernateModel model = (HibernateModel) rhs;

		if (!getId().equals(model.getId()))
		{
			return ((Comparable) getId()).compareTo(model.getId());
		}

		return getClazz().getName().compareTo(model.getClazz().getName());
	}

	@Override
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

	@Override
	public int hashCode()
	{
		return getClazz().getName().hashCode() ^ getId().hashCode();
	}

	private IHibernateDao getDao()
	{
		return dao.getObject();
	}
}
