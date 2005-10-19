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
package wicket.contrib.markup.html.navmenu.style.tabs;

import wicket.contrib.markup.html.navmenu.MenuModel;
import wicket.markup.html.border.Border;

/**
 * Border component that renders the menu using a provided menu model.
 * 
 * @author Eelco Hillenius
 */
public class TabsMenuBorder extends Border
{
	/**
	 * Construct.
	 * 
	 * @param id
	 *            component id
	 * @param model
	 *            the menu model
	 */
	public TabsMenuBorder(String id, MenuModel model)
	{
		super(id);
		add(new TabsNavigationMenu("navmenu", model));
	}
}