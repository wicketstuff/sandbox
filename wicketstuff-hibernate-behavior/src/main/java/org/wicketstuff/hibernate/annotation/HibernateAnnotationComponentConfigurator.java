package org.wicketstuff.hibernate.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.application.IComponentOnBeforeRenderListener;
import org.apache.wicket.behavior.AbstractBehavior;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.IPropertyReflectionAwareModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.validation.validator.StringValidator;
import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Configure a Wicket <code>Component</code> based on Hibernate annotations.
 * <p>
 * Inspects the <code>Model</code> of a <code>FormComponent</code> and 
 * configures the <code>Component</code> according to the declared Hibernate Annotations 
 * used on the model object.  <br />
 * <strong>NOTE:</strong> This means the
 * <code>Component</code>'s <code>Model</code> <em>must</em> be known 
 * when {@link #configure(Component) configuring} a <code>Component</code>.
 * </p>
 *
 * <p>
 * This object can be used as a <code>Behavior</code> to configure a single <code>Component</code>. <br />
 * <strong>NOTE:</strong> this object is <em>stateless</em>, and the same instance can be reused to
 * configure multiple <code>Component</code>s.
 * </p>
 * <pre>
 * public class MyWebPage extends WebPage {
 *   public MyWebPage() {
 *     TextField name = new TextField("id", new PropertyModel(user, "name");
 *     name.addBehavior(new HibernateAnnotationComponentConfigurator());
 *
 *     add(name);
 *   }
 * }
 * </pre>
 *
 * <p>
 * This object can also be used as a component listener that will automatically configure <em>all</em>
 * <code>FormComponent</code>s based on Hibernate annotations. This ensures that an entire application
 * respects annotations without adding custom <code>Validator</code>s or <code>Behavior</code>s to each 
 * <code>FormComponent</code>.
 * </p>
 * <pre>
 * public class MyApplication extends WebApplication {
 *   public void init() {
 *     addComponentOnBeforeRenderListener(new HibernateAnnotationComponentConfigurator());
 *   }
 * }
 * </pre>
 *
 * @see http://jroller.com/page/wireframe/?anchor=hibernateannotationcomponentconfigurator
 * @see http://jroller.com/page/wireframe/?anchor=hibernate_annotations_and_wicket
 */
@SuppressWarnings("serial")
public class HibernateAnnotationComponentConfigurator extends AbstractBehavior implements IComponentOnBeforeRenderListener {
	private static final Logger LOGGER = LoggerFactory.getLogger(HibernateAnnotationComponentConfigurator.class);

	@SuppressWarnings("unchecked")
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

	@Override
	public void bind(Component component) {
		super.bind(component);

		configure(component);
	}

	public void onBeforeRender(Component component) {
		if (!component.hasBeenRendered()) {
			configure(component);
		}
	}

	void configure(Component component) {
		if (!isApplicableFor(component)) {
			return;
		}
		FormComponent formComponent = (FormComponent)component;
		IPropertyReflectionAwareModel propertyModel = (IPropertyReflectionAwareModel) component.getModel();
		for (Annotation annotation : getAnnotations(propertyModel)) {
			Class<? extends Annotation> annotationType = annotation.annotationType();
			HibernateAnnotationConfig config = (HibernateAnnotationConfig) configs.get(annotationType);
			if (null != config) {
				config.onAnnotatedComponent(annotation, formComponent);
			}
		}
	}

	private Collection<Annotation> getAnnotations(IPropertyReflectionAwareModel propertyModel) {
		Field field = propertyModel.getPropertyField();
		if (field == null) {
			LOGGER.warn("Unable to find annotations for model: " + propertyModel);
			return Collections.EMPTY_LIST;
		}
		return Arrays.asList(field.getAnnotations());
	}

	private boolean isApplicableFor(Component component) {
		if (!(component instanceof FormComponent)) {
			return false;
		}
		IModel model = component.getModel();
		if (null == model || !IPropertyReflectionAwareModel.class.isAssignableFrom(model.getClass())) {
			LOGGER.debug("No valid model is available for configuring Component: " + component);
			return false;
		}

		return true;
	}

	/**
	 * simple interface to abstract performing work for a specific annotation.
	 */
	private static interface HibernateAnnotationConfig {
		void onAnnotatedComponent(Annotation annotation, FormComponent component);
	}
}
