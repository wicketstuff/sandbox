package wicket.contrib.panel.example;

import org.apache.wicket.model.IModel;

public class TextArea extends org.apache.wicket.markup.html.form.TextArea {

	/**
	 * @see wicket.Component#Component(String)
	 */
	public TextArea(final String id)
	{
		super(id);
	}

	/**
	 * @see wicket.Component#Component(String, IModel)
	 */
	public TextArea(final String id, final IModel model)
	{
		super(id, model);
	}
}
