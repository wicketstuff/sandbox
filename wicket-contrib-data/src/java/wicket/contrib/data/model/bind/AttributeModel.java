package wicket.contrib.data.model.bind;

import org.apache.wicket.model.IModel;

/**
 * A convenience class to represent the attribute value in an attribute
 * modifier.
 * 
 * @author Phil Kulak
 */
public abstract class AttributeModel implements IModel
{
	public void detach()
	{
	}

	public IModel getNestedModel()
	{
		return null;
	}

	public Object getObject()
	{
		return getAttributeValue();
	}

	public void setObject(Object object)
	{
		throw new UnsupportedOperationException("Attribute models can "
				+ "not have their models set.");
	}

	protected abstract String getAttributeValue();
}
