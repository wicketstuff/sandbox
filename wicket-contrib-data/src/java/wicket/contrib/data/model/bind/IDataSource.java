package wicket.contrib.data.model.bind;

import java.util.List;

import wicket.contrib.data.model.sandbox.OrderedList;
import wicket.model.IModel;

public interface IDataSource
{
	public OrderedList getList();
	
	public void update(Object entity);
	
	public void delete(Object entity);
	
	public IModel wrap(Object entity);
	
	public List getFields();
	
	public class EntityField
	{
		String name;
		Class clazz;
		
		public EntityField(String name, Class clazz)
		{
			this.name = name;
			this.clazz = clazz;
		}

		public Class getClazz()
		{
			return clazz;
		}

		public String getName()
		{
			return name;
		}
	}
}
