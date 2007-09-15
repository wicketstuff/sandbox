package wicketstuff.crud.filter;

import org.apache.wicket.Component;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.model.IModel;

/**
 * Mixin interface for columns that can be filtered
 * 
 * @author igor.vaynberg
 * 
 */
public interface IFilterableColumn extends IColumn
{
	/**
	 * Gets component that will be used to collect filter information
	 * 
	 * @param id
	 *            component id
	 * @param model
	 *            model where component should store its value
	 * @return
	 */
	Component getFilter(String id, IModel model);
}
