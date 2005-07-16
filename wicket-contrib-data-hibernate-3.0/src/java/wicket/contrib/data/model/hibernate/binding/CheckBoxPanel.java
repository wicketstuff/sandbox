package wicket.contrib.data.model.hibernate.binding;

import wicket.markup.html.panel.Panel;
import wicket.model.IModel;

public class CheckBoxPanel extends Panel {
	public CheckBoxPanel(String id, IModel model) {
		super(id);
		add(new InlineCheckBox("inlineCheckBox", model));
	}
}
