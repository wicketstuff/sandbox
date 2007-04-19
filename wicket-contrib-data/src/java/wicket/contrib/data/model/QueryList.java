package wicket.contrib.data.model;

import java.util.Iterator;
import java.util.List;

/**
 * A list that formats its ordering information into an SQL string.
 * 
 * @author Phil Kulak
 */
public abstract class QueryList extends OrderedPageableList
{
	protected final List getItems(int start, int max, List ordering)
	{
		return getItems(start, max, makeOrderBy(ordering));
	}
	
	protected final List getAllItems(List ordering)
	{
		return getAllItems(makeOrderBy(ordering));
	}
	
	protected abstract List getItems(int start, int max, String orderBy);
	
	protected List getAllItems(String orderBy)
	{
		return null;
	}
	
	protected String makeOrderBy(List ordering)
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
		return orderBy.toString();
	}
}
