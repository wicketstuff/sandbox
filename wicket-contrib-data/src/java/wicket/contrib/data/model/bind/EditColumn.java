package wicket.contrib.data.model.bind;

import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;

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
