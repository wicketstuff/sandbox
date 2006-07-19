/*
 * $Id$
 * $Revision$
 * $Date$
 * 
 * ==============================================================================
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package wicket.contrib.markup.html.navmenu;

import java.util.ArrayList;
import java.util.List;

import wicket.Component;
import wicket.Page;
import wicket.RequestCycle;
import wicket.model.AbstractDetachableAssignmentAwareModel;
import wicket.model.IModel;

/**
 * Menu model for one row.
 * 
 * @author Eelco Hillenius
 */
public class MenuRowModel extends AbstractDetachableAssignmentAwareModel<List<MenuItem>>
{
	/** menu level. */
	private final int level;

	/** the tree model. */
	private final MenuModel menuModel;

	/** current row. */
	private transient List<MenuItem> row;

	/**
	 * Construct.
	 * 
	 * @param menuModel
	 *            menu model
	 * @param level
	 *            the level in the menu, 0..n-1
	 */
	public MenuRowModel(MenuModel menuModel, int level)
	{
		this.menuModel = menuModel;
		this.level = level;
	}

	/**
	 * Whether the given menu item is part of the currently selected path
	 * 
	 * @param currentPage
	 *            the current page
	 * @param menuItem
	 *            the menu item
	 * @return true if the given menu item is part of the currently selected
	 *         path
	 */
	public boolean isPartOfCurrentSelection(Page currentPage, MenuItem menuItem)
	{
		return menuModel.isPartOfCurrentSelection(currentPage, menuItem);
	}

	/**
	 * @see wicket.model.IModel#getNestedModel()
	 */
	public IModel getNestedModel()
	{
		return null;
	}

	/**
	 * @see wicket.model.AbstractDetachableAssignmentAwareModel#onAttach()
	 */
	protected void onAttach()
	{
	}

	/**
	 * @see wicket.model.AbstractDetachableAssignmentAwareModel#onDetach()
	 */
	protected void onDetach()
	{
		this.row = null;
	}

	/**
	 * @see wicket.model.AbstractDetachableAssignmentAwareModel#onGetObject(wicket.Component)
	 */
	@Override
	protected List<MenuItem> onGetObject(Component component)
	{
		// lazily attach
		if (row == null)
		{
			row = new ArrayList<MenuItem>();
			RequestCycle requestCycle = RequestCycle.get();
			Page currentPage = component.getPage();
			MenuTreePath currentSelection = menuModel.getCurrentSelection(currentPage);
			if (currentSelection.getPathCount() > level)
			{
				MenuItem node = (MenuItem)currentSelection.getPathComponent(level);
				int len = node.getChildCount();
				for (int i = 0; i < len; i++)
				{
					MenuItem child = (MenuItem)node.getChildAt(i);
					if (child.checkAccess())
					{
						row.add(child);
					}
				}
			}
		}
		return row;
	}

	@Override
	protected void onSetObject(Component component, List<MenuItem> object)
	{
		throw new UnsupportedOperationException("set object is not supported for this model");
	}
}
