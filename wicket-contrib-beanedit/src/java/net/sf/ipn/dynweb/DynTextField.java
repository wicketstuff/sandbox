/*
 * Created on Feb 21, 2005
 */
package net.sf.ipn.dynweb;

import wicket.markup.ComponentTag;
import wicket.markup.html.form.TextField;
import wicket.markup.html.form.validation.RequiredValidator;
import wicket.model.IModel;

/**
 * @author Jonathan Carlson This subclass of TextField changes the tag name to "input"
 *         (probably from "span") and the type attribute to "text"
 * @deprecated
 */
public class DynTextField extends TextField
{

	public DynTextField(String id)
	{
		super(id);
	}

	public DynTextField(String id, Class type)
	{
		super(id, type);
	}

	public DynTextField(String id, IModel object)
	{
		super(id, object);
	}

	public DynTextField(String id, IModel model, Class type)
	{
		super(id, model, type);
	}

	/**
	 * Changes the tag name to "input" and the type attr to "text"
	 * @see wicket.Component#onComponentTag(wicket.markup.ComponentTag)
	 */
	protected void onComponentTag(ComponentTag tag)
	{
		tag.setName("input");
		tag.put("type", "text");
		if (this.size != null)
			tag.put("size", this.size.intValue());
		if (this.maxLength != null)
			tag.put("maxlength", this.maxLength.intValue());
		if (this.readOnly)
			tag.put("readonly", "true");
		super.onComponentTag(tag);
	}

	private Integer size = null;

	public DynTextField setSize(int size)
	{
		this.size = new Integer(size);
		return this;
	}

	private Integer maxLength = null;

	public DynTextField setMaxLength(int value)
	{
		this.size = new Integer(value);
		return this;
	}

	private boolean readOnly = false;

	public DynTextField setReadOnly(boolean value)
	{
		this.readOnly = value;
		return this;
	}

	public DynTextField setRequired(boolean required)
	{
		if (required)
			add(RequiredValidator.getInstance());
		return this;
	}

}
