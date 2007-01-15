package wicket.extensions.markup.html.beanedit;

import java.lang.reflect.Field;

public class PropertyMetaFactory {
	
	public static IPropertyMeta getPropertyMetaImplementation(Field field, int ndx, boolean readOnly)
	{
		return new PropertiesDrivenPropertyMeta(field, ndx, readOnly);
	}

}
