package wicket.contrib.data.model.bind;

import org.apache.wicket.model.IModel;

/**
 * A panel for rendering an {@link InlineTextField}
 * 
 * @author Phil Kulak
 */
public class TextFieldPanel extends InlineValidatingPanel
{
	public TextFieldPanel(String id, IModel model)
	{
		super(id);
		InlineTextField field = new InlineTextField("inlineTextField", model);
		field.setRenderBodyOnly(true);
		setComponent(field);
	}
}
