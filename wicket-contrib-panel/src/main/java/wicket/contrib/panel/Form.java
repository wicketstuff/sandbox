package wicket.contrib.panel;

import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.model.IModel;

public class Form extends org.apache.wicket.markup.html.form.Form implements HasMarkup {

	/**
	 * Constructs a form with no validation.
	 *
	 * @param id
	 *            See Component
	 */
	public Form(final String id)
	{
		this(id, null);
	}

	/**
	 * @param id
	 *            See Component
	 * @param model
	 *            See Component
	 * @see wicket.Component#Component(String, IModel)
	 */
	public Form(final String id, IModel model)
	{
		super(id, model);
	}

	@Override
	protected MarkupStream locateMarkupStream() {
//		return super.locateMarkupStream();
		MarkupContainer markupContainer = (MarkupContainer) this;
		MarkupStream markupStream = getApplication().getMarkupSettings().getMarkupCache().getMarkupStream(markupContainer, false, true);
		return markupStream;
	}
}
