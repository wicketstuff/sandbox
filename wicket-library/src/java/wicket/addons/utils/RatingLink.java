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

import java.text.DecimalFormat;

import wicket.Page;
import wicket.addons.RateIt;
import wicket.addons.RatingDetails;
import wicket.addons.dao.Addon;
import wicket.addons.dao.AddonDaoImpl;
import wicket.markup.html.basic.Label;
import wicket.markup.html.link.IPageLink;
import wicket.markup.html.link.PageLink;
import wicket.markup.html.panel.Panel;

/**
 * 
 * @author Juergen Donnerstag
 */
public class RatingLink extends Panel
{
	/**
	 * Constructor
	 * 
	 * @param parameters
	 */
	public RatingLink(final String componentName, final Addon addon, final AddonDaoImpl dao)
	{
		super(componentName);

		final PageLink rating = new PageLink("rating", new IPageLink()
		{
			public Page getPage()
			{
				return new RatingDetails(addon.getId());
			}

			public Class getPageIdentity()
			{
				return RatingDetails.class;
			}
		});

		add(rating);

		final Object[] rateCountAndAverage = dao.getRatingCountAndAverage(addon);
		final int ratingCount = ((Integer)rateCountAndAverage[0]).intValue();

		if (ratingCount == 0)
		{
			rating.setAutoEnable(false);
			rating.setEnabled(false);
			rating.setBeforeDisabledLink("");
			rating.setAfterDisabledLink("");
			rating.add(new Label("rateCount", "not rated"));
		}
		else
		{
			final double ratingAvg = ((Float)rateCountAndAverage[1]).doubleValue();
			rating.add(new Label("rateCount", "rating (" + new DecimalFormat("#.0#").format(ratingAvg)
					+ " #" + ratingCount + ")"));
		}

		add(new PageLink("rateIt", new IPageLink()
		{
			public Page getPage()
			{
				return new RateIt(addon.getId());
			}

			public Class getPageIdentity()
			{
				return RateIt.class;
			}
		}));
	}
}
