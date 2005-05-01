/*
 * $Id$
 * $Revision$
 * $Date$
 *
 * ================================================================================
 * Copyright (c)
 * All rechten voorbehouden.
 */
package wicket.contrib.markup.html.form.fvalidate;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;

import wicket.markup.html.form.FormComponent;
import wicket.markup.html.form.validation.IntegerValidator;
import wicket.markup.html.form.validation.IValidator;
import wicket.markup.html.form.validation.LengthValidator;
import wicket.markup.html.form.validation.PatternValidator;
import wicket.markup.html.form.validation.RequiredValidator;
import wicket.markup.html.form.validation.TypeValidator;

/**
 * IValidationSpecResolver implementation that uses the form component's
 * registered validators to designate the code and message for fValidate.
 *
 * TODO support more extensive error message support
 *
 * @author Eelco Hillenius
 */
public class ValidationSpecResolver implements IValidationSpecResolver
{
	/**
	 * Construct.
	 */
	public ValidationSpecResolver()
	{
	}

	/**
	 * Gets the spec for the form component based on it's registered validators.
	 * @param formComponent the form component to get the specification for
	 * @return the fValidate code and description (see <a
	 *         href="http://www.peterbailey.net/fValidate/types/">codes/ types
	 *         </a>
	 * @see wicket.contrib.markup.html.form.fvalidate.IValidationSpecResolver#getSpec(wicket.markup.html.form.FormComponent)
	 */
	public ValidationSpec getSpec(FormComponent formComponent)
	{
		ValidationSpec spec = internalGetSpec(formComponent);
		return spec;
	}

	/**
	 * Gets the validator code (the fValidate component to be used
	 * with what parameters) for this component based on the set
	 * {@link wicket.AttributeModifier}s.
	 * @param formComponent the form component to get the specification for
	 * @return the fValidate specific validator expression
	 */
	protected final ValidationSpec internalGetSpec(FormComponent formComponent)
	{
		// create the attribute for fValidate
		ValidationSpec spec = new ValidationSpec();
		boolean required = false;
		for (Iterator i = formComponent.getValidators().iterator(); i.hasNext();)
		{
			IValidator validator = (IValidator) i.next();
			if (validator instanceof RequiredValidator) // required is a special case
			{
				required = true;
			}
			else if(spec.getCode() != null) // skip if we've written once
			{
				continue;
			}
			else // no expr yet, try to find a value for the validator
			{
				setSpecFields(spec, validator);
			}
		}
		if(required)
		{
			// if no attrib was written yet write the otherwise optional required
			if(spec.getCode() == null)
			{
				spec.setCode("blank");
				spec.setErrorMsg("required");
			} // else no need as we've got a non null anyway
		}
		return spec;
	}

	/**
	 * Fills the fValidate expression for the given validator.
	 * E.g. 'length|6' or 'date|mm/dd/yyyy'.
	 * See <a href="http://www.peterbailey.net/fValidate/types">fValidate types</a>
	 * @param spec spec to set fields on
	 * @param validator the validator
	 */
	private final void setSpecFields(ValidationSpec spec, IValidator validator)
	{
		if(validator instanceof TypeValidator)
		{
			setSpecFieldsForType(spec, (TypeValidator)validator);
		}
		else if(validator instanceof LengthValidator)
		{
			LengthValidator val = (LengthValidator)validator;
			StringBuffer code = new StringBuffer("length|");
			StringBuffer msg = new StringBuffer();
			final int min;
			final int max;
			min = (val.isCheckMin()) ? val.getMin() : 0;
			code.append(min);
			if(val.isCheckMax())
			{
				max = val.getMax();
				code.append("|").append(val.getMax());
				msg.append("length should be between ")
					.append(min).append(" and ").append(max);
			}
			else
			{
				msg.append("length should be greater than ").append(min);
			}
			spec.setCode(code.toString());
			spec.setErrorMsg(msg.toString());
		}
		else if(validator instanceof IntegerValidator)
		{
			IntegerValidator val = (IntegerValidator)validator;
			StringBuffer code = new StringBuffer("number|0|");
			long min = val.getMin();
			long max = val.getMax();
			code.append(min).append("|").append(max);
			spec.setCode(code.toString());
			StringBuffer msg = new StringBuffer("number should be between ")
				.append(min).append(" and ").append(max);
		}
		else if(validator instanceof PatternValidator)
		{
			// TODO fValidate 'custom' could be used here.
			// However fValidate does not make it clear how this would be
			// used. Should find that out sometime if there is demand for it
		}
	}

	/**
	 * Fills the fValidate spec based on the given type validator.
	 * @param validator the type validator
	 * @param spec spec to set fields on
	 */
	private void setSpecFieldsForType(ValidationSpec spec, TypeValidator validator)
	{
		Class type = validator.getType();
		if(Number.class.isAssignableFrom(type))
		{
			if(Double.class.isAssignableFrom(type)
					|| Float.class.isAssignableFrom(type)
					|| BigDecimal.class.isAssignableFrom(type))
			{
				spec.setCode("number|1");
				spec.setErrorMsg("not a valid decimal");
				// NOTE: localized patterns are not supported at
				// this time. That should probably be solved regexp etc
			}
			else
			{
				spec.setCode("number|0");
				spec.setErrorMsg("not a valid number");
			}
		}
		else if(Date.class.isAssignableFrom(type))
		{
			spec.setCode("date|mm/dd/yyyy");
			spec.setErrorMsg("not a valid date; use format mm/dd/yyyy");
		}
	}
}
