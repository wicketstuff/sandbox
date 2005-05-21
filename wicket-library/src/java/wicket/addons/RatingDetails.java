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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import wicket.Page;
import wicket.addons.admin.AddOrModifyUser;
import wicket.addons.dao.Addon;
import wicket.addons.dao.IAddonDao;
import wicket.addons.dao.Rating;
import wicket.addons.dao.User;
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
    public RatingDetails(final int addonId)
    {
        super(null, "Wicket-Addons: Rating Details");
        
        final Addon addon = (Addon)getAddonDao().load(Addon.class, new Integer(addonId));
        final PageLink details = new PageLink("details", new IPageLink()
                {
					public Page getPage()
					{
						return new PluginDetails(addonId);
					}

					public Class getPageIdentity()
					{
						return PluginDetails.class;
					}
                });

        add(details);
        details.add(new Label("addon", addon.getName()));
        
        final IAddonDao dao = this.getAddonDao();

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
						return new RateIt(addonId);
					}

					public Class getPageIdentity()
					{
						return RateIt.class;
					}
                }));

        dao.initialize(addon.getRatings());
        add(new ListView("comments", addon.getRatings())
                {
					protected void populateItem(ListItem listItem)
					{
					    Rating rating = (Rating)listItem.getModelObject();
					    rating = (Rating) dao.load(rating);
					    
					    listItem.add(new Label("date", new Model(rating.getCreateDate())));
					    
					    dao.initialize(rating.getAddon().getOwner());
					    final User owner = rating.getAddon().getOwner();
					    final int ownerId = owner.getId();
					    final PageLink user = new PageLink("user", new IPageLink()
					            {
									public Page getPage()
									{
										return new AddOrModifyUser(ownerId);
									}
		
									public Class getPageIdentity()
									{
										return AddOrModifyUser.class;
									}
					            });
					    
					    listItem.add(user);
					    user.add(new Label("username", owner.getNickname()));

					    listItem.add(new Label("rating", new Model(new Integer(rating.getRating()))));
					    listItem.add(new Label("comment", rating.getComment()));
					}
                });
    }
}
