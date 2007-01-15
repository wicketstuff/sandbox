package wicket.extensions.markup.html.beanedit;

import java.lang.reflect.Field;

import wicket.model.ResourceModel;

public class PropertiesDrivenPropertyMeta extends PropertyMeta {

	public PropertiesDrivenPropertyMeta(Field field, int ndx, boolean readOnly) {
		super(field, ndx, readOnly);
	}
	
	protected void init(Field field) {
		
		this.label = new ResourceModel(field.getName(), field.getName());
	}

}
