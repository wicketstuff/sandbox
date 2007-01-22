package wicket.contrib.beanpanels.editor;

import wicket.Component;
import wicket.contrib.beanpanels.BeanModel;
import wicket.contrib.beanpanels.IPropertyMeta;

public interface IPropertyEditor {
	
	Component create( String componentId, IPropertyMeta propertyMeta, BeanModel beanModel );
	
	boolean isBaseType( Class type );
}
