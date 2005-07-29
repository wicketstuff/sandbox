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

public class HibernateDataSource implements IListDataSource
{
	private IHibernateDao dao;
	private OrderedPageableList list;
	private Class entity;
	
	public HibernateDataSource(Class entity, IHibernateDao dao)
	{
		list = new HibernateQueryList("FROM " + entity.getName() + " e", dao);
		this.dao = dao;
		this.entity = entity;
	}
	
	public HibernateDataSource(Class entity, OrderedPageableList list, 
			IHibernateDao dao)
	{
		this.entity = entity;
		this.list = list;
		this.dao = dao;
	}

	public OrderedPageableList getList()
	{
		return list;
	}

	public void merge(final Object entity)
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
	
	public List findAll(final Class c)
	{
		return new HibernateQueryList("FROM " + c.getName(), dao);
	}

	public IModel wrap(Object entity)
	{
		return new HibernateModel(entity, dao);
	}

	public List getFields()
	{
		SessionFactory factory = (SessionFactory) dao.execute(new IHibernateCallback()
		{
			public Object execute(Session session)
			{
				return session.getSessionFactory();
			}
		});

		ClassMetadata meta = factory.getClassMetadata(entity);
		
		String[] propNames = meta.getPropertyNames();
		List columns = new ArrayList(propNames.length);

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
