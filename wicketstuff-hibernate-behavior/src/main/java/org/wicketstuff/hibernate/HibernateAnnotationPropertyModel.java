package org.wicketstuff.hibernate;

import org.apache.wicket.model.PropertyModel;

/**
 * 
 * @author rsonnek
 * @deprecated this is a hack just to expose the property expression for inspection
 */
public class HibernateAnnotationPropertyModel extends PropertyModel {

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
	
	public Object getTarget() {
		return super.getTarget();
	}
}