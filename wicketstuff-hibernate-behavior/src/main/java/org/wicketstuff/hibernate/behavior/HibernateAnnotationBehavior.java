package org.wicketstuff.hibernate.behavior;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.AbstractBehavior;
import org.wicketstuff.hibernate.HibernateAnnotationComponentConfigurator;

/**
 * Updates behavior of wicket components based on hibernate annotations.
 * <p>
 * Since the underlying {@link HibernateAnnotationComponentConfigurator configurator} is 
 * stateless, this behavior is also stateless and the same instance can be reused across 
 * multiple components.
 * </p>
 * 
 * <p>
 * The {@link IModel underlying model} of the component <em>must</em> be bound before 
 * attaching this behavior.
 * </p>
 */
public class HibernateAnnotationBehavior extends AbstractBehavior {
	private final HibernateAnnotationComponentConfigurator configurator = new HibernateAnnotationComponentConfigurator();

	@Override
	public void bind(Component component) {
		super.bind(component);

		configurator.configure(component);
	}
}
