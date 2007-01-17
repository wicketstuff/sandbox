package wicket.contrib.beanpanels;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @author Paolo Di Tommaso
 *
 */
public class PropertiesProvider implements IPropertiesProvider {

	private static final transient Log log = LogFactory.getLog(PropertiesProvider.class);

	public List propertiesFor( final Class clazz ) {
		return propertiesFor(clazz,(IPropertyFilter)null);
	}
	
	public List propertiesFor( final Class clazz, final String[] attributes ) { 
		return propertiesFor( clazz, attributes!=null ? Arrays.asList(attributes) : null );
	}
	
	public List propertiesFor( final Class clazz, final List attributes ) { 
		
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

	public List propertiesFor( final Class clazz, final IPropertyFilter filter ) { 
		List result = new ArrayList();
		Field[] fields = clazz.getDeclaredFields();
		for( int i=0, c=(fields!=null ? fields.length : 0); i<c; i++ ) { 
			/*
			 * includes only proprties for which exists a getter method
			 */
			if( getter(clazz,fields[i]) != null ) { 
				
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
		boolean readOnly = (setter(clazz,field) == null);
		PropertyMeta meta = new PropertyMeta(field, index);
		meta.setReadOnly(readOnly);
		return meta;
	}
	
	/**
	 * Returns the getter method for the specified Field instance
	 * @param clazz
	 * @param field
	 * @return
	 */
	protected static Method getter( Class clazz, Field field ) { 
		if( clazz == null || field == null ) { return null; }
		
		String methodName = "get" + firstCharUp(field.getName());
		try {
			return clazz.getMethod(methodName, new Class[] { } );
		} 
		catch (Exception e) {
			log.warn("Unabled to find method '" + methodName + "' in class: " + clazz );
			return null;
		}
		
	}

	/**
	 * Returns the setter method for the spcified Field instance
	 * @param clazz
	 * @param field
	 * @return
	 */
	protected static Method setter( Class clazz, Field field ) { 
		if( clazz == null || field == null ) { return null; }
		
		String methodName = "set" + firstCharUp(field.getName());
		try {
			return clazz.getMethod(methodName, new Class[] { field.getType() } );
		} 
		catch (Exception e) {
			log.warn("Unabled to find method '" + methodName + "' in class: " + clazz );
			return null;
		}
	}
	
	
	private static String firstCharUp( String str ) { 
		if( str == null ) { 
			return null;
		}
		
		if( str.length() < 2 ) { 
			return str;
		}
		
		return str.substring(0,1).toUpperCase() + str.substring(1);
	}	
}
