package wicketstuff.crud;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.model.Model;

import wicketstuff.crud.property.EnumChoiceProperty;
import wicketstuff.crud.property.StringProperty;


public class ReflectionPropertySource implements PropertySource
{
	private List<Property> properties = new ArrayList<Property>();

	public ReflectionPropertySource(Class clazz)
	{
		for (Method method : clazz.getMethods())
		{
			final String methodName = method.getName();
			if (methodName.startsWith("get") && methodName.length() > 3)
			{
				// check that we also have a setter
				boolean haveSetter = true;
				try
				{
					clazz.getMethod("set" + methodName.substring(3), method.getReturnType());
				}
				catch (SecurityException e)
				{
					throw new RuntimeException(e);
				}
				catch (NoSuchMethodException e)
				{
					haveSetter = false;
				}

				if (haveSetter)
				{

					final String propName = methodName.substring(3, 4).toLowerCase()
							+ methodName.substring(4);

					if (method.getReturnType().equals(String.class))
					{
						properties.add(new StringProperty(propName, new Model(propName)));
					}

					if (Enum.class.isAssignableFrom(method.getReturnType()))
					{
						properties.add(new EnumChoiceProperty(propName, new Model(propName), method
								.getReturnType()));
					}
				}
			}
		}
	}

	public List<Property> getProperties()
	{
		return properties;
	}
}
