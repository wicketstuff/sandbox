package wicketstuff.crud.property;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.validation.IValidator;

import wicketstuff.crud.Property;


public abstract class AbstractProperty implements Property
{
	private IModel label;
	private final String path;

	private boolean required;
	private List<IValidator> validators;

	public AbstractProperty(String path)
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


	public boolean isRequired()
	{
		return required;
	}


	public void setRequired(boolean required)
	{
		this.required = required;
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
	
	public AbstractProperty addValidator(IValidator validator) {
		if (validator==null) {
			throw new IllegalArgumentException("Argument `validator` cannot be null");
		}
		if(validators==null) {
			validators=new ArrayList<IValidator>(1);
		}
		validators.add(validator);
		return this;
	}


}
