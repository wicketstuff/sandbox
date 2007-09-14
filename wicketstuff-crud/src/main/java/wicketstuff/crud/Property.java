package wicketstuff.crud;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.validation.IValidator;


public abstract class Property implements Serializable
{
	private IModel label;
	private final String path;

	private boolean required;
	private List<IValidator> validators;

	public Property(String path)
	{
		this.path = path;
	}


	public String getPath()
	{
		return path;
	}


	public IModel getLabel()
	{
		return label;
	}

	public void setLabel(IModel label)
	{
		this.label = label;
	}

	public Component getViewer(String id, IModel object)
	{
		return new Label(id, new PropertyModel(object, getPath()));
	}

	public Component getFilter(String id, IModel object)
	{
		return getEditor(id, object);
	}

	public abstract Component getEditor(String id, IModel model);


	public boolean isRequired()
	{
		return required;
	}


	public Property setRequired(boolean required)
	{
		this.required = required;
		return this;
	}


	public List<IValidator> getValidators()
	{
		if (validators == null)
		{
			return Collections.EMPTY_LIST;
		}
		else
		{
			return validators;
		}
	}

	public Property add(IValidator validator)
	{
		if (validator == null)
		{
			throw new IllegalArgumentException("Argument `validator` cannot be null");
		}
		if (validators == null)
		{
			validators = new ArrayList<IValidator>(1);
		}
		validators.add(validator);
		return this;
	}

	protected void configureEditor(final FormComponent editor)
	{
		configure(new Editor()
		{

			public void add(IValidator validator)
			{
				editor.add(validator);
			}

			public void setLabel(IModel label)
			{
				editor.setLabel(label);
			}

			public void setRequired(boolean required)
			{
				editor.setRequired(required);
			}

			public IModel getLabel()
			{
				return editor.getLabel();
			}

			public boolean isRequired()
			{
				return editor.isRequired();
			}

		});
	}

	protected void configure(Editor editor)
	{
		editor.setRequired(isRequired());
		for (IValidator validator : getValidators())
		{
			editor.add(validator);
		}
		editor.setLabel(getLabel());
	}

}
