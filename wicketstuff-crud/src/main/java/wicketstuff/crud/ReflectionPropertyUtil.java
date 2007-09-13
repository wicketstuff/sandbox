package wicketstuff.crud;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.apache.wicket.model.Model;

import wicketstuff.crud.property.EnumChoiceProperty;
import wicketstuff.crud.property.StringProperty;

public class ReflectionPropertyUtil
{
	public static Property newProperty(Class clazz, String property)
	{
		Class type = null;
		boolean writeable = false;

		// find property type
		final String getterName = "get" + property.substring(0, 1).toUpperCase()
				+ property.substring(1);
		Method getter = findMethod(clazz, getterName);

		if (getter != null)
		{
			type = getter.getReturnType();
		}
		else
		{
			// fail over to looking up a property
			Field field = findField(clazz, property);
			if (field != null)
			{
				type = field.getType();
			}
			else
			{
				throw new IllegalArgumentException("Property `" + property
						+ "` not found in class `" + clazz.getName() + "`");
			}
		}

		// check for setter
		final String setterName = "set" + property.substring(0, 1).toUpperCase()
				+ property.substring(1);
		Method setter = findMethod(clazz, setterName, type);

		if (setter != null || getter == null)
		{
			writeable = true;
		}

		if (writeable)
		{
			if (String.class.equals(type))
			{
				return new StringProperty(property, new Model(property));
			}
			else if (Enum.class.isAssignableFrom(type))
			{
				return new EnumChoiceProperty(property, new Model(property), type);
			}
			else
			{
				throw new IllegalStateException("Unsupported property type: " + type.getName());
			}

		}
		else
		{
			throw new IllegalStateException("Property `" + property + "` is not writeable");
		}
	}

	private static Method findMethod(Class clazz, String name, Class... params)
	{
		try
		{
			return clazz.getMethod(name, params);
		}
		catch (SecurityException e)
		{
			return null;
		}
		catch (NoSuchMethodException e)
		{
			return null;
		}
	}

	private static Field findField(Class clazz, String name)
	{
		Field field = null;
		try
		{
			field = clazz.getDeclaredField(name);
		}
		catch (SecurityException e)
		{
		}
		catch (NoSuchFieldException e)
		{
		}
		if (field == null)
		{
			try
			{
				field = clazz.getField(name);
			}
			catch (SecurityException e)
			{
			}
			catch (NoSuchFieldException e)
			{
			}
		}
		return field;
	}

}
