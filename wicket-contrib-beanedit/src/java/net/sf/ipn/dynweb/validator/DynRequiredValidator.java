/*
 * Created on Jan 14, 2005
 */
package net.sf.ipn.dynweb.validator;

import java.util.Map;

import wicket.markup.html.form.validation.StringValidator;
import wicket.util.string.Strings;

/**
 * @author Jonathan Carlson Provides a way to specify an error message key and key/values
 *         for doing string replacement.
 */
public class DynRequiredValidator extends StringValidator
{
	private String resourceKey;
	private Map resourceModel;

	public DynRequiredValidator(String resourceKey, Map resourceModel)
	{
		this.resourceKey = resourceKey;
		this.resourceModel = resourceModel;
	}

	/**
	 * Adds our variable replacement strings to the default ones specified in the
	 * superclass.
	 * @see wicket.markup.html.form.validation.AbstractValidator#messageModel()
	 */
	protected Map messageModel()
	{
		Map map = super.messageModel();
		map.putAll(this.resourceModel);
		return map;
	}

	/**
	 * Use the resource key given in the constructor instead of the default one.
	 * @see wicket.markup.html.form.validation.AbstractValidator#getResourceKey()
	 */
	protected String resourceKey()
	{
		return this.resourceKey;
	}


	/**
	 * @see wicket.markup.html.form.validation.StringValidator#onValidate(java.lang.String)
	 */
	public void onValidate(String value)
	{
		// Check value
		if (Strings.isEmpty(value))
		{
			error();
		}
	}

	/**
	 * @see Object#toString()
	 */
	public String toString()
	{
		return "[DynRequiredValidator]";
	}

}