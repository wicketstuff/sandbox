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
package wicket.addons;

import java.util.List;

import wicket.Page;
import wicket.addons.db.Addon;
import wicket.addons.services.AddonService;
import wicket.addons.utils.CategoryDropDownChoice;
import wicket.markup.html.basic.Label;
import wicket.markup.html.form.Form;
import wicket.markup.html.link.IPageLink;
import wicket.markup.html.link.PageLink;
import wicket.markup.html.list.ListItem;
import wicket.markup.html.list.PageableListView;
import wicket.markup.html.navigation.paging.PagingNavigator;
import wicket.markup.html.panel.FeedbackPanel;
import wicket.model.Model;

/**
 * @author Juergen Donnerstag
 */
public final class ClickList extends BaseHtmlPage /* AuthenticateHtmlPage */
{
    private final PageableListView listing;
    
    /**
     * Constructor
     * @param parameters
     */
    public ClickList()
    {
        super(null, "Addon specific comments");

        final AddonService addonService = this.getAddonService();

        // Create and add feedback panel to page
        final FeedbackPanel feedback = new FeedbackPanel("feedback");
        //add(feedback);

        add(new RatingListForm("form", addonService));
        
        final List listingData = addonService.getAddonsByClicks(0, null);
        listing = new PageableListView("listing", listingData, 30)
        {
            public void populateItem(final ListItem listItem)
            {
                final Addon addon = (Addon)listItem.getModelObject();
                
                listItem.add(new Label("nr", new Model(new Integer(listItem.getIndex() + 1))));
                listItem.add(new Label("24h", new Model(new Integer(addon.getClicksLast24H()))));
                listItem.add(new Label("week", new Model(new Integer(addon.getClicksLastWeek()))));
                listItem.add(new Label("month", new Model(new Integer(addon.getClicksLastMonth()))));
                listItem.add(new Label("all", new Model(new Integer(addon.getClicksAll()))));
                
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
                
                listItem.add(link);
                
                link.add(new Label("name", addon.getName()));
            }
        };

        add(listing);
        add(new PagingNavigator("pageTableNav1", listing));
        add(new PagingNavigator("pageTableNav2", listing));
    }

    public final class RatingListForm extends Form
    {
        private final CategoryDropDownChoice categories;
        
        /**
         * Constructor
         * @param componentName Name of form
         * @param book Book model
         * @param feedback Feedback component that shows errors
         */
        public RatingListForm(final String componentName, final AddonService addonService)
        {
            super(componentName);
            
            categories = new CategoryDropDownChoice("categories", null);
            add(categories);
        }
        
        /**
         * Show the resulting valid edit
         * @param cycle The request cycle
         */
        public final void onSubmit()
        {
            listing.setModelObject(getAddonService().getAddonsByClicks(0, null));
            listing.removeAll();
            getRequestCycle().setResponsePage(getPage());
        }
    }
}
