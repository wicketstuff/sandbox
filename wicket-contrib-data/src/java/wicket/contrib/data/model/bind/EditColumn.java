package wicket.contrib.data.model.bind;

import wicket.Component;
import wicket.MarkupContainer;
import wicket.model.IModel;

public class EditColumn<T> extends AbstractColumn<T>
{
	private static final long serialVersionUID = 1L;

	public EditColumn()
	{
		super(null, null);
	}

	public Component getComponent(MarkupContainer parent, String id, IModel<T> model)
	{
		return new EditPanel(parent, id);
	}
}
