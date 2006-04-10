/*
 * $Id$ $Revision:
 * 408 $ $Date$
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

import java.text.DecimalFormat;
import java.util.List;

import wicket.Page;
import wicket.addons.BaseHtmlPage;
import wicket.addons.PluginDetails;
import wicket.addons.RatingList;
import wicket.addons.db.Addon;
import wicket.addons.services.AddonService;
import wicket.markup.html.basic.Label;
import wicket.markup.html.link.IPageLink;
import wicket.markup.html.link.PageLink;
import wicket.markup.html.list.ListItem;
import wicket.markup.html.list.ListView;
import wicket.markup.html.panel.Panel;

/**
 * @author Juergen Donnerstag
 */
public final class SidebarTopRated extends Panel
{
	/**
	 * Constructor
	 * 
	 * @param parameters
	 */
	public SidebarTopRated(final String componentName, final BaseHtmlPage page)
	{
		super(componentName);

		final AddonService dao = page.getAddonService();
		final List top5 = dao.getTop5AddonsByRating();
		add(new ListView("rows", top5)
		{
			protected void populateItem(ListItem listItem)
			{
				final Addon addon = (Addon)listItem.getModelObject();
				listItem.add(new Label("rateing", new DecimalFormat("0.00").format(addon
						.getAverageRating())));

				final PageLink link = new PageLink("addon", new IPageLink()
				{
					public Page getPage()
					{
						return new PluginDetails(addon);
					}

					public Class getPageIdentity()
					{
						return PluginDetails.class;
					}
				});

				link.setAutoEnable(false);
				listItem.add(link);
				link.add(new Label("name", addon.getName()));
			}
		});

		add(new PageLink("ratingList", new IPageLink()
		{
			public Page getPage()
			{
				return new RatingList();
			}

			public Class getPageIdentity()
			{
				return RatingList.class;
			}
		}));
	}
}
