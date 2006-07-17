package wicket.contrib.data.model;

import java.util.List;

/**
 * A list that formats its ordering information into an SQL string.
 * 
 * @author Phil Kulak
 */
public abstract class QueryList<E> extends OrderedPageableList<E>
{
	@Override
	protected final List<E> getItems(int start, int max, List<ListOrder> ordering)
	{
		return getItems(start, max, makeOrderBy(ordering));
	}

	@Override
	protected final List<E> getAllItems(List<ListOrder> ordering)
	{
		return getAllItems(makeOrderBy(ordering));
	}

	protected abstract List<E> getItems(int start, int max, String orderBy);

	protected List<E> getAllItems(String orderBy)
	{
		return null;
	}

	protected String makeOrderBy(List<ListOrder> ordering)
	{
		StringBuffer orderBy = new StringBuffer();
		if (ordering.size() > 0)
		{
			orderBy.append(" ORDER BY");
			boolean addComma = false;
			for (Object element : ordering)
			{
				if (addComma)
				{
					orderBy.append(",");
				}

				ListOrder order = (ListOrder) element;
				orderBy.append(" " + order.getField()).append(
						order.isAscending() ? " ASC" : " DESC");

				addComma = true;
			}
		}
		return orderBy.toString();
	}
}
