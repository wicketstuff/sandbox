package wicket.contrib.panel.example;

import org.apache.wicket.Response;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

public class Label extends org.apache.wicket.markup.html.basic.Label {

	public Label(final String id)
	{
		super(id);
		setRenderBodyOnly(true);
	}

	/**
	 * Convenience constructor. Same as Label(String, new Model(String))
	 *
	 * @param id
	 *            See Component
	 * @param label
	 *            The label text
	 *
	 * @see wicket.Component#Component(String, IModel)
	 */
	public Label(final String id, String label)
	{
		this(id, new Model(label));
	}

	/**
	 * @see wicket.Component#Component(String, IModel)
	 */
	public Label(final String id, IModel model)
	{
		super(id, model);
		setRenderBodyOnly(true);
	}


	@Override
	/**
	 * @see wicket.Component#onComponentTagBody(wicket.markup.MarkupStream,
	 *      wicket.markup.ComponentTag)
	 */
	protected void onComponentTagBody(final MarkupStream markupStream, final ComponentTag openTag)
	{
		Response response = null;
		String tag = getLabelTag();
		if (tag!=null && !getRenderBodyOnly()) {
			response = getResponse();
			String cssStyle = getCssStyle();
			response.write("<" + tag + (cssStyle != null ? " class='" + cssStyle + "'" : "")  + ">");
		}
		replaceComponentTagBody(markupStream, openTag, getModelObjectAsString());
		if (tag!=null && !getRenderBodyOnly())
			response.write("</" + tag + ">");
	}

	protected String getLabelTag() {
		return "label";
	}

	protected String getCssStyle() {
		return null;
	}
}
