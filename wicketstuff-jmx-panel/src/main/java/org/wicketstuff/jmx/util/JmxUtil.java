/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wicketstuff.jmx.util;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.management.Attribute;
import javax.management.MBeanAttributeInfo;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.wicketstuff.jmx.markup.html.EditorPanel;

/**
 * Jmx Utility Helper class
 * 
 * @author Gerolf Seitz
 * 
 */
public class JmxUtil
{
	private static final Set<Class<?>> writableTypes = new HashSet<Class<?>>(Arrays
			.asList(new Class<?>[] { Boolean.class, Byte.class, Character.class, Short.class,
					Integer.class, Long.class, Float.class, Double.class, String.class }));

	/**
	 * This method returns the {@link Class} object representing the type, which
	 * is passed as a {@link String}.
	 * 
	 * @param type
	 *            the {@link String} to be evaluated
	 * @return the corresponding {@link Class} to the value of the given String
	 */
	@SuppressWarnings("unchecked")
	public static Class getType(String type)
	{
		try
		{
			if ("boolean".equals(type))
			{
				return boolean.class;
			}
			else if ("int".equals(type))
			{
				return int.class;
			}
			else if ("long".equals(type))
			{
				return long.class;
			}
			else if ("short".equals(type))
			{
				return short.class;
			}
			else if ("char".equals(type))
			{
				return char.class;
			}
			else if ("byte".equals(type))
			{
				return byte.class;
			}
			else if ("float".equals(type))
			{
				return float.class;
			}
			else if ("double".equals(type))
			{
				return double.class;
			}
			else
			{
				return Class.forName(type);
			}
		}
		catch (ClassNotFoundException e)
		{
			// TODO: return null, logger.error(e)
			throw new WicketRuntimeException(e);
		}
	}

	/**
	 * Returns a {@link String} representation of the given object.
	 * 
	 * @param object
	 *            the object to build the String of
	 * @return a {@link String} representation of the given object.
	 */
	@SuppressWarnings("unchecked")
	public static String toString(Object object)
	{
		if (object == null)
		{
			return "null";
		}
		Class c = object.getClass();

		StringBuffer sb = new StringBuffer();
		// convert maps to { key1: value1, key2: value2, ... }
		if (Map.class.isAssignableFrom(c))
		{
			Map map = (Map)object;
			sb.append("{ ");
			for (Iterator iter = map.keySet().iterator(); iter.hasNext();)
			{
				Object key = iter.next();
				sb.append(toString(key));
				sb.append(": ");
				sb.append(toString(map.get(key)));
				if (iter.hasNext())
				{
					sb.append(",");
				}
				sb.append(" ");
			}
			sb.append("}");
		}
		// convert collections and arrays to [ value1, value2, value3, ... ]
		else if (Collection.class.isAssignableFrom(c) || Object[].class.isAssignableFrom(c))
		{
			Object[] array = (Object[])(c.isArray() ? object : ((Collection)object).toArray());
			sb.append("[ ");
			for (int i = 0; i < array.length; i++)
			{
				sb.append(toString(array[i]));
				if (i < array.length - 1)
				{
					sb.append(",");
				}
				sb.append(" ");
			}
			sb.append("]");
		}
		else
		{
			sb.append(object.toString());
		}
		return sb.toString();
	}

	/**
	 * Checks whether any given type is considered a writeable type.
	 * 
	 * @param clazz
	 *            the type to be evaluated
	 * @return true if the parameter is considered a writeable parameter.
	 */
	public static boolean isWriteableType(Class<?> clazz)
	{
		return clazz.isPrimitive() || writableTypes.contains(clazz);
	}

	/**
	 * This method is used to create an editor for the given
	 * {@link MBeanAttributeInfo} based on properties like
	 * {@link MBeanAttributeInfo#isReadable()},
	 * {@link MBeanAttributeInfo#isWritable()} and
	 * {@link MBeanAttributeInfo#getType()}. <br/>
	 * The returned {@link Component} is created according to the accessibility
	 * of the attribute:
	 * <ul>
	 * <li>!read && !write: empty {@link Label}</li>
	 * <li>read && !write: {@link Label} that renders the value of attribute</li>
	 * <li>read && write: {@link EditorPanel}</li>
	 * </ul>
	 * 
	 * @param id
	 *            the id of the editor
	 * @param mbean
	 *            the {@link JmxMBeanWrapper}
	 * @param attribute
	 *            the {@link MBeanAttributeInfo} to process
	 * @return a {@link Component} that may allow editing the attribute
	 */
	public static Component getEditor(String id, final JmxMBeanWrapper mbean,
			final MBeanAttributeInfo attribute)
	{
		Component editor;
		if (!attribute.isReadable())
		{
			editor = new Label("editor");
		}
		else
		{
			// Create a model to bind the editor component to the bean.
			IModel model = newAttributeModel(mbean, attribute);

			// get the type of the attribute
			final Class<?> type = getType(attribute.getType());

			if (!(attribute.isWritable() && isWriteableType(type)))
			{
				editor = new Label(id, toString(model.getObject()));
			}
			else
			{
				editor = new EditorPanel("editor", model, new Model(attribute.getName()), type,
						true);
			}
		}
		editor.add(new AttributeModifier("class", true, new AbstractReadOnlyModel()
		{
			private static final long serialVersionUID = 1L;

			@Override
			public Object getObject()
			{
				return attribute.isWritable() ? "writable" : "readonly";
			}
		})
		{
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isEnabled(Component component)
			{
				return attribute.isReadable() || attribute.isWritable();
			}
		});
		return editor;
	}

	public static IModel newAttributeModel(final JmxMBeanWrapper mbean,
			final MBeanAttributeInfo attribute)
	{
		return new Model()
		{
			private static final long serialVersionUID = 1L;


			public void setObject(final Object object)
			{
				mbean.setAttribute(new Attribute(attribute.getName(), object));
			}

			@Override
			public Serializable getObject()
			{
				return (Serializable)mbean.getAttribute(new Attribute(attribute.getName(), null));
			}

		};
	}

	public static void main(String[] args)
	{
		Map map = new HashMap();
		map.put("abc", "def");
		map.put(12, 15);
		System.out.println(toString(map));
		System.out.println(toString(new String[] { "1", "4", "aloa", null }));
		System.out.println(Number.class.isAssignableFrom(int.class));
	}
}
