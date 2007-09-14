package wicketstuff.crud.view;

import java.util.List;

import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

import wicketstuff.crud.ICrudListener;
import wicketstuff.crud.Property;

public class DeletePanel extends Panel
{

	public DeletePanel(String id, IModel model, List<Property> properties,
			final ICrudListener crudListener)
	{
		super(id, model);

		add(new PropertiesView("props", model, properties));

		add(new Link("confirm", model)
		{

			@Override
			public void onClick()
			{
				crudListener.onDeleteConfirmed(getModel());
			}

		});


		add(new Link("cancel", model)
		{

			@Override
			public void onClick()
			{
				crudListener.onCancel();
			}

		});

	}


}
