/*
 * $Id$ $Revision$
 * $Date$
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
package wicket.addons.sidebars;

import wicket.Page;
import wicket.addons.RssInfos;
import wicket.markup.html.link.IPageLink;
import wicket.markup.html.link.PageLink;
import wicket.markup.html.panel.Panel;

/**
 * @author Juergen Donnerstag
 */
public final class SidebarRSS extends Panel
{
	/**
	 * Constructor
	 * 
	 * @param parameters
	 */
	public SidebarRSS(final String componentName)
	{
		super(componentName);

		add(new PageLink("rss", new IPageLink()
		{
			public Page getPage()
			{
				return new RssInfos(null);
			}

			public Class getPageIdentity()
			{
				return RssInfos.class;
			}
		}));
	}
}
