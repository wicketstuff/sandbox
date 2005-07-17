package wicket.contrib.data.model.hibernate.binding;

import wicket.markup.html.panel.Panel;
import wicket.model.IModel;

/**
 * A panel for rendering an {@link InlineTextField}
 * 
 * @author Phil Kulak
 */
public class TextFieldPanel extends Panel
{
	public TextFieldPanel(String id, IModel model)
	{
		super(id);
		InlineTextField field = new InlineTextField("inlineTextField", model);
		field.setRenderBodyOnly(true);
		add(field);
	}
}
