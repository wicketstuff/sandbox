package wicket.contrib.data.model.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.type.Type;

import wicket.contrib.data.model.OrderedPageableList;
import wicket.contrib.data.model.bind.IListDataSource;
import wicket.contrib.data.model.hibernate.IHibernateDao.IHibernateCallback;
import wicket.model.IModel;

public class HibernateDataSource<T, V> implements IListDataSource<T>
{
	private IHibernateDao dao;
	private OrderedPageableList<T> list;
	private Class<T> entity;
	
	public HibernateDataSource(Class<T> entity, IHibernateDao dao)
	{
		list = new HibernateQueryList<T>("FROM " + entity.getName() + " e", dao);
		this.dao = dao;
		this.entity = entity;
	}
	
	public HibernateDataSource(Class<T> entity, OrderedPageableList<T> list, 
			IHibernateDao dao)
	{
		this.entity = entity;
		this.list = list;
		this.dao = dao;
	}

	public OrderedPageableList<T> getList()
	{
		return list;
	}

	@SuppressWarnings("unchecked")
	public void merge(final T entity)
	{
		dao.execute(new IHibernateCallback()
		{
			public Object execute(Session session)
			{
				session.saveOrUpdate(entity);
				return null;
			}
		});
	}

	@SuppressWarnings("unchecked")
	public void delete(final Object entity)
	{
		dao.execute(new IHibernateCallback()
		{
			public Object execute(Session session)
			{
				session.delete(entity);
				return null;
			}
		});
	}
	
	public List<T> findAll(final Class c)
	{
		return new HibernateQueryList<T>("FROM " + c.getName(), dao);
	}

	public IModel<T> wrap(T entity)
	{
		return new HibernateModel<T, V>(entity, dao);
	}

	public List<EntityField> getFields()
	{
		SessionFactory factory = (SessionFactory) dao.execute(new IHibernateCallback<SessionFactory>()
		{
			public SessionFactory execute(Session session)
			{
				return session.getSessionFactory();
			}
		});

		ClassMetadata meta = factory.getClassMetadata(entity);
		
		String[] propNames = meta.getPropertyNames();
		List<EntityField> columns = new ArrayList<EntityField>(propNames.length);

		for (int i = 0; i < propNames.length; i++)
		{
			String prop = propNames[i];
			Type type = meta.getPropertyType(prop);
			
			// Get the type;
			int fieldType = EntityField.FIELD;
			if (type.isCollectionType())
			{
				fieldType = EntityField.COLLECTION;
			}
			else if (type.isEntityType())
			{
				fieldType = EntityField.ENTITY;
			}
			
			columns.add(new EntityField(prop, type.getReturnedClass(), fieldType));
		}
		return columns;
	}
}
