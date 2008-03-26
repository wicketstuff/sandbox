package org.wicketstuff.hibernate.validator;

import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.util.lang.PropertyResolver;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;
import org.hibernate.validator.ClassValidator;
import org.hibernate.validator.InvalidValue;

/**
 * <p>
 * Validates Object's property over Hibernate Validator framework.
 * </p>
 * 
 * @author miojo
 */
public class HibernateFormComponentValidator implements IValidator {

	private static final long serialVersionUID = 1L;

	private String property;

	private Class clazz;

	/**
	 * Default constructor
	 */
	public HibernateFormComponentValidator() {
	}

	/**
	 * 
	 * @param name
	 *            class to be used
	 */
	public HibernateFormComponentValidator(Class name) {
		clazz = name;
	}

	/**
	 * 
	 * @param name
	 *            class to be used
	 * @param property
	 *            to be validated
	 */
	public HibernateFormComponentValidator(Class name, String property) {
		this(name);
		this.property = property;
	}

	/**
	 * 
	 * 
	 * @param name
	 *            class to be used
	 * @param component
	 *            from form to be validated
	 */
	public HibernateFormComponentValidator(Class name, FormComponent component) {
		this(name);
		property = component.getId();
	}

	@SuppressWarnings("unchecked")
	public void validate(IValidatable validatable) {
		Object object = null;

		// if property and/or clazz are null,
		// introspect model to get these values
		if (property == null || clazz == null) {
			// Need a new implementation extending Validatable:
			// ValidatableFormComponent with a Model reference would be great
			// for this
			IModel model = null; // ((ValidatableFormComponent)validatable).getModel();
			if (model instanceof PropertyModel) {
				PropertyModel propertyModel = null;

				// PropertyModel with public propertyExpression() method is
				// needed too
				String expression = propertyModel.getPropertyExpression();
				property = expression;

				// if this is a PropertyModel, it has an Object, so get it.
				object = propertyModel.getTarget();

				// if clazz is null, get from target object
				clazz = clazz != null ? clazz : object.getClass();
			}
		}

		// An instance is required to pass to HV framework
		if (object == null) {
			try {
				object = clazz.newInstance();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}

			// set object property value
			PropertyResolver.setValue(property, object, validatable.getValue(),
							null);
		}

		// creates the Class Validator
		ClassValidator validator = new ClassValidator(clazz);
		InvalidValue[] invalidValues = validator.getInvalidValues(object,
						property);

		// append error messages
		for (InvalidValue iv : invalidValues)
			validatable.error(new ValidationError().setMessage(iv.getMessage()));
	}

}