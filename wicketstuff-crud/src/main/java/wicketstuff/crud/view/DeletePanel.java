package wicketstuff.crud.view;

import java.util.List;

import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

import wicketstuff.crud.ICrudListener;
import wicketstuff.crud.Property;

/**
 * Confirm delete screen
 * 
 * @author igor.vaynberg
 * 
 */
public class DeletePanel extends Panel
{

	/**
	 * Constructor
	 * 
	 * @param id
	 * @param model
	 * @param properties
	 * @param crudListener
	 */
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
