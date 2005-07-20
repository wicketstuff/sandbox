package wicket.contrib.data.model.bind;

import wicket.Component;
import wicket.model.IModel;

public class DeleteColumn extends AbstractColumn
{
	public DeleteColumn()
	{
		super(null, null);
	}
	
	public Component getComponent(String id, IModel model)
	{
		return new DeletePanel(id);
	}
}
