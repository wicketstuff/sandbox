package wicket.contrib.data.model.bind;

import wicket.model.AbstractModel;
import wicket.model.IModel;

/**
 * A convenience class to represent the attribute value in an attribute
 * modifier.
 * 
 * @author Phil Kulak
 */
public abstract class AttributeModel extends AbstractModel<String>
{
	@Override
	public IModel getNestedModel()
	{
		return null;
	}

	public String getObject()
	{
		return getAttributeValue();
	}

	public void setObject(String object)
	{
		throw new UnsupportedOperationException("Attribute models can "
				+ "not have their models set.");
	}

	protected abstract String getAttributeValue();
}
