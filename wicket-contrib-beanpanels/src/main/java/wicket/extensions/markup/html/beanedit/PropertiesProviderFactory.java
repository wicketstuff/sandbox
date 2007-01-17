package wicket.extensions.markup.html.beanedit;

import wicket.WicketRuntimeException;

public class PropertiesProviderFactory {

	static private Class propertiesProviderClass = PropertiesProvider.class;

	static private IPropertiesProvider propertiesProvider;
	
	public final static void registerPropertiesProvider( Class providerClass ) { 
		
		if( !IPropertiesProvider.class.isAssignableFrom(providerClass) ) { 
			throw new WicketRuntimeException("Invalid PropertiesProviderClass: " + providerClass );
		}
		
		propertiesProviderClass = providerClass;
		propertiesProvider = null;
	}
	
	
	public static IPropertiesProvider get() { 
		if( propertiesProvider == null ) { 
			try {
				propertiesProvider = (IPropertiesProvider) propertiesProviderClass.newInstance();
			} catch (Exception e) {
				throw new WicketRuntimeException("Unable to instantiate the properties provider class: " + propertiesProviderClass );
			}
		}
		return propertiesProvider;
	}

	
}
