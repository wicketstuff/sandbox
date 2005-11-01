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
package wicket.addons.utils;

import java.text.SimpleDateFormat;
import java.util.Locale;

import wicket.Page;
import wicket.addons.CommentsPage;
import wicket.addons.PluginDetails;
import wicket.addons.ServiceLocator;
import wicket.addons.db.Addon;
import wicket.addons.services.AddonService;
import wicket.markup.html.basic.Label;
import wicket.markup.html.link.ExternalLink;
import wicket.markup.html.link.IPageLink;
import wicket.markup.html.link.Link;
import wicket.markup.html.link.PageLink;
import wicket.markup.html.panel.Panel;
import wicket.model.Model;
import wicket.model.PropertyModel;

/**
 * @author Juergen Donnerstag
 */
public class AddonListEntry extends Panel
{
    private final Long addonId;
    
    /**
     * Constructor
     * @param parameters
      */
    public AddonListEntry(final String componentName, final Addon addon)
    {
        super(componentName);
        this.addonId = addon.getId();

        add(new Label("name", new PropertyModel(addon, "name")));
        add(new Label("license", new PropertyModel(addon, "license")));
        add(new Label("category", new PropertyModel(addon.getCategory(), "name")));

        final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        final String dateString = dateFormat.format(addon.getLastUpdated());
        add(new Label("updated", dateString));

        add(new Label("version", new PropertyModel(addon, "version")));
        add(new Label("text", new PropertyModel(addon, "description")));

        final ExternalLink pluginHome = new ExternalLink("pluginHome", addon.getHomepage(), "plugin-homepage");
        add(pluginHome);
        
        String homepage = addon.getHomepage();
        if ((homepage == null) || (homepage.trim().length() == 0))
        {
            homepage = "(unknown)";
            pluginHome.setVisible(false);
        }
        add(new Label("homepage", homepage));

        add(new PageLink("details", new IPageLink()
        {
            public Page getPage()
            {
                return new PluginDetails(addon);
            }
            
            public Class getPageIdentity()
            {
                return PluginDetails.class;
            }
        }));
        
        final AddonService addonService = ServiceLocator.instance().getAddonService();

        add(new RatingLink("ratingLink", addon));

        final Link comments = new PageLink("comments", new IPageLink()
        {
            public Page getPage()
            {
                return new CommentsPage(addon);
            }
            
            public Class getPageIdentity()
            {
                return CommentsPage.class;
            }
        });
        
        add(comments);
        final Integer commentCount = addonService.getCommentCount(addon);
        comments.add(new Label("commentCount", new Model(commentCount)));

        if (addon.getCreateTime().before(addon.getLastUpdated()))
        {
            // TODO avoid fixed url path
            add(new ExternalImage("image", new Model("addons/images/updated.gif")));
        }
        else
        {
            // TODO avoid fixed url path
            add(new ExternalImage("image", new Model("addons/images/new.gif")));
        }
    }
}
