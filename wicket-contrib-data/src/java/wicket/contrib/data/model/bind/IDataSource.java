package wicket.contrib.data.model.bind;

import java.util.List;

import wicket.contrib.data.model.sandbox.IOrderedList;
import wicket.model.IModel;

/**
 * A way to abstract all the details of a data connection away from components
 * that must interact with that data. Each datasource represents one list of
 * homogeneous objects.
 * 
 * @author Phil Kulak
 */
public interface IDataSource
{
	/**
	 * @return a list of items that should be displayed by a component
	 */
	public IOrderedList getList();
	
	/**
	 * Updates the persistent state of the given object.
	 */
	public void update(Object entity);
	
	/**
	 * Deletes the object from the database.
	 * 
	 * @param entity the object to delete
	 */
	public void delete(Object entity);
	
	/**
	 * @param c the class of the object to search for
	 * @return all persistent instances of the class
	 */
	public List findAll(Class c);
	
	/**
	 * Wraps the give object in an IModel. The model returned MUST properly
	 * override equals(), otherwise row editing will not be supported.
	 * 
	 * @param entity the object to wrap
	 * @return the object wraped in a detachable model
	 */
	public IModel wrap(Object entity);
	
	/**
	 * @return a list of {@link EntityField}s for the object
	 */
	public List getFields();
	
	/**
	 * A class loosly based on Hibernate's Type object, but not dependent on
	 * any one persistance framework.
	 */
	public class EntityField
	{
		/** a field that links to another mapped entitiy: 
		    manyToOne or oneToOne */
		public static final int ENTITY = 1;
		
		/** a field that links to a collection */
		public static final int COLLECTION = 2;
		
		/** a field that links to a standard field: string, boolean, etc */
		public static final int FIELD = 4;
		
		String name;
		Class clazz;
		int type;
		
		public EntityField(String name, Class clazz, int type)
		{
			this.name = name;
			this.clazz = clazz;
			this.type = type;
		}

		public Class getClazz()
		{
			return clazz;
		}

		public String getName()
		{
			return name;
		}
		
		public int getType()
		{
			return type;
		}
	}
}
