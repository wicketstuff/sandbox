package wicket.contrib.data.model.hibernate.binding;

import wicket.Component;
import wicket.model.IModel;

public class LabelColumn extends AbstractColumn {
	public LabelColumn(String displayName, String ognlPath) {
		super(displayName, ognlPath);
	}

	public Component getComponent(String id, IModel model) {
		return new LabelPanel(id, model);
	}
}
