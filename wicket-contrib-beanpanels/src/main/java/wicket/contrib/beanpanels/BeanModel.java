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
package wicket.contrib.beanpanels;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import wicket.Component;
import wicket.model.IModel;

/**
 * Model for JavaBeans.
 *
 * @author Eelco Hillenius
 * @author Paolo Di Tommaso
 */
public class BeanModel implements IModel
{
	/** the java bean to edit. */
	private final Serializable bean;
	
	private List propertiesList;
	
	private List names;
	
	/**
	 * Construct.
	 * @param bean the javabean to edit
	 */
	public BeanModel( final Serializable bean )
	{
		this(bean,(List)null);
	}
	
	public BeanModel( final Object bean ) { 
		this( (Serializable)bean, (List)null );
	}
	
	/** deprecated */
	public BeanModel( final Serializable bean, final String[]  names ) { 
		this(bean, names != null ? Arrays.asList(names) : null );
	}
	
	/** deprecated */
	public BeanModel( final Serializable bean, final List names ) { 
		if (bean == null)
		{
			throw new IllegalArgumentException("bean must be not null");
		}

		this.bean = bean;
		this.names = names;


	}
 	
	/**
	 * @see wicket.model.IModel#getNestedModel()
	 */
	public IModel getNestedModel()
	{
		return null;
	}

	/**
	 * @see wicket.model.IDetachable#detach()
	 */
	public void detach()
	{
	}

	/**
	 * @see wicket.model.IModel#getObject(wicket.Component)
	 */
	public Object getObject(Component component)
	{
		return bean;
	}

	/**
	 * Throws an {@link UnsupportedOperationException} as changing the bean is not permitted.
	 * @see wicket.model.IModel#setObject(wicket.Component, java.lang.Object)
	 */
	public void setObject(Component component, Object object)
	{
		throw new UnsupportedOperationException("BeanModel is read-only");
	}
	
	/**
	 * Convenience method.
	 * @return the bean
	 */
	public Serializable getBean()
	{
		return bean;
	}
	
	public List getPropertiesList() { 
		if( propertiesList == null ) { 
			propertiesList = PropertiesProviderFactory.get().propertiesFor( bean.getClass(), new IPropertyFilter() {
				
				int c = 0;
				
				public int accept(Field field) {
					int p = names != null 
						  ? names.indexOf(field.getName()) 
						  : c++;
						  
					return p;
				} } );			
		}
		return propertiesList;
	}
	
	protected List<IPropertyMeta> properties() { return null; }
	
}
