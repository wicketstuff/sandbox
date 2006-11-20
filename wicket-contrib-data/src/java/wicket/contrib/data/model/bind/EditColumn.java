package wicket.contrib.data.model.bind;

import wicket.Component;
import wicket.model.IModel;

public class EditColumn extends AbstractColumn
{
	public EditColumn()
	{
		super(null, null);
	}

	public Component getComponent(String id, IModel model)
	{
		return new EditPanel(id);
	}
}
