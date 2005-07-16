package wicket.contrib.data.model.hibernate.binding;

import wicket.markup.html.panel.Panel;
import wicket.model.IModel;

public class TextFieldPanel extends Panel {
	public TextFieldPanel(String id, IModel model) {
		super(id);
		InlineTextField field = new InlineTextField("inlineTextField", model);
		field.setRenderBodyOnly(true);
		add(field);
	}
}
