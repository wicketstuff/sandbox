package wicket.extensions.markup.html.beanedit;

/**
 * 
 * @author Paolo Di Tommaso
 *
 */
public interface IPropertyFilter {

	/**
	 * Callback to filter properties in a BeanModel 
	 * 
	 * @param propertyName the property name 
	 * @return returning -1 will not include the porperty in the result list, otherwise the 
	 * return value is interpreted as the index the the result properties list view
	 */
	int accept( final String propertyName );
	
}
