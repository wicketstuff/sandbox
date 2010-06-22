package org.wicketstuff.html5.markup.html.form;

import org.apache.wicket.model.IModel;

public class RangeTextField<T extends Number> extends NumberField<T> {

	private static final long serialVersionUID = 1L;

	/**
	 * @see org.apache.wicket.Component#Component(String)
	 */
	public RangeTextField(final String id)
	{
		this(id, null);
	}

	/**
	 * @param id
	 * @param model
	 * @see org.apache.wicket.Component#Component(String, IModel)
	 */
	public RangeTextField(final String id, IModel<T> model)
	{
		super(id, model);
	}

	/**
	 * @param id
	 * @param model
	 * @see org.apache.wicket.Component#Component(String, IModel)
	 */
	public RangeTextField(final String id, IModel<T> model, String datalistId)
	{
		super(id, model, datalistId);
	}
	
	/**
	 * @see org.apache.wicket.markup.html.form.TextField#getInputType()
	 */
	@Override
	protected String getInputType()
	{
		return "range";
	}

}
