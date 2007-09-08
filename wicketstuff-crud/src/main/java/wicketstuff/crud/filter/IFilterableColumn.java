package wicketstuff.crud.filter;

import org.apache.wicket.Component;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.model.IModel;

public interface IFilterableColumn extends IColumn
{
	Component getFilter(String id, IModel model);
}
