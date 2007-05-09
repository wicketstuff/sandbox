package org.wicketstuff.hibernate.behavior;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.behavior.AbstractBehavior;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.hibernate.validator.Length;
import org.hibernate.validator.NotEmpty;
import org.hibernate.validator.NotNull;

/**
 * Updates behavior of wicket components based on hibernate annotations.
 * 
 * TODO: attach client side javascript as well
 * 
 * @author rsonnek
 */
public class HibernateAnnotationBehavior extends AbstractBehavior {

	@Override
	public void bind(Component component) {
		super.bind(component);

		HibernateAnnotationPropertyModel model = (HibernateAnnotationPropertyModel) component.getModel();
		String fieldName = model.propertyExpression();
		Class type = model.getTargetClass();
		try {
			Field field = type.getDeclaredField(fieldName);
			Annotation[] annotations = field.getAnnotations();
			if (null != annotations) {
				for (int y = 0; y < annotations.length; y++) {
					Annotation annotation = annotations[y];
					if (annotation.annotationType().isAssignableFrom(NotNull.class)
							|| annotation.annotationType().isAssignableFrom(NotEmpty.class)) {
						((FormComponent)component).setRequired(true);
					} else if (annotation.annotationType().isAssignableFrom(Length.class)) {
						int max = ((Length)annotation).max();
						component.add(new AttributeModifier("maxlength", new Model(Integer.toString(max))));
					}
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("Error binding validator for component model: " + type.getName() + "."+ fieldName, e);
		}
	}

	/**
	 * 
	 * @author rsonnek
	 * @deprecated this is a hack just to expose the property expression for inspection
	 */
	public static class HibernateAnnotationPropertyModel extends PropertyModel {

		public HibernateAnnotationPropertyModel(Object modelObject, String expression) {
			super(modelObject, expression);
		}

		@Override
		public String propertyExpression() {
			return super.propertyExpression();
		}

		public Class getTargetClass() {
			return getTarget().getClass();
		}
	}
}
