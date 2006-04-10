/*
 * $Id$ $Revision$ $Date:
 * 2005-11-01 19:10:22 +0100 (Di, 01 Nov 2005) $
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
package wicket.addons;

import java.util.List;

import wicket.Page;
import wicket.PageParameters;
import wicket.addons.db.News;
import wicket.markup.html.link.IPageLink;
import wicket.markup.html.link.PageLink;
import wicket.markup.html.list.ListItem;
import wicket.markup.html.list.ListView;

/**
 * 
 * @author Juergen Donnerstag
 */
public final class Home extends BaseHtmlPage /* AuthenticateHtmlPage */
{
	private static final int NUMBER_OF_NEWS_PER_PAGE = 10;

	private final ListView newsList;
	private int newsIndex = 0;

	public Home()
	{
		this(null);
	}

	/**
	 * Constructor
	 * 
	 * @param parameters
	 */
	public Home(final PageParameters parameters)
	{
		super(parameters, "Wicket Library Homepage");
		/*
		 * StringResourceModel model = new
		 * StringResourceModel("heading.welcome", this, null); add(new
		 * Label("heading", model.getString()));
		 */
		final List newsData = ServiceLocator.instance().getNewsService().getNews(newsIndex,
				NUMBER_OF_NEWS_PER_PAGE);

		// News
		newsList = new ListView("newsList", newsData)
		{
			public void populateItem(final ListItem listItem)
			{
				final ContentNews value = new ContentNews("news", (News)listItem.getModelObject());
				listItem.add(value);
			}
		};

		add(newsList);

		add(new PageLink("older", new OlderPageLink(NUMBER_OF_NEWS_PER_PAGE)));
		add(new PageLink("newer", new OlderPageLink(-NUMBER_OF_NEWS_PER_PAGE)));

		get("newer").setVisible(false);
		if (newsData.size() < NUMBER_OF_NEWS_PER_PAGE)
		{
			get("older").setVisible(false);
		}
	}

	private class OlderPageLink implements IPageLink
	{
		private int offset;

		public OlderPageLink(final int offset)
		{
			this.offset = offset;
		}

		public Page getPage()
		{
			newsIndex = newsIndex + offset;
			final List newsData = ServiceLocator.instance().getNewsService().getNews(newsIndex,
					NUMBER_OF_NEWS_PER_PAGE);
			newsList.modelChanging();
			newsList.setModelObject(newsData);

			get("older").setVisible(newsData.size() == NUMBER_OF_NEWS_PER_PAGE);
			get("newer").setVisible(newsIndex >= NUMBER_OF_NEWS_PER_PAGE);

			newsList.modelChanged();
			return Home.this;
		}

		public Class getPageIdentity()
		{
			return this.getClass();
		}
	}
}
