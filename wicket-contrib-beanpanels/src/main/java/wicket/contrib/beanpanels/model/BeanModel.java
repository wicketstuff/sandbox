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

import wicket.contrib.beanpanels.annotation.Choice;
import wicket.contrib.beanpanels.annotation.Label;
import wicket.contrib.beanpanels.util.PropertyUtil;

/**
 * Model for JavaBeans.
 *
 * @author Eelco Hillenius
 * @author Paolo Di Tommaso
 */
public class BeanModel extends AbstractBeanModel 
{
	
	private IPropertyFilter filter;
	
	/**
	 * Construct.
	 * @param bean the javabean to edit
	 */
	public BeanModel( final Serializable bean )
	{
		super(bean);
		
		if( bean == null ) {
			throw new IllegalArgumentException("bean must be not null");
		}
	}

	public BeanModel( final Object bean ) { 
		this( (Serializable)bean );
	}

	public BeanModel( final Serializable bean, IPropertyFilter filter ) { 
		this(bean);
		this.filter = filter;
	}
	/** deprecated */
	public BeanModel( final Serializable bean, final String[]  names ) { 
		this(bean);
		this.filter = new PropertyNameFilter(names);
	}
	
	/** deprecated */
	public BeanModel( final Serializable bean, final List names ) { 
		this(bean);
		this.filter = new PropertyNameFilter(names);
	}
	

	final protected List propertiesFor( ) { 
		List result = new ArrayList();
		Class clazz = getType();
		Field[] fields = clazz.getDeclaredFields();
		for( int i=0, c=(fields!=null ? fields.length : 0); i<c; i++ ) { 
			/*
			 * includes only proprties for which exists a getter method
			 */
			if( PropertyUtil.getter(clazz,fields[i]) != null ) { 
				
				if( filter != null ) { 
					int p = filter.accept(fields[i]);
					if( p != -1 ) { 
						result.add( createPropertyMeta(fields[i],p) );
					}
				}
				else { 
					result.add( createPropertyMeta(fields[i],i) );
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
	protected IPropertyMeta createPropertyMeta(Field field, int index ) { 
		/*
		 * if the setter dows not exists property is read-only
		 */
		boolean readOnly = (PropertyUtil.setter(getType(),field) == null);
		PropertyMeta meta = new PropertyMeta(field, index);
		meta.setReadOnly(readOnly);
		/*
		 * try to load Label annotation value
		 */
		Label annotation = field.getAnnotation(Label.class);
		if( annotation != null ) { 
			meta.setLabel(annotation.value());
		}
		/*
		 * Check for Choice annotation
		 */
		Choice choice = field.getAnnotation(Choice.class);
		if( choice != null ) { 
			meta.setChoices( Arrays.asList(choice.value()) );
		}
		return meta;
	}	
	
	public List getProperties() { 
		return propertiesFor();			
	}

}
