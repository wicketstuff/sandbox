package wicket.contrib.data.model.bind;

import java.util.List;

/**
 * Provides the functionality needed to interact with a datastore in the
 * context of a single entity class.
 * 
 * @author Phil Kulak
 */
public interface IObjectDataSource extends IDataSource
{
	/**
	 * @return a list of {@link EntityField}s for the object
	 */
	public List/*<EntityField>*/ getFields();
	
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
