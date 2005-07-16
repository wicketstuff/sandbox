package wicket.contrib.data.model.hibernate.binding;

import wicket.Component;
import wicket.model.IModel;

public class TextFieldColumn extends AbstractColumn {
	public TextFieldColumn(String displayName, String ognlPath) {
		super(displayName, ognlPath);
	}
	
	public Component getComponent(String id, IModel model) {
		return new TextFieldPanel(id, model);
	}
}
