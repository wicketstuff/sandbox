package wicketstuff.crud;

import java.util.List;

import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

public abstract class DeletePanel extends Panel
{

	public DeletePanel(String id, IModel model, List<Property> properties)
	{
		super(id, model);

		add(new PropertiesView("props", model, properties));

		add(new Link("confirm", model)
		{

			@Override
			public void onClick()
			{
				onConfirm(getModel());
			}

		});


		add(new Link("cancel", model)
		{

			@Override
			public void onClick()
			{
				onCancel();
			}

		});

	}

	protected abstract void onConfirm(IModel model);

	protected abstract void onCancel();

}
