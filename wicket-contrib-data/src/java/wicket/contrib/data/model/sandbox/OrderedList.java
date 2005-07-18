package wicket.contrib.data.model.sandbox;

import java.util.List;

import wicket.model.IDetachable;

public interface OrderedList extends IDetachable, List
{
	public OrderedPageableList addOrder(String field);
	
	public OrderedPageableList removeOrder(String field);
}
