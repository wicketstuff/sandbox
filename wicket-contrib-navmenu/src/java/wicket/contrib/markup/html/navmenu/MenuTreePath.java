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

import javax.swing.tree.TreePath;

/**
 * Custom tree path.
 * 
 * @author Eelco Hillenius
 */
public final class MenuTreePath extends TreePath
{
	private static final long serialVersionUID = 1L;

	/**
	 * Construct.
	 * 
	 * @param path
	 *            the path
	 */
	public MenuTreePath(Object[] path)
	{
		super(path);
	}

	/**
	 * Checks whether the given node is part of this path.
	 * 
	 * @param menuItem
	 *            the node
	 * @return true when the given node is part of the path
	 */
	public boolean isPartOfPath(MenuItem menuItem)
	{
		int len = getPathCount();
		Object parent = menuItem.getParent();
		// first check whether the node is part of the actual path
		for (int i = 0; i < len; i++)
		{
			Object pathComponent = getPathComponent(i);
			if (pathComponent.equals(menuItem))
			{
				return true;
			}
		}

		return false;
	}
}