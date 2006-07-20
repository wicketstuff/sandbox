package wicket.contrib.data.model.bind;

import wicket.Component;
import wicket.MarkupContainer;
import wicket.model.IModel;

public class DeleteColumn<T> extends AbstractColumn<T>
{
	private static final long serialVersionUID = 1L;

	public DeleteColumn()
	{
		super(null, null);
	}

	public Component getComponent(MarkupContainer parent, String id, IModel<T> model)
	{
		return new DeletePanel(parent, id);
	}
}
