package wicket.contrib.beanpanels.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class PropertyUtil {

	private static final Log log = LogFactory.getLog(PropertyUtil.class);
	
	/**
	 * Returns the getter method for the specified Field instance
	 * @param clazz
	 * @param field
	 * @return
	 */
	public static Method getter( Class clazz, Field field ) { 
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
	public static Method setter( Class clazz, Field field ) { 
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
