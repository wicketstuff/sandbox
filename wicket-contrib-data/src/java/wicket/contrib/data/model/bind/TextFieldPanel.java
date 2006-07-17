package wicket.contrib.data.model.bind;

import wicket.MarkupContainer;
import wicket.model.IModel;

/**
 * A panel for rendering an {@link InlineTextField}
 * 
 * @author Phil Kulak
 */
public class TextFieldPanel extends InlineValidatingPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TextFieldPanel(MarkupContainer parent, String id, IModel model)
	{
		super(parent, id);
		InlineTextField field = new InlineTextField(this, "inlineTextField", model);
		field.setRenderBodyOnly(true);
		setComponent(field);
	}
}
