package org.wicketstuff.hibernate;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.validation.validator.StringValidator;
import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;

public class HibernateAnnotationComponentConfigurator {
	private static Map configs = new HashMap() {{
		put(NotNull.class, new HibernateAnnotationConfig() {
			public void onAnnotatedComponent(Annotation annotation, FormComponent component) {
				component.setRequired(true);
			}
		});
		put(Length.class, new HibernateAnnotationConfig() {
			public void onAnnotatedComponent(Annotation annotation, FormComponent component) {
				int max = ((Length)annotation).max();
				component.add(new AttributeModifier("maxlength", new Model(Integer.toString(max))));
				component.add(StringValidator.maximumLength(max));
			}
		});
	}};

	public void configure(Component component) {
		if (!isApplicableFor(component)) {
			return;
		}
		FormComponent formComponent = (FormComponent)component;
		PropertyModel propertyModel = (PropertyModel) component.getModel();
		String fieldName = propertyModel.getPropertyExpression();
		Class type = propertyModel.getTarget().getClass();
		try {
			Field field = type.getDeclaredField(fieldName);
			Annotation[] annotations = field.getAnnotations();
			if (null != annotations) {
				for (int y = 0; y < annotations.length; y++) {
					Annotation annotation = annotations[y];
					HibernateAnnotationConfig config = (HibernateAnnotationConfig) configs.get(annotation.getClass());
					if (null != config) {
						config.onAnnotatedComponent(annotation, formComponent);
					}
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("Error binding validator for component model: " + type.getName() + "."+ fieldName, e);
		}
	}

	private boolean isApplicableFor(Component component) {
		IModel model = component.getModel();
		if (null == model) {
			return false;
		}
		if (!(model instanceof PropertyModel)) {
			return false;
		}
		if (!(component instanceof FormComponent)) {
			return false;
		}

		return true;
	}

	private static interface HibernateAnnotationConfig {
		void onAnnotatedComponent(Annotation annotation, FormComponent component);
	}
}
