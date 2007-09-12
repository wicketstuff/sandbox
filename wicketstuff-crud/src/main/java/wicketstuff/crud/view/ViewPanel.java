package wicketstuff.crud.view;

import java.util.List;

import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

import wicketstuff.crud.Property;

public abstract class ViewPanel extends Panel
{

	public ViewPanel(String id, IModel model, List<Property> properties)
	{
		super(id, model);

		add(new PropertiesView("props", model, properties));

		add(new Link("edit", model)
		{

			@Override
			public void onClick()
			{
				onEdit(getModel());
			}

		});


		add(new Link("delete", model)
		{

			@Override
			public void onClick()
			{
				onDelete(getModel());
			}

		});

		add(new Link("back", model)
		{

			@Override
			public void onClick()
			{
				onBack();
			}

		});

	}

	protected abstract void onEdit(IModel model);

	protected abstract void onDelete(IModel model);

	protected abstract void onBack();

}
