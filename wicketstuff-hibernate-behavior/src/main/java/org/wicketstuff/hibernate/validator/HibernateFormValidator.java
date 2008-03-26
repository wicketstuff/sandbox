package org.wicketstuff.hibernate.validator;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.validation.IFormValidator;
import org.hibernate.validator.ClassValidator;
import org.hibernate.validator.InvalidValue;

/**
 * <p>
 * Validates Model's object over Hibernate Validator framework
 * </p>
 * 
 * @author miojo
 */
public class HibernateFormValidator implements IFormValidator {

	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unchecked")
	private Class clazz;

	@SuppressWarnings("unchecked")
	public HibernateFormValidator(Class name) {
		clazz = name;
	}

	public HibernateFormValidator() {
	}

	public FormComponent[] getDependentFormComponents() {
		return null;
	}

	@SuppressWarnings("unchecked")
	public void validate(Form form) {
		Object object = form.getModelObject();

		Class _clazz = clazz == null ? object.getClass() : clazz;

		ClassValidator validator = new ClassValidator(_clazz);
		InvalidValue[] invalidValues = validator.getInvalidValues(object);

		for (InvalidValue iv : invalidValues) {
			form.error(iv.getMessage());
		}
	}

}