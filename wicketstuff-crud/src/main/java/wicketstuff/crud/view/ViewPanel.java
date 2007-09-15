package wicketstuff.crud.view;

import java.util.List;

import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

import wicketstuff.crud.ICrudListener;
import wicketstuff.crud.Property;

/**
 * View panel
 * 
 * @author igor.vaynberg
 * 
 */
public class ViewPanel extends Panel
{

	/**
	 * Constructor
	 * 
	 * @param id
	 * @param model
	 * @param properties
	 * @param crudListener
	 */
	public ViewPanel(String id, IModel model, List<Property> properties,
			final ICrudListener crudListener)
	{
		super(id, model);

		add(new PropertiesView("props", model, properties));

		add(new Link("edit", model)
		{

			@Override
			public void onClick()
			{
				crudListener.onEdit(getModel());
			}

		});


		add(new Link("delete", model)
		{

			@Override
			public void onClick()
			{
				crudListener.onDelete(getModel());
			}

		});

		add(new Link("back", model)
		{

			@Override
			public void onClick()
			{
				crudListener.onCancel();
			}

		});

	}

}
