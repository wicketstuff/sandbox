package wicket.contrib.data.model.hibernate.binding;

import wicket.Component;
import wicket.model.IModel;

public class CheckBoxColumn extends AbstractColumn {
	public CheckBoxColumn(String displayName, String ognlPath) {
		super(displayName, ognlPath);
	}

	public Component getComponent(String id, IModel model) {
		return new CheckBoxPanel(id, model);
	}
}
