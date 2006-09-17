package wicket.contrib.data.model.bind;

import wicket.model.AbstractModel;

/**
 * A convenience class to represent the attribute value in an attribute
 * modifier.
 * 
 * @author Phil Kulak
 */
public abstract class AttributeModel extends AbstractModel<String>
{
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
