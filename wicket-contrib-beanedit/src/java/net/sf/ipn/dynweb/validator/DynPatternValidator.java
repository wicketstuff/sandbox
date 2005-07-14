/*
 * Created on Jan 21, 2005
 */
package net.sf.ipn.dynweb.validator;

import java.util.Map;
import java.util.regex.Pattern;

import wicket.markup.html.form.validation.PatternValidator;
import wicket.util.parse.metapattern.MetaPattern;

/**
 * @author Jonathan Carlson Allows developer to specify an error message key and a
 *         variable replacement Map, which the superclass does not allow.
 */
public class DynPatternValidator extends PatternValidator
{

	private String resourceKey;

	private Map resourceModel;

	public DynPatternValidator(String pattern, String resourceKey, Map resourceModel)
	{
		super(pattern);
		this.resourceKey = resourceKey;
		this.resourceModel = resourceModel;
	}

	public DynPatternValidator(String pattern, int flags, String resourceKey, Map resourceModel)
	{
		super(pattern, flags);
		this.resourceKey = resourceKey;
		this.resourceModel = resourceModel;
	}

	public DynPatternValidator(Pattern pattern, String resourceKey, Map resourceModel)
	{
		super(pattern);
		this.resourceKey = resourceKey;
		this.resourceModel = resourceModel;
	}

	public DynPatternValidator(MetaPattern pattern, String resourceKey, Map resourceModel)
	{
		super(pattern);
		this.resourceKey = resourceKey;
		this.resourceModel = resourceModel;
	}


	/**
	 * Use the resource key given in the constructor instead of the default one.
	 * @see wicket.markup.html.form.validation.AbstractValidator#resourceKey()
	 */
	protected String resourceKey()
	{
		return this.resourceKey;
	}

	/**
	 * Adds our variable replacement strings to the default ones specified in the
	 * superclass.
	 * @see wicket.markup.html.form.validation.AbstractValidator#getMessageModel()
	 */
	protected Map messageModel()
	{
		Map map = super.messageModel();
		map.putAll(this.resourceModel);
		return map;
	}

}