package wicket.contrib.data.model.hibernate.sandbox;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.type.Type;

import wicket.contrib.data.model.bind.IDataSource;
import wicket.contrib.data.model.hibernate.sandbox.IHibernateDao.IHibernateCallback;
import wicket.contrib.data.model.sandbox.OrderedList;
import wicket.model.IModel;

public class HibernateDataSource implements IDataSource
{
	private IHibernateDao dao;
	private OrderedList list;
	
	public HibernateDataSource(String entityName, IHibernateDao dao)
	{
		list = new HibernateQueryList("FROM " + entityName + " e", dao);
		this.dao = dao;
	}
	
	public HibernateDataSource(OrderedList list, IHibernateDao dao)
	{
		this.list = list;
		this.dao = dao;
	}

	public OrderedList getList()
	{
		return list;
	}

	public void update(final Object entity)
	{
		dao.execute(new IHibernateCallback()
		{
			public Object execute(Session session)
			{
				session.update(entity);
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
		
		Class entityClass = list.get(0).getClass();
		ClassMetadata meta = factory.getClassMetadata(entityClass);
		
		String[] propNames = meta.getPropertyNames();
		List columns = new ArrayList(propNames.length);

		for (int i = 0; i < propNames.length; i++)
		{
			String prop = propNames[i];
			Type type = meta.getPropertyType(prop);
			columns.add(new EntityField(prop, type.getReturnedClass()));
		}
		return columns;
	}
}
