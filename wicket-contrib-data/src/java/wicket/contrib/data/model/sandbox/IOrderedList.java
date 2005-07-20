package wicket.contrib.data.model.sandbox;

import java.util.ArrayList;
import java.util.List;

import wicket.model.IDetachable;

public interface IOrderedList extends IDetachable, List
{
	/** usefull for when you need a no-data fallback */
	public static final IOrderedList EMPTY_LIST = new EmptyOrderedList();
	
	public IOrderedList addOrder(String field);
	
	public IOrderedList removeOrder(String field);
	
	public class EmptyOrderedList extends ArrayList implements IOrderedList {

		public IOrderedList addOrder(String field)
		{
			return this;
		}

		public IOrderedList removeOrder(String field)
		{
			return this;
		}

		public void detach() {}
	}
}
