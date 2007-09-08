package wicketstuff.crud;

import java.io.Serializable;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

public class ReflectionCreateBeanModelFactory implements ICreateBeanModelFactory
{
	private final String className;

	public ReflectionCreateBeanModelFactory(Class<? extends Serializable> clazz)
	{
		className = clazz.getName();
	}


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
