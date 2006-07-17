/*
 * $Id$ $Revision$ $Date$
 * 
 * ==================================================================== Licensed
 * under the Apache License, Version 2.0 (the "License"); you may not use this
 * file except in compliance with the License. You may obtain a copy of the
 * License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package wicket.contrib.data.model;

import java.util.ArrayList;
import java.util.List;

import wicket.markup.html.list.PageableListView;

/**
 * A List that behaves like a pageable object. It works with a count and a
 * window. The count is used for the total number of elements that are available
 * for this list, and the window is used for the currently loaded elements.
 * <p>
 * This list figures out what elements need to be loaded when asked for
 * elements. The actual loading of the count and elements is delegated to the
 * action objects.
 * </p>
 * <p>
 * Not all {@link java.util.List}methods are supported. Unsupported methods
 * will throw {@link java.lang.IllegalStateException}s.
 * </p>
 * <p>
 * NOTE: this list is meant for specific situations, like backing up
 * {@link wicket.markup.html.list.PageableListView}s, that use sequential
 * addressing of the list. Random access is inefficient, as a number of rows
 * (the page) is loaded at once for each window. Hence it IS efficent for the
 * sequential access.
 * </p>
 * 
 * @author Eelco Hillenius
 */
public class PageableList<T, V> extends UnimplementedList<T>
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** default page size. */
	private static final int DEFAULT_PAGE_SIZE = 10;

	/** total count. */
	private int count;

	/** action that selects a count and a (partial) list. */
	private ISelectCountAndListAction<T, V> countAndListAction;

	/** start from index. */
	private int currentPageStartIndex = -1;

	/** flag that records whether the count was designated at least once. */
	private boolean initialized = false;

	/** part of the list that is actually loaded. */
	private List<T> pageItems = new ArrayList<T>();

	/** size of the partial list that is to be actually loaded. */
	private int pageSize;

	/**
	 * Construct; uses the given window size.
	 * 
	 * @param pageSize
	 *            the page size
	 * @param countAndListAction
	 *            ction that selects a count and a (partial) list
	 */
	public PageableList(int pageSize, ISelectCountAndListAction<T, V> countAndListAction)
	{
		this.pageSize = pageSize;
		this.countAndListAction = countAndListAction;
	}

	/**
	 * Construct; uses the default page size (10).
	 * 
	 * @param countAndListAction
	 *            ction that selects a count and a (partial) list
	 */
	public PageableList(ISelectCountAndListAction<T, V> countAndListAction)
	{
		this(DEFAULT_PAGE_SIZE, countAndListAction);
	}

	/**
	 * Construct; uses the rowsPerPage property of the given table as the page
	 * size.
	 * 
	 * @param table
	 *            the table to get the page size from
	 * @param countAndListAction
	 *            ction that selects a count and a (partial) list
	 */
	public PageableList(PageableListView<T> table,
			ISelectCountAndListAction<T, V> countAndListAction)
	{
		this(table.getRowsPerPage(), countAndListAction);
	}

	/**
	 * Clears the recorded count and the current loaded window; forces to reload
	 * the size/ count and window.
	 * 
	 * @see java.util.Collection#clear()
	 */
	@Override
	public final void clear()
	{
		pageItems.clear();
		currentPageStartIndex = -1;
		initialized = false;
	}

	/**
	 * Gets the element at the given position. If the index is outside of the
	 * currently loaded window, the window that the index should be in is
	 * loaded.
	 * 
	 * @see java.util.List#get(int)
	 */
	@Override
	public final T get(int index)
	{
		if (index > size())
		{
			throw new IndexOutOfBoundsException(
					"index is larger than number of query results");
		}
		int start = getCurrentPageStartIndex(index);
		if (start != currentPageStartIndex)
		{
			currentPageStartIndex = start;
			loadPage(currentPageStartIndex, getPageSize());
		}
		int windowIndex = getPageIndex(index);
		return pageItems.get(windowIndex);
	}

	/**
	 * Gets action that selects a count and a (partial) list.
	 * 
	 * @return action that selects a count and a (partial) list.
	 */
	public final ISelectCountAndListAction<T, V> getCountAndListAction()
	{
		return countAndListAction;
	}

	/**
	 * Gets size of the partial list that is to be actually loaded.
	 * 
	 * @return size of the partial list that is to be actually loaded.
	 */
	public final int getPageSize()
	{
		return pageSize;
	}

	/**
	 * @see java.util.Collection#isEmpty()
	 */
	@Override
	public final boolean isEmpty()
	{
		return size() == 0;
	}

	/**
	 * Sets action that selects a count and a (partial) list.
	 * 
	 * @param countAndListAction
	 *            action that selects a count and a (partial) list.
	 */
	public final void setCountAndListAction(
			ISelectCountAndListAction<T, V> countAndListAction)
	{
		this.countAndListAction = countAndListAction;
	}

	/**
	 * Sets size of the partial list that is to be actually loaded.
	 * 
	 * @param windowSize
	 *            size of the partial list that is to be actually loaded.
	 */
	public final void setPageSize(int windowSize)
	{
		this.pageSize = windowSize;
	}

	/**
	 * Gets the total size.
	 * 
	 * @return the total size/ count of this list.
	 * @see java.util.Collection#size()
	 */
	@Override
	public final int size()
	{
		if (!initialized)
		{
			count = getTotalSize();
			initialized = true;
		}
		return count;
	}

	/**
	 * Gets the currently loaded items if any. This method will allways return a
	 * list, but when no elements are loaded yet, it will be empty. NOTE use
	 * this method for exceptional cases only; usually the normal list
	 * operations should suffice.
	 * 
	 * @return the currently loaded items
	 */
	public final List<T> getPageItems()
	{
		return pageItems;
	}

	/**
	 * Gets the total size; the size of all underlying elements.
	 * 
	 * @return the total count
	 */
	protected int getTotalSize()
	{
		return countAndListAction.execute(getQueryObject());
	}

	/**
	 * Gets the object that holds the parameters for executing queries. This
	 * method just returns null.
	 * 
	 * @return null allways; override this method to provide a custom object
	 */
	protected V getQueryObject()
	{
		return null;
	}

	/**
	 * Load the rows that start from the given parameter 'startFromRow' until
	 * 'startFromIndex' + 'numberOfElements' and return the results wrapped in a
	 * list.
	 * 
	 * @param startFromIndex
	 *            load from index
	 * @param numberOfElements
	 *            the number of elements to load
	 * @return the results wrapped in a list
	 */
	protected List<T> load(int startFromIndex, int numberOfElements)
	{
		return countAndListAction.execute(getQueryObject(), startFromIndex,
				numberOfElements);
	}

	/**
	 * Load data for the current window.
	 * 
	 * @param startFromIndex
	 *            load from index
	 * @param numberOfElements
	 *            the number of elements to load
	 */
	private final void loadPage(int startFromIndex, int numberOfElements)
	{
		pageItems.clear();
		if (count > 0)
		{
			List<T> loaded = load(startFromIndex, numberOfElements);
			pageItems.addAll(loaded);
		}
	}

	/**
	 * Get the starting index of the current page.
	 * 
	 * @param index
	 *            absolute index
	 * @return starting index of the current page
	 */
	private final int getCurrentPageStartIndex(int index)
	{
		if (index < pageSize)
		{
			return 0;
		}
		else
		{
			return Math.abs(index / pageSize) * pageSize;
		}
	}

	/**
	 * Get the index of the window.
	 * 
	 * @param index
	 *            absolute index
	 * @return the relative (within the window) index
	 */
	private int getPageIndex(int index)
	{
		return index % pageSize;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "PageableList{current page index="
				+ currentPageStartIndex + ",pagesize=" + pageSize + "}";
	}
}