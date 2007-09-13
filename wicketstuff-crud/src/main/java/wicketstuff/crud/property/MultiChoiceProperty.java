package wicketstuff.crud.property;

import java.util.Collection;
import java.util.Iterator;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;

import wicketstuff.crud.Property;
import wicketstuff.crud.property.editor.ChoiceEditor;


public class MultiChoiceProperty extends Property
{
	private IModel choices;
	private IChoiceRenderer renderer;

	public MultiChoiceProperty(String path, IModel label)
	{
		super(path);
		setLabel(label);
	}


	public IModel getChoicesModel()
	{
		return choices;
	}


	public MultiChoiceProperty setChoices(IModel choicesModel)
	{
		this.choices = choicesModel;
		return this;
	}


	public IChoiceRenderer getRenderer()
	{
		return renderer;
	}


	public MultiChoiceProperty setRenderer(IChoiceRenderer renderer)
	{
		this.renderer = renderer;
		return this;
	}


	@Override
	public Component getEditor(String id, IModel object)
	{
		ChoiceEditor editor = new ChoiceEditor(id, new PropertyModel(object, getPath()), choices,
				renderer);
		configure(editor);
		return editor;
	}

	@Override
	public Component getViewer(String id, IModel object)
	{
		final PropertyModel prop = new PropertyModel(object, getPath());
		return new Label(id, new AbstractReadOnlyModel()
		{

			@Override
			public Object getObject()
			{
				final Collection<?> choices = (Collection<?>)prop.getObject();
				StringBuilder buff = new StringBuilder();
				if (choices != null && !choices.isEmpty())
				{
					Iterator<?> it = choices.iterator();
					while (it.hasNext())
					{
						buff.append(renderer.getDisplayValue(it.next()));
						if (it.hasNext())
						{
							buff.append(", ");
						}
					}
				}
				return buff.toString();
			}

			@Override
			public void detach()
			{
				prop.detach();
			}

		});
	}
}
