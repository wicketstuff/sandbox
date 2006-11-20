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

import wicket.Application;
import wicket.contrib.markup.html.navmenu.MenuRowStyle;
import wicket.markup.html.PackageResourceReference;

/**
 * Style that renders tabs.
 * 
 * @author Eelco Hillenius
 */
public final class TabsStyle
{
	/** the first level (level 0) of this style. */
	public static final MenuRowStyle LEVEL_0 = new Level0();

	/** the second level (level 1) of this style. */
	public static final MenuRowStyle LEVEL_1 = new Level1();

	/**
	 * Class for the first level (level 0) of this style.
	 */
	private static final class Level0 extends MenuRowStyle
	{
		/**
		 * Construct.
		 */
		private Level0()
		{
			new PackageResourceReference(Application.get(), TabsStyle.class, "folders/active.gif");
			new PackageResourceReference(Application.get(), TabsStyle.class, "folders/hover.gif");
			new PackageResourceReference(Application.get(), TabsStyle.class, "folders/selected.gif");
			new PackageResourceReference(Application.get(), TabsStyle.class,
					"folders/unselected.gif");
			setStyleSheetResource(new PackageResourceReference(Application.get(), TabsStyle.class,
					"tabs-folders.css"));
			setRowCSSClass("wicketNavmenuTabsLevel0");
		}
	}

	/**
	 * Class for the second level (level 1) of this style.
	 */
	private static final class Level1 extends MenuRowStyle
	{
		/**
		 * Construct.
		 */
		private Level1()
		{
			setStyleSheetResource(new PackageResourceReference(Application.get(), TabsStyle.class,
					"tabs-folders.css"));
			setContainerCSSClass("wicketNavmenuTabsLevel1Border");
			setRowCSSClass("wicketNavmenuTabsLevel1");
		}
	}

	/**
	 * Hidden constructor; the public constants should be used instead.
	 */
	private TabsStyle()
	{
		super();
	}
}