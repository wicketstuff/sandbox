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
package wicket.addons.utils;

import java.util.ArrayList;
import java.util.List;

import wicket.addons.ServiceLocator;
import wicket.addons.db.Addon;
import wicket.addons.services.AddonService;
import wicket.markup.html.list.ListItem;
import wicket.markup.html.list.ListView;
import wicket.markup.html.panel.Panel;

/**
 * @author Juergen Donnerstag
 */
public class RatingChart extends Panel
{
	/**
	 * Constructor
	 * 
	 * @param parameters
	 */
	public RatingChart(final String componentName, final Addon addon)
	{
		super(componentName);

		final AddonService dao = ServiceLocator.instance().getAddonService();

		final Object[] rateCountAndAverage = dao.getRatingCountAndRatingAverage(addon);
		final int ratingCount = ((Integer)rateCountAndAverage[0]).intValue();

		final List chartData = dao.getRatingChartData(addon);

		int maxCount = -1;
		for (Object data : chartData)
		{
			final Object[] ratingAndCount = (Object[])data;
			final int count = ((Integer)ratingAndCount[1]).intValue();
			if (count > maxCount)
			{
				maxCount = count;
			}
		}

		final List columns = new ArrayList();
		for (int i = 0, j = 0; i < 10; i++)
		{
			if (chartData.size() > j)
			{
				final Object[] ratingAndCount = (Object[])chartData.get(j);
				final int rating = ((Integer)ratingAndCount[0]).intValue();

				if (rating == i)
				{
					final int count = ((Integer)ratingAndCount[1]).intValue();
					final int value = 10 * count / maxCount;
					columns.add(new Integer(value));
					j += 1;
					continue;
				}
			}

			columns.add(new Integer(0));
		}

		add(new ListView("column", columns)
		{
			protected void populateItem(ListItem listItem)
			{
				final int value = ((Integer)listItem.getModelObject()).intValue();

				final List values = new ArrayList();
				for (int i = 0; i < value; i++)
				{
					values.add(new Integer(i));
				}

				listItem.add(new ListView("value", values)
				{
					protected void populateItem(ListItem listItem)
					{
						// no action. The listItem does not have a content.
						// Just repeat the item "border" x times.
					}
				});
			}
		});
	}
}
