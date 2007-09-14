package wicketstuff.crud.view;

import org.apache.wicket.markup.html.border.Border;
import org.apache.wicket.model.IModel;

public class FormBorder extends Border
{
	public FormBorder(String id)
	{
		super(id);
	}

	public FormBorder(String id, IModel model)
	{
		super(id, model);
	}

}
