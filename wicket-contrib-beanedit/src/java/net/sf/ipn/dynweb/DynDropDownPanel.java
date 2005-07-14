/*
 * Created on Dec 28, 2004
 */
package net.sf.ipn.dynweb;

import java.util.List;

import wicket.markup.html.form.DropDownChoice;
import wicket.markup.html.form.FormComponent;
import wicket.model.IModel;

/**
 * @author Jonathan Carlson Builds an HTML drop-down list component using the given
 *         metadata
 */
public class DynDropDownPanel extends DynFormComponentPanel
{
	private DropDownChoice dropDownChoice;
	private List choiceList;
	private IModel model;

	/**
	 * Construct.
	 * @param componentName
	 * @param model
	 * @param choices
	 */
	public DynDropDownPanel(String componentName, final IModel model, final List choices)
	{
		super(componentName);
		this.dropDownChoice = new DropDownChoice("dynSelect", model, choices);
		add(dropDownChoice);
	}

	/**
	 * @see net.sf.ipn.dynweb.DynFormComponentPanel#getFormComponent()
	 */
	public FormComponent getFormComponent()
	{
		return this.dropDownChoice;
	}

	/**
	 * @param title
	 * @return
	 */
	public DynDropDownPanel setTitle(String title)
	{
		setTitle(this.dropDownChoice, title);
		return this;
	}

	/**
	 * @param required
	 * @return
	 */
	public DynDropDownPanel setRequired(boolean required)
	{
		setRequired(this.dropDownChoice, required);
		return this;
	}

}