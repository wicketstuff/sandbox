/*
 * Created on Feb 21, 2005
 */
package net.sf.ipn.dynweb;

import wicket.AttributeModifier;
import wicket.markup.html.form.FormComponent;
import wicket.markup.html.form.TextField;
import wicket.model.IModel;
import wicket.model.Model;

/**
 * @author Jonathan Carlson Builds a dynamic HTML text field
 */
public class DynTextFieldPanel extends DynFormComponentPanel
{
	public TextField textField = null;

	public DynTextFieldPanel(String componentName, final IModel model)
	{
		super(componentName);
		this.textField = new TextField("dynTextField", model);
		add(this.textField);
	}

	public FormComponent getFormComponent()
	{
		return this.textField;
	}

	public DynTextFieldPanel setReadOnly(boolean readOnly)
	{
		setReadOnly(this.textField, readOnly);
		return this;
	}

	public DynTextFieldPanel setTitle(String title)
	{
		System.out.println("############## Setting title: " + title + " " + this);
		setTitle(this.textField, title);
		return this;
	}

	public DynTextFieldPanel setRequired(boolean required)
	{
		System.out.println("############## Setting required: " + required + " " + this);
		System.out.flush();
		setRequired(this.textField, required);
		return this;
	}

	public DynTextFieldPanel setSize(int size)
	{
		this.textField.add(new AttributeModifier("size", new Model(String.valueOf(size))));
		return this;
	}

	public DynTextFieldPanel setMaxLength(int value)
	{
		this.textField.add(new AttributeModifier("maxlength", new Model(String.valueOf(value))));
		return this;
	}

}