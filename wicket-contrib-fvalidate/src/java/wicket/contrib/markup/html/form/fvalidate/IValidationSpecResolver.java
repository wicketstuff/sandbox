/*
 * $Id$ $Revision$ $Date$
 * 
 * ================================================================================
 * Copyright (c) All rechten voorbehouden.
 */
package wicket.contrib.markup.html.form.fvalidate;

import wicket.markup.html.form.FormComponent;

/**
 * 
 */
public interface IValidationSpecResolver
{
	/**
	 * Gets the fValidate specification (code and error message) for the given
	 * validator.
	 * @param formComponent the form component to get the specification for
	 * @return the fValidate code and description (see <a
	 *         href="http://www.peterbailey.net/fValidate/types/">codes/ types
	 *         </a>
	 */
	ValidationSpec getSpec(FormComponent formComponent);
}
