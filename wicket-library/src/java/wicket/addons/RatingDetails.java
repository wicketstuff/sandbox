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
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import wicket.Page;
import wicket.addons.admin.AddOrModifyUser;
import wicket.addons.db.Addon;
import wicket.addons.db.Rating;
import wicket.addons.db.User;
import wicket.addons.models.AddonModel;
import wicket.addons.services.AddonService;
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
        
        getUserService().lock(addon, 0);
        final AddonModel addonModel = new AddonModel(addon);
        
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
        
        final AddonService addonService = this.getAddonService();

        final Object[] rateCountAndAverage = addonService.getRatingCountAndRatingAverage(addon);
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

        List data = new ArrayList();
        data.addAll(addonModel.getRatings());
        add(new ListView("comments", data)
                {
					protected void populateItem(ListItem listItem)
					{
					    Rating rating = (Rating)listItem.getModelObject();
					    
					    listItem.add(new Label("date", new Model(rating.getCreated())));
					    
					    final User owner = rating.getAddon().getOwner();
					    final Long ownerId = owner.getId();
					    final PageLink user = new PageLink("user", new IPageLink()
					            {
									public Page getPage()
									{
										return new AddOrModifyUser(owner);
									}
		
									public Class getPageIdentity()
									{
										return AddOrModifyUser.class;
									}
					            });
					    
					    listItem.add(user);
					    user.add(new Label("username", owner.getLoginName()));

					    listItem.add(new Label("rating", new Model(new Integer(rating.getRating()))));
					    listItem.add(new Label("comment", rating.getComment()));
					}
                });
    }
}
