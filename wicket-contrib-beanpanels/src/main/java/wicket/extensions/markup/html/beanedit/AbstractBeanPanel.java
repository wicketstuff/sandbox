/*
 * $Id$
 * $Revision$
 * $Date$
 *
 * ====================================================================
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package wicket.extensions.markup.html.beanedit;


import wicket.markup.html.panel.Panel;

/**
 * Abstract Panel for generic bean displaying/ editing. It's here to provide the constructors,
 * but does nothing else.
 *
 * @author Eelco Hillenius
 * @author Paolo Di Tommaso
 */
public abstract class AbstractBeanPanel extends Panel
{
	/** boolean types. */
	protected static final Class[] BOOL_TYPES = new Class[] { Boolean.class, Boolean.TYPE };

	protected static final Class[] DATE_TYPES = new Class[] { java.util.Date.class, java.sql.Date.class };
	
	/** basic java types. */
	protected static final Class[] BASE_TYPES = new Class[] { String.class, Number.class, Integer.TYPE, Double.TYPE, Long.TYPE, Float.TYPE, Short.TYPE, Byte.TYPE };

	/**
	 * Construct.
	 * @param id component id
	 * @param beanModel model with the JavaBean to be edited or displayed
	 */
	public AbstractBeanPanel(String id, BeanModel beanModel)
	{
		super(id, beanModel);
		if (beanModel == null)
		{
			throw new NullPointerException("argument beanModel must not be null");
		}
	}

	/**
	 * Does isAssignableFrom check on given class array for given type.
	 * @param types array of types
	 * @param type type to check against
	 * @return true if one of the types matched
	 */
	protected boolean checkAssignableFrom(Class[] types, Class type)
	{
		int len = types.length;
		for (int i = 0; i < len; i++)
		{
			if (types[i].isAssignableFrom(type))
			{
				return true;
			}
		}
		return false;
	}	

	
}
