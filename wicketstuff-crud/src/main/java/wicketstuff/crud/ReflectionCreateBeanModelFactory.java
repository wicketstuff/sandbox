package wicketstuff.crud;

import java.io.Serializable;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

/**
 * Quick create bean model factory that uses reflection to create an instance
 * using default constructor
 * 
 * @author igor.vaynberg
 * 
 */
public class ReflectionCreateBeanModelFactory implements ICreateBeanModelFactory
{
	/** name of class whose instance will be created */
	private final String className;

	/**
	 * @param clazz
	 *            class that represents the type of instance that will be
	 *            created
	 */
	public ReflectionCreateBeanModelFactory(Class<? extends Serializable> clazz)
	{
		className = clazz.getName();
	}


	/** {@inheritDoc} */
	public IModel newModel()
	{
		try
		{
			return new Model((Serializable)Class.forName(className).newInstance());
		}
		catch (Exception e)
		{
			throw new RuntimeException("Unable to instantiate instance of class " + className, e);
		}

	}

}
