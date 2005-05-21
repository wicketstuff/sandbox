/*
 * $Id$
 * $Revision$
 * $Date$
 *
 * ====================================================================
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package wicket.addons.sidebars;

import java.util.List;

import wicket.Page;
import wicket.addons.BaseHtmlPage;
import wicket.addons.ClickList;
import wicket.addons.PluginDetails;
import wicket.addons.dao.Addon;
import wicket.addons.dao.IAddonDao;
import wicket.markup.html.basic.Label;
import wicket.markup.html.link.IPageLink;
import wicket.markup.html.link.PageLink;
import wicket.markup.html.list.ListItem;
import wicket.markup.html.list.ListView;
import wicket.markup.html.panel.Panel;
import wicket.model.Model;

/**
 * @author Juergen Donnerstag
 */
public final class SidebarTopClicks extends Panel
{
    /**
     * Constructor
     * @param parameters
      */
    public SidebarTopClicks(final String componentName, final BaseHtmlPage page)
    {
        super(componentName);
        
        final IAddonDao dao = page.getAddonDao();
        final List top5 = dao.getTop5AddonsByClicks();
        add(new ListView("rows", top5)
                {
					protected void populateItem(ListItem listItem)
					{
					    final Addon addon = (Addon) listItem.getModelObject();
					    listItem.add(new Label("clicks", new Model(new Integer(addon.getClicksLastWeek()))));
					    
					    final PageLink link = new PageLink("addon", new IPageLink()
					            {
									public Page getPage()
									{
										return new PluginDetails(addon.getId());
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
        
        add(new PageLink("clickList", new IPageLink()
                {
					public Page getPage()
					{
						return new ClickList();
					}

					public Class getPageIdentity()
					{
						return ClickList.class;
					}
                }));
    }
}
