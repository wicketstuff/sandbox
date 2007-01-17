package wicket.contrib.beanpanels;

import java.lang.reflect.Field;

public class AnnotatedPropertiesProvider extends PropertiesProvider {

	protected IPropertyMeta createPropertyMeta(Class clazz, Field field, int index) { 

		PropertyMeta meta = (PropertyMeta) super.createPropertyMeta(clazz, field, index);
		Label annotation = field.getAnnotation(Label.class);
		if( annotation != null ) { 
			meta.setLabel(annotation.value());
		}
		
		return meta;
	}
}
 