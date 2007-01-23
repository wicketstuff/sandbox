package wicket.contrib.beanpanels.model;

import java.lang.reflect.Field;

/**
 * 
 * @author Paolo Di Tommaso
 *
 */
public interface IPropertyFilter {

	/**
	 * Callback to filter properties in a BeanModel 
	 * 
	 * @param field the property field
	 * @return returning -1 will not include the porperty in the result list, otherwise the 
	 * return value is interpreted as the index the the result properties list view
	 */
	int accept( final Field field );
	
}
