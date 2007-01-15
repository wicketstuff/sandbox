package 	wicket.extensions.markup.html.beanedit;

import java.lang.reflect.Field;

import wicket.model.Model;

public class AnnotationDrivenPropertyMeta extends PropertyMeta {

	public AnnotationDrivenPropertyMeta(Field field, int ndx, boolean readOnly) {
		super(field, ndx, readOnly);
	}
	
	protected void init(Field field) {
		Label annotation = field.getAnnotation(Label.class);
		if (annotation != null && annotation.value() != null)
			this.label = new Model(annotation.value());
	}

}
