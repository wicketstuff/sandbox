package wicket.contrib.beanpanels.editor;

import wicket.Component;
import wicket.contrib.beanpanels.model.BeanModel;
import wicket.contrib.beanpanels.model.IPropertyMeta;

public interface IPropertyEditor {
	
	Component create( String componentId, IPropertyMeta propertyMeta, BeanModel beanModel );
	
	boolean isBaseType( Class type );
}
