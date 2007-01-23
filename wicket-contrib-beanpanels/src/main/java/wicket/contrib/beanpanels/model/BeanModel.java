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
package wicket.contrib.beanpanels.model;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import wicket.Component;
import wicket.contrib.beanpanels.annotation.Label;
import wicket.contrib.beanpanels.util.PropertyUtil;
import wicket.model.IModel;

/**
 * Model for JavaBeans.
 *
 * @author Eelco Hillenius
 * @author Paolo Di Tommaso
 */
public class BeanModel extends AbstractBeanModel
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
		super(bean);
		
		if (bean == null)
		{
			throw new IllegalArgumentException("bean must be not null");
		}

		this.bean = bean;
		this.names = names;


	}
 	
	final protected List propertiesFor( final Class clazz ) {
		return propertiesFor(clazz,(IPropertyFilter)null);
	}
	
	final protected List propertiesFor( final Class clazz, final String[] attributes ) { 
		return propertiesFor( clazz, attributes!=null ? Arrays.asList(attributes) : null );
	}
	
	final protected List propertiesFor( final Class clazz, final List attributes ) { 
		
		IPropertyFilter filter = null;

		if( attributes != null ) { 
			filter = new IPropertyFilter() {
				public int accept(Field field) {
					return attributes.indexOf(field.getName());
				} 
			};
		}
		
		return propertiesFor(clazz,filter);
	}

	final protected List propertiesFor( final Class clazz, final IPropertyFilter filter ) { 
		List result = new ArrayList();
		Field[] fields = clazz.getDeclaredFields();
		for( int i=0, c=(fields!=null ? fields.length : 0); i<c; i++ ) { 
			/*
			 * includes only proprties for which exists a getter method
			 */
			if( PropertyUtil.getter(clazz,fields[i]) != null ) { 
				
				if( filter != null ) { 
					int p = filter.accept(fields[i]);
					if( p != -1 ) { 
						result.add( createPropertyMeta(clazz,fields[i],p) );
					}
				}
				else { 
					result.add( createPropertyMeta(clazz,fields[i],i) );
				}
			}
		}
		
		Collections.sort(result,new Comparator() {
			public int compare(Object o1, Object o2) {
				IPropertyMeta p1 = (IPropertyMeta) o1;
				IPropertyMeta p2 = (IPropertyMeta) o2;

				return p1.getIndex()<p2.getIndex() ? -1 : p1.getIndex() > p2.getIndex() ? 1 : 0;
			} } );

		return result;
	}
	/**
	 * Property meta info factory method. Override this method to provide alternative meta information providing strategy.
	 * 
	 * @param field The field istance
	 * @param index
	 * @return
	 */
	protected IPropertyMeta createPropertyMeta( Class clazz, Field field, int index ) { 
		/*
		 * if the setter dows not exists property is read-only
		 */
		boolean readOnly = (PropertyUtil.setter(clazz,field) == null);
		PropertyMeta meta = new PropertyMeta(field, index);
		meta.setReadOnly(readOnly);
		/*
		 * try to load Label annotation value
		 */
		Label annotation = field.getAnnotation(Label.class);
		if( annotation != null ) { 
			meta.setLabel(annotation.value());
		}

		return meta;
	}	
	
	public List getProperties() { 
		return propertiesFor( bean.getClass(), new IPropertyFilter() {
				
				int c = 0;
				
				public int accept(Field field) {
					int p = names != null 
						  ? names.indexOf(field.getName()) 
						  : c++;
						  
					return p;
				} } );			
	}
	
}
