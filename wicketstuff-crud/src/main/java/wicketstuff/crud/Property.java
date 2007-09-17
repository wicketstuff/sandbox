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
import org.apache.wicket.util.string.Strings;
import org.apache.wicket.validation.IValidator;

/**
 * Represents a bean property from crud panel's point of view
 * 
 * @author igor.vaynberg
 * 
 */
public abstract class Property implements Serializable
{
	/** label model */
	private IModel label;

	/** property dot-notation path */
	private final String path;

	/** required flag */
	private boolean required;

	/** validators */
	private List<IValidator> validators;

	/**
	 * Constructor
	 * 
	 * @param path
	 *            property path in dot notation
	 */
	public Property(String path)
	{
		if (Strings.isEmpty(path))
		{
			throw new IllegalArgumentException("Property path cannot be null or empty");
		}
		this.path = path;
	}


	/**
	 * Constructor
	 * 
	 * @param path
	 *            property path in dot notation
	 * @param label
	 *            property label
	 */
	public Property(String path, IModel label)
	{
		this(path);
		if (label == null)
		{
			throw new IllegalArgumentException("Property label cannot be null");
		}
		this.label = label;
	}


	/**
	 * @return property path
	 */
	public String getPath()
	{
		return path;
	}


	/**
	 * @return property label
	 */
	public IModel getLabel()
	{
		return label;
	}

	/**
	 * Sets property label
	 * 
	 * @param label
	 */
	public void setLabel(IModel label)
	{
		this.label = label;
	}

	/**
	 * Gets the component that will be used to view the property
	 * 
	 * @param id
	 * @param object
	 * @return
	 */
	public Component getViewer(String id, IModel object)
	{
		return new Label(id, new PropertyModel(object, getPath()));
	}

	/**
	 * Gets the component that will be used to enter values for filtering on
	 * this property
	 * 
	 * @param id
	 * @param object
	 * @return
	 */
	public Component getFilter(String id, IModel object)
	{
		return getEditor(id, object);
	}

	/**
	 * Gets the component that will be used to edit the property
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	public abstract Component getEditor(String id, IModel model);


	/**
	 * @return required flag
	 */
	public boolean isRequired()
	{
		return required;
	}


	/**
	 * Sets the reuqired flag
	 * 
	 * @param required
	 * @return
	 */
	public Property setRequired(boolean required)
	{
		this.required = required;
		return this;
	}


	/**
	 * @return validators for this property
	 */
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

	/**
	 * Adds a validator to this property
	 * 
	 * @param validator
	 * @return
	 */
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

	/**
	 * Method used by subclasses to perform a common initialization on editor
	 * components
	 * 
	 * @param editor
	 */
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

	/**
	 * Method used by subclasses to perform a common initialization on editor
	 * components
	 * 
	 * @param editor
	 */
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
