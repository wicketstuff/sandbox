package wicketstuff.crud.view;

import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.border.Border;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.IModel;

import wicketstuff.crud.ICrudListener;
import wicketstuff.crud.Property;

public class EditPanel extends Panel
{
	private final List<Property> properties;
	private final ICrudListener crudListener;

	public EditPanel(String id, IModel model, List<Property> properties,
			final ICrudListener crudListener)
	{
		super(id, model);
		this.properties = properties;
		this.crudListener = crudListener;
	}

	@Override
	protected void onBeforeRender()
	{
		if (!hasBeenRendered())
		{
			// we perform a two-phase initialization because we use factories
			// which if overridden would be called from the constructor of this
			// class

			Form form = new Form("form", getModel());
			addOrReplace(form);

			Border formBody = newFormBorder("form-body-border");
			form.add(formBody);


			RepeatingView props = new RepeatingView("props");
			formBody.add(props);

			for (Property property : properties)
			{
				WebMarkupContainer prop = new WebMarkupContainer(props.newChildId());
				props.add(prop);

				final Component editor = property.getEditor("editor", getModel());
				editor.setOutputMarkupId(true);
				Border border = newEditorBorder("border", editor);
				border.add(editor);

				prop.add(border);
			}

			formBody.add(new Button("save")
			{
				@Override
				public void onSubmit()
				{
					crudListener.onSave(EditPanel.this.getModel());
				}
			});

			formBody.add(new Link("cancel")
			{

				@Override
				public void onClick()
				{
					crudListener.onCancel();
				}

			});

		}
		super.onBeforeRender();
	}


	protected Border newFormBorder(String id)
	{
		return new FormBorder(id);
	}

	protected Border newEditorBorder(String id, Component editor)
	{
		return new EditorBorder(id);
	}

}
