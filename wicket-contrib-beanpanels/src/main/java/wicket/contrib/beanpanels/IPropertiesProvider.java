package wicket.contrib.beanpanels;

import java.util.List;

public interface IPropertiesProvider {

	List propertiesFor( final Class clazz );
	List propertiesFor( final Class clazz, final String[] attributes );
	List propertiesFor( final Class clazz, final List attributes );
	List propertiesFor( final Class clazz, final IPropertyFilter filter );
	
}
