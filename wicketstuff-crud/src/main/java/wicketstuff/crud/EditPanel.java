package wicketstuff.crud;

import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.IModel;

public abstract class EditPanel extends Panel
{

	public EditPanel(String id, IModel model, List<Property> properties)
	{
		super(id, model);
		Form form = new Form("form", model);
		addOrReplace(form);
		RepeatingView props = new RepeatingView("props");
		form.add(props);

		for (Property property : properties)
		{
			WebMarkupContainer prop = new WebMarkupContainer(props.newChildId());
			props.add(prop);

			final Component editor = property.getEditor("editor", model);
			editor.setOutputMarkupId(true);
			prop.add(editor);
			prop.add(new Label("label", property.getLabel())
			{
				@Override
				protected void onComponentTag(ComponentTag tag)
				{
					super.onComponentTag(tag);
					tag.put("for", editor.getMarkupId());
				}
			});
		}

		form.add(new Button("save")
		{
			@Override
			public void onSubmit()
			{
				onSave(EditPanel.this.getModel());
			}
		});

		form.add(new Link("cancel")
		{

			@Override
			public void onClick()
			{
				onCancel();
			}

		});
	}

	protected abstract void onSave(IModel model);

	protected abstract void onCancel();

}
