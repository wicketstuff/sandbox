package wicket.contrib.data.model.bind;

import wicket.contrib.data.model.OrderedPageableList;

/**
 * Provides the functionality needed to interact with a datastore in the
 * context of a list of entities.
 * 
 * @author Phil Kulak
 */
public interface IListDataSource<T> extends IObjectDataSource<T>
{
	/**
	 * @return a list of items that should be displayed by a component
	 */
	public OrderedPageableList<T> getList();
}
