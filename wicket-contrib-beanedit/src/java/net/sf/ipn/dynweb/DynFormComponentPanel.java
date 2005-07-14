/*
 * Created on Jun 20, 2005
 */
package net.sf.ipn.dynweb;

import java.util.HashMap;
import java.util.Map;

import net.sf.ipn.dynweb.validator.DynRequiredValidator;

import wicket.AttributeModifier;
import wicket.markup.html.form.FormComponent;
import wicket.markup.html.panel.Panel;
import wicket.model.IModel;
import wicket.model.Model;

/**
 * Provides a few common attributes for form element components. Subclasses must implement
 * setTitle and setRequired and any other desired HTML attribute setters. These setters
 * must return self (this).
 * @author Jonathan Carlson
 */
public abstract class DynFormComponentPanel extends Panel
{

	protected String title = null;
	protected boolean required = false;
	protected boolean readOnly = false;
	protected boolean disabled = false;

	public DynFormComponentPanel(String id)
	{
		super(id);
	}

	public DynFormComponentPanel(String id, IModel model)
	{
		super(id, model);
	}

	public abstract FormComponent getFormComponent();

	public boolean isReadOnly()
	{
		return this.readOnly;
	}

	/** Must only be used by subclasses */
	protected void setReadOnly(FormComponent component, boolean readOnly)
	{
		this.readOnly = readOnly;
		if (readOnly)
			component.add(new AttributeModifier("readonly", new Model("true")));
	}

	public boolean isDisabled()
	{
		return this.disabled;
	}

	/** Must only be used by subclasses */
	protected void setDisabled(FormComponent component, boolean disabled)
	{
		this.disabled = disabled;
		if (disabled)
			component.add(new AttributeModifier("disabled", new Model("true")));
	}

	public String getTitle()
	{
		return this.title;
	}

	/** Must only be used by subclasses */
	protected void setTitle(FormComponent component, String s)
	{
		this.title = s;
	}

	public boolean isRequired()
	{
		return this.required;
	}

	/** Must only be used by subclasses */
	protected void setRequired(FormComponent component, boolean required)
	{
		this.required = required;
		if (!required)
			return;
		System.out.println("getTitle: " + getTitle());
		if (getTitle() == null)
			throw new IllegalStateException(
					"^^setRequired(true) requires the title to be set first.");
		Map map = new HashMap();
		map.put("attrName", getTitle());
		component.add(new DynRequiredValidator("error.field-required", map));
	}

}
