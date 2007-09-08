package wicketstuff.crud.property;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;

import wicketstuff.crud.property.editor.ChoiceEditor;


public class ChoiceProperty extends AbstractProperty
{
	private IModel choices;
	private IChoiceRenderer renderer;

	public ChoiceProperty(String path, IModel label)
	{
		super(path);
		setLabel(label);
	}


	public IModel getChoicesModel()
	{
		return choices;
	}


	public void setChoices(IModel choicesModel)
	{
		this.choices = choicesModel;
	}


	public IChoiceRenderer getRenderer()
	{
		return renderer;
	}


	public void setRenderer(IChoiceRenderer renderer)
	{
		this.renderer = renderer;
	}


	public Component getEditor(String id, IModel object)
	{
		return new ChoiceEditor(id, new PropertyModel(object, getPath()), choices, renderer);
	}

	public Component getViewer(String id, IModel object)
	{
		final PropertyModel prop = new PropertyModel(object, getPath());
		return new Label(id, new AbstractReadOnlyModel()
		{

			@Override
			public Object getObject()
			{
				final Object object = prop.getObject();
				return renderer.getDisplayValue(object);
			}

			@Override
			public void detach()
			{
				prop.detach();
			}

		});
	}
}
