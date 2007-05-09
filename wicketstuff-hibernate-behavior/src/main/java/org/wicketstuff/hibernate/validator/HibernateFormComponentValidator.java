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
 * <p>Validates Object's property over Hibernate Validator framework.</p>
 * 
 * @author miojo
 */
public class HibernateFormComponentValidator implements IValidator {

	private ClassValidator validator;

	private String property;

	private Object object;

	@SuppressWarnings("unchecked")
	public HibernateFormComponentValidator(Class name) {
		validator = new ClassValidator(name);

		// An instance is required to pass to HV framework
		try {
			object = name.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	public HibernateFormComponentValidator(Class name, String property) {
		this(name);
		this.property = property;
	}

	public HibernateFormComponentValidator(Class name, FormComponent formc) {
		this(name);
		property = formc.getId();
		formc.add(this);
	}

	@SuppressWarnings("unchecked")
	public void validate(IValidatable validatable) {
		if (property == null) {
			// Need a new implementation extending Validatable
			// ValidatableFormComponent with a Model reference would be great for this
			IModel model = null; // ((ValidatableFormComponent) validatable).getModel();
			if (model instanceof PropertyModel) {
				// PropertyModel with public propertyExpression() method is needed too 
				String expression = null; // ((PropertyModel) model).propertyExpression();

				property = expression;
			}
		}

		PropertyResolver.setValue(property, object, validatable.getValue(),
				null);

		InvalidValue[] invalidValues = validator.getInvalidValues(object,
				property);

		for (InvalidValue iv : invalidValues)
			validatable
					.error(new ValidationError().setMessage(iv.getMessage()));
	}

}