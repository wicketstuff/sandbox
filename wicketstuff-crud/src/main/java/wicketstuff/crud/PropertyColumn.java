package wicketstuff.crud;

import org.apache.wicket.Component;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;

import wicketstuff.crud.filter.IFilterableColumn;


public class PropertyColumn extends AbstractColumn implements IFilterableColumn
{
	private final Property property;

	public PropertyColumn(Property property)
	{
		super(property.getLabel());
		this.property = property;
	}

	public void populateItem(Item cellItem, String componentId, IModel rowModel)
	{
		cellItem.add(property.getViewer(componentId, rowModel));
	}

	public Component getFilter(String id, IModel model)
	{
		return property.getFilter(id, model);
	}


}
