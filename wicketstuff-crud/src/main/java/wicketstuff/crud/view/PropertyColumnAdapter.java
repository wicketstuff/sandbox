package wicketstuff.crud.view;

import org.apache.wicket.Component;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;

import wicketstuff.crud.Property;
import wicketstuff.crud.filter.IFilterableColumn;


/**
 * Adapts data table columns to viewing properties
 * 
 * @author igor.vaynberg
 * 
 */
class PropertyColumnAdapter extends AbstractColumn implements IFilterableColumn
{
	private final Property property;

	/**
	 * Constructor
	 * 
	 * @param property
	 */
	public PropertyColumnAdapter(Property property)
	{
		super(property.getLabel());
		this.property = property;
	}

	/** {@inheritDoc} */
	public void populateItem(Item cellItem, String componentId, IModel rowModel)
	{
		cellItem.add(property.getViewer(componentId, rowModel));
	}

	/** {@inheritDoc} */
	public Component getFilter(String id, IModel model)
	{
		return property.getFilter(id, model);
	}


}
