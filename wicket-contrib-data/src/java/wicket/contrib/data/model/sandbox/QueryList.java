package wicket.contrib.data.model.sandbox;

import java.util.Iterator;
import java.util.List;

public abstract class QueryList extends OrderedPageableList
{
	protected final List getItems(int start, int max, List ordering)
	{
		StringBuffer orderBy = new StringBuffer();
		if (ordering.size() > 0)
		{
			orderBy.append(" ORDER BY");
			boolean addComma = false;
			for (Iterator i = ordering.iterator(); i.hasNext();)
			{
				if (addComma) orderBy.append(",");
					
				ListOrder order = (ListOrder) i.next();
				orderBy.append(" " + order.getField())
					.append(order.isAscending() ? " ASC" : " DESC");
				
				addComma = true;
			}
		}
		return getItems(start, max, orderBy.toString());
	}
	
	protected abstract List getItems(int start, int max, String orderBy);
}
