package wicketstuff.crud.view;

import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.IModel;

import wicketstuff.crud.Property;

class PropertiesView extends Panel
{

	public PropertiesView(String id, IModel model, List<Property> properties)
	{
		super(id, model);

		RepeatingView props = new RepeatingView("props");
		add(props);

		for (Property property : properties)
		{
			WebMarkupContainer prop = new WebMarkupContainer(props.newChildId());
			props.add(prop);

			final Component viewer = property.getViewer("viewer", model);
			viewer.setOutputMarkupId(true);
			prop.add(viewer);
			prop.add(new Label("label", property.getLabel())
			{
				@Override
				protected void onComponentTag(ComponentTag tag)
				{
					super.onComponentTag(tag);
					tag.put("for", viewer.getMarkupId());
				}
			});
		}

	}

}
