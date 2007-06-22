package org.wicketstuff.hibernate;

import org.apache.wicket.Component;
import org.apache.wicket.application.IComponentInstantiationListener;
import org.wicketstuff.hibernate.behavior.HibernateAnnotationBehavior;

/**
 * Component listener that will automatically configure all form components based on hibernate annotations.
 *
 * <p>
 * This component helps ensure that an entire application respects the
 * annotations without adding custom validators or behaviors to each form component.
 * </p>
 *
 * <p>
 * Unfortunately, the current implementation is incomplete.  The component does not yet 
 * have a model when the {@link #onInstantiation(Component) entry point} is called.  
 * The {@link HibernateAnnotationBehavior behavior} that is attached is then executed 
 * before the Model is bound to the Component, which causes the annotations to not be 
 * found.
 * </p>
 * 
 * <p>
 * There needs to be work done in the wicket core to allow for this kind of callback.
 * </p>
 *
 * @author rsonnek
 */
public class HibernateAnnotationComponentInstantiaionListener implements IComponentInstantiationListener {
	private final HibernateAnnotationBehavior behavior = new HibernateAnnotationBehavior();

	public void onInstantiation(Component component) {
		component.add(behavior);
	}
}
