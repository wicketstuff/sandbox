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

import java.text.DecimalFormat;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import wicket.Page;
import wicket.addons.admin.AddOrModifyUser;
import wicket.addons.dao.Addon;
import wicket.addons.dao.AddonDaoImpl;
import wicket.addons.dao.Rating;
import wicket.addons.utils.RatingChart;
import wicket.markup.html.basic.Label;
import wicket.markup.html.link.IPageLink;
import wicket.markup.html.link.PageLink;
import wicket.markup.html.list.ListItem;
import wicket.markup.html.list.ListView;
import wicket.model.Model;

/**
 * @author Juergen Donnerstag
 */
public final class RatingDetails extends BaseHtmlPage /* AuthenticateHtmlPage */
{
    private static Log log = LogFactory.getLog(RatingDetails.class);
    
    /**
     * Constructor
     * @param parameters
      */
    public RatingDetails(final Addon addon)
    {
        super(null, "Wicket-Addons: Rating Details");
        
        final PageLink details = new PageLink("details", new IPageLink()
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

        add(details);
        details.add(new Label("addon", addon.getName()));
        
        final AddonDaoImpl dao = this.getAddonDao();

        final Object[] rateCountAndAverage = dao.getRatingCountAndAverage(addon);
        final Integer ratingCount = (Integer)rateCountAndAverage[0];
        final Float ratingAvg = (Float)rateCountAndAverage[1];
        
        add(new Label("numberOfRatings", new Model(ratingCount)));
        add(new Label("average", new DecimalFormat("#.0#").format(ratingAvg)));

        
        add(new RatingChart("chart", addon));
        
        add(new PageLink("rateIt", new IPageLink()
                {
					public Page getPage()
					{
						return new RateIt(addon);
					}

					public Class getPageIdentity()
					{
						return RateIt.class;
					}
                }));
        
        // Lazy loading ratings with addon.getRatings() fails because the 
        // session used to load the addon object has already been closed
        final List comments = dao.getRatingsByAddon(addon);
        
        add(new ListView("comments", comments)
                {
					protected void populateItem(ListItem listItem)
					{
					    final Rating comment = (Rating)listItem.getModelObject();
					    
					    listItem.add(new Label("date", new Model(comment.getCreateDate())));
					    
					    final PageLink user = new PageLink("user", new IPageLink()
					            {
									public Page getPage()
									{
										return new AddOrModifyUser(comment.getAddon().getOwner());
									}
		
									public Class getPageIdentity()
									{
										return AddOrModifyUser.class;
									}
					            });
					    
					    listItem.add(user);
					    user.add(new Label("username", comment.getAddon().getOwner().getNickname()));

					    listItem.add(new Label("rating", new Model(new Integer(comment.getRating()))));
					    listItem.add(new Label("comment", comment.getComment()));
					}
                });
    }
}
