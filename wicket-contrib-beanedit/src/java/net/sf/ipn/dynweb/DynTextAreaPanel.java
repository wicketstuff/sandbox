/*
 * Created on Feb 21, 2005
 */
package net.sf.ipn.dynweb;

import wicket.AttributeModifier;
import wicket.markup.html.form.FormComponent;
import wicket.markup.html.form.TextArea;
import wicket.model.IModel;
import wicket.model.Model;

/**
 * @author Jonathan Carlson Builds a dynamic HTML text area. rows and columns are
 *         configurable.
 */
public class DynTextAreaPanel extends DynFormComponentPanel
{
	public TextArea textArea = null;

	public DynTextAreaPanel(String componentName, final IModel model)
	{
		super(componentName);
		this.textArea = new TextArea("dynTextArea", model);
		add(this.textArea);
	}

	public FormComponent getFormComponent()
	{
		return this.textArea;
	}

	public DynTextAreaPanel setRows(int rows)
	{
		this.textArea.add(new AttributeModifier("rows", new Model(String.valueOf(rows))));
		return this;
	}

	public DynTextAreaPanel setColumns(int cols)
	{
		this.textArea.add(new AttributeModifier("cols", new Model(String.valueOf(cols))));
		return this;
	}

	public DynTextAreaPanel setReadOnly(boolean readOnly)
	{
		setReadOnly(this.textArea, readOnly);
		return this;
	}

	public DynTextAreaPanel setRequired(boolean required)
	{
		setRequired(this.textArea, required);
		return this;
	}

	public DynTextAreaPanel setTitle(String title)
	{
		setTitle(this.textArea, title);
		return this;
	}

}