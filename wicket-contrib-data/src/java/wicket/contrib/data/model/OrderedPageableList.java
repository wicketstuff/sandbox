package wicket.contrib.data.model;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.model.IDetachable;

/**
 * <p>
 * A read-only List that behaves like a pageable object. It works with a count 
 * and a window. The count is used for the total number of elements that are 
 * available for this list, and the window is used for the currently loaded 
 * elements.
 * </p>
 * <p>
 * This list figures out what elements need to be loaded when asked for
 * elements. The actual loading of the count and elements is delegated to
 * subclasses. Ordering is supported through the add/remove ordering methods.
 * </p>
 * <p>
 * Detachable operation is supported by {@link DetachableList}, however,
 * for expensive queries (such as search) it may be advisable to set the window
 * several times larger than the paging size and not use detaching so that the
 * search is rerun less often. Also note tha this class is most efficient when
 * it's window size is exactly that of the page containing it, however it's best
 * to overestimate: a window one item smaller than the page will result in two
 * selects for each request.
 * </p>
 * <p>
 * By default, the max number of orderings retained is two. For more efficient
 * query cache utilization, set this to one, or zero to disable ordering
 * completely.
 * </p>
 * <p>
 * NOTE: this list is meant for specific situations, like backing up
 * {@link wicket.markup.html.list.PageableListView}s, that use sequential
 * addressing of the list. Random access is inefficient, as a number of rows
 * (the page) is loaded at once for each window. Hence it IS efficent for
 * sequential access.
 * </p>
 * 
 * @author Phil Kulak
 * @author Eelco Hillenius
 */
public abstract class OrderedPageableList extends AbstractList
		implements IDetachable
{
	private List window = null;

	private int windowStart = 0;

	private int windowSize = 10;

	private int totalElements = -1;

	private List ordering = new ArrayList(3);
	
	private int orderingMaxFields = 2;
	
	private boolean usePaging = true;
	
	/** a handy replacement when Collections.EMPTY_LIST just won't do. **/
	public static final OrderedPageableList EMPTY_LIST = new OrderedPageableList()
	{
		protected List getItems(int start, int max, List ordering)
		{
			return null;
		}

		protected int getCount()
		{
			return 0;
		}
	};
	
	/**
	 * @param windowSize the new size of the pageing window
	 * @return itself to allow chaining
	 */
	public OrderedPageableList setWindowSize(int windowSize)
	{
		this.windowSize = windowSize;
		return this;
	}
	
	/**
	 * @param orderingMaxFields the maximum number of orderings to remember
	 * @return itself to allow chaining
	 */
	public OrderedPageableList setOrderingMaxFields(int orderingMaxFields)
	{
		this.orderingMaxFields = orderingMaxFields;
		return this;
	}
	
	/**
	 * @param usePaging false to have this list fetch all items when any one is needed
	 * @return itself to allow chaining
	 */
	public OrderedPageableList setUsePaging(boolean usePaging)
	{
		this.usePaging = usePaging;
		return this;
	}

	/**
	 * Returns items starting from the start index, with size no greater then
	 * max.
	 * 
	 * @param start the index to start at
	 * @param max the maximum elements to return
	 * @param ordering the list of orderings to use                  
	 * @return all the items in the current window
	 */
	protected abstract List getItems(int start, int max, List ordering);
	
	/**
	 * Override this method to return all items in the list. This doesn't have
	 * to be implemented. If it isn't, {@link #getCount()} will be called when
	 * all the items need to be loaded.
	 * 
	 * @param ordering the list of orderings to use
	 * @return all items
	 */
	protected List getAllItems(List ordering)
	{
		return null;
	}
	
	private int getCountInternal()
	{
		if (!usePaging)
		{
			resetWindow(0);
			return window.size();
		}
		return getCount();
	}

	/**
	 * @return the total elements.
	 */
	protected abstract int getCount();

	/**
	 * Adds an ordering to the end of the list. If the field already exists, the
	 * direction is switched.
	 * 
	 * @param field the field to order by
	 * @return itself to allow chaining
	 */
	public OrderedPageableList addOrder(String field)
	{
		if (orderingMaxFields == 0) {
			return this;
		}
		
		ListOrder order = new ListOrder(field);
		int i = ordering.indexOf(order);
		
		// If it exists, remove it and swap it's ordering.
		if (i != -1)
		{
			order = (ListOrder) ordering.remove(i);
			order.switchOrder();
		}

		if (ordering.size() == orderingMaxFields) {
			ordering.remove(orderingMaxFields - 1);
		}
		
		// Add the ordering to the top.
		ordering.add(0, order);
		
		// Reset the state since the query has changed.
		detach();
		return this;
	}

	/**
	 * Removes the ordering on the given field.
	 */
	public OrderedPageableList removeOrder(String field)
	{
		ordering.remove(new ListOrder(field));
		return this;
	}

	/**
	 * Gets the element at the given position. If the index is outside of the
	 * currently loaded window, the window that the index should be in is
	 * loaded. Forward traversal is assumed.
	 * 
	 * @see java.util.List#get(int)
	 */
	public final Object get(int index)
	{
		if (window == null)
			resetWindow(index);

		if (index >= size())
			throw new IndexOutOfBoundsException("Index greater then size - 1");
		if (index < 0)
			throw new IndexOutOfBoundsException("Index less than zero.");

		int relativeIndex = index - windowStart;

		if (relativeIndex < 0 || relativeIndex >= window.size())
		{
			resetWindow(index);
			relativeIndex = 0;
		}
		
		if (relativeIndex >= window.size())
			throw new IndexOutOfBoundsException("The count query has returned a " +
				"number greater then the amount of records in the data set or " +
				"get() has been called for a row that doesn't exist. If this " +
				"exception was thrown after adding an ordering, check that the " +
				"ordering didn't introduce an inner join that reduced the size " +
				"of the record set. ");
		
		return window.get(relativeIndex);
	}

	/**
	 * @see java.util.List#size()
	 */
	public final int size()
	{
		if (totalElements == -1)
			totalElements = getCountInternal();

		return totalElements;
	}

	public void detach()
	{
		window = null;
		totalElements = -1;
	}

	private void resetWindow(int start)
	{
		if (usePaging)
		{
			windowStart = start;
			window = getItems(windowStart, windowSize, ordering);
		}
		else
		{
			windowStart = 0;
			window = getAllItems(ordering);
			
			if (window == null)
			{
				window = getItems(0, size(), ordering);
			}
		}
	}
}
