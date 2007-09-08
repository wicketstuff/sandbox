package wicketstuff.crud.property;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;

import wicketstuff.crud.Property;


public abstract class AbstractProperty implements Property
{
	private IModel label;
	private final String path;

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


}
