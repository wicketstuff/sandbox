package wicket.contrib.panel.example;

import org.apache.wicket.model.IModel;

public class Button extends org.apache.wicket.markup.html.form.Button {

	/**
	 * Constructor without a model. Buttons without models leave the markup
	 * attribute &quot;value&quot;. Provide a model if you want to set the
	 * button's label dynamically.
	 *
	 * @see wicket.Component#Component(String)
	 */
	public Button(String id)
	{
		super(id);
	}

	/**
	 * Constructor taking an model for rendering the 'label' of the button (the
	 * value attribute of the input/button tag). Use a
	 * {@link wicket.model.StringResourceModel} for a localized value.
	 *
	 * @param id
	 *            Component id
	 * @param model
	 *            The model property is used to set the &quot;value&quot;
	 *            attribute. It will thus be the label of the button that shows
	 *            up for end users. If you want the attribute to keep it's
	 *            markup attribute value, don't provide a model, or let it
	 *            return an empty string.
	 */
	public Button(final String id, final IModel model)
	{
		super(id, model);
	}
}
