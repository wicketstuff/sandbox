package org.wicketstuff.html5.markup.html.form;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.model.IModel;

public class NumberField<T extends Number> extends AbstractHtml5TextComponent<T> {

	private static final long serialVersionUID = 1L;

	private T minimum;
	
	private T maximum;
	
	private T step;
	
	public NumberField(String id) {
		super(id);
	}
	
	public NumberField(String id, IModel<T> model) {
		super(id, model);
	}
	
	public NumberField(String id, IModel<T> model, String datalistId) {
		super(id, model, datalistId);
	}

	/**
	 * Processes the component tag.
	 * 
	 * @param tag
	 *            Tag to modify
	 * @see org.apache.wicket.Component#onComponentTag(ComponentTag)
	 */
	@Override
	protected void onComponentTag(final ComponentTag tag)
	{
		super.onComponentTag(tag);
	
		if (minimum != null) {
			tag.put("min", minimum.toString());
		}
		
		if (maximum != null) {
			tag.put("max", maximum.toString());
		}
		
		if (step != null) {
			tag.put("step", step.toString());
		}
	}
	
	/**
	 * @see org.apache.wicket.markup.html.form.TextField#getInputType()
	 */
	@Override
	protected String getInputType()
	{
		return "number";
	}

	/**
	 * @return the minimum
	 */
	public T getMinimum() {
		return minimum;
	}

	/**
	 * @return the maximum
	 */
	public T getMaximum() {
		return maximum;
	}

	/**
	 * @return the step
	 */
	public T getStep() {
		return step;
	}

	/**
	 * @param minimum the minimum to set
	 */
	public NumberField<T> setMinimum(final T minimum) {
		this.minimum = minimum;
		return this;
	}

	/**
	 * @param maximum the maximum to set
	 */
	public NumberField<T> setMaximum(final T maximum) {
		this.maximum = maximum;
		return this;
	}

	/**
	 * @param step the step to set
	 */
	public NumberField<T> setStep(final T step) {
		this.step = step;
		return this;
	}
}
