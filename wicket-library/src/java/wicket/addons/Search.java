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
package wicket.addons;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import wicket.Page;
import wicket.PageParameters;
import wicket.addons.db.Addon;
import wicket.markup.html.WebMarkupContainer;
import wicket.markup.html.basic.Label;
import wicket.markup.html.form.Form;
import wicket.markup.html.form.TextField;
import wicket.markup.html.link.IPageLink;
import wicket.markup.html.link.PageLink;
import wicket.markup.html.list.ListItem;
import wicket.markup.html.list.ListView;
import wicket.markup.html.panel.FeedbackPanel;
import wicket.model.PropertyModel;

/**
 * @author Juergen Donnerstag
 */
public final class Search extends BaseHtmlPage /* AuthenticateHtmlPage */
{
	private List results = new ArrayList();
	private final WebMarkupContainer nothingFound;

	/**
	 * Constructor
	 * 
	 * @param parameters
	 */
	public Search(final PageParameters parameters)
	{
		super(parameters, "Wicket-Addons: Search page");

		final FeedbackPanel feedback = new FeedbackPanel("feedback");
		add(feedback);

		add(new SearchFullTextForm("formFullText"));

		add(new SearchForm("formByName"));

		add(new ListView("results", results)
		{
			protected void populateItem(ListItem item)
			{
				final Object modelObject = item.getModelObject();
				final Addon addon;
				final double score;

				if (modelObject instanceof AddonAndScore)
				{
					final AddonAndScore addonAndScore = (AddonAndScore)modelObject;
					addon = addonAndScore.getAddon();
					score = addonAndScore.getScore();
				}
				else
				{
					addon = (Addon)modelObject;
					score = 1;
				}

				item.add(new Label("score", "" + score));
				item.add(new Label("name", addon.getName()));
				item.add(new Label("category", addon.getCategory().getName()));
				item.add(new Label("version", addon.getVersion()));
				item.add(new Label("description", addon.getDescription()));

				item.add(new PageLink("details", new IPageLink()
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
			}
		});

		this.nothingFound = new WebMarkupContainer("nothingFound");
		this.nothingFound.setVisible(false);
		add(this.nothingFound);
	}

	public final class SearchFullTextForm extends Form
	{
		private String searchText;

		/**
		 * Constructor
		 * 
		 * @param componentName
		 *            Name of form
		 */
		public SearchFullTextForm(final String componentName)
		{
			super(componentName);

			add(new TextField("query", new PropertyModel(this, "searchText")));
		}

		public String getSearchText()
		{
			return searchText;
		}

		public void setSearchText(final String text)
		{
			this.searchText = text;
		}

		/**
		 * Show the resulting valid edit
		 * 
		 * @param cycle
		 *            The request cycle
		 */
		public final void onSubmit()
		{
			Search.this.nothingFound.setVisible(false);
			Search.this.get("results").modelChanging();

			// This is an example on how to get input from an <input>
			// even without a Wicket TextField.
			final String searchText = getSearchText();
			if ((searchText != null) && (searchText.trim().length() > 0))
			{
				final List data = getAddonService().searchAddon(searchText, 20);
				results.clear();
				for (Object obj : data)
				{
					final AddonAndScore addon = new AddonAndScore((Object[])obj);
					results.add(addon);
				}

				Search.this.nothingFound.setVisible(data.size() == 0);
			}
			else
			{
				results.clear();
			}

			Search.this.get("results").modelChanged();
		}
	}

	public final class SearchForm extends Form
	{
		private String searchText;

		/**
		 * Constructor
		 * 
		 * @param componentName
		 *            Name of form
		 */
		public SearchForm(final String componentName)
		{
			super(componentName);

			add(new TextField("query", new PropertyModel(this, "searchText")));
		}

		public String getSearchText()
		{
			return searchText;
		}

		public void setSearchText(final String text)
		{
			this.searchText = text;
		}

		/**
		 * Show the resulting valid edit
		 * 
		 * @param cycle
		 *            The request cycle
		 */
		public final void onSubmit()
		{
			Search.this.nothingFound.setVisible(false);
			Search.this.get("results").modelChanging();

			// This is an example on how to get input from an <input>
			// even without a Wicket TextField.
			final String searchText = getSearchText();
			if ((searchText != null) && (searchText.trim().length() > 0))
			{
				final List data = getAddonService().getAddonsByName(searchText, 20);
				results.clear();
				results.addAll(data);

				Search.this.nothingFound.setVisible(data.size() == 0);
			}
			else
			{
				results.clear();
			}

			Search.this.get("results").modelChanged();
		}
	}

	private final class AddonAndScore implements Serializable
	{
		private double score;
		private Addon addon;

		public AddonAndScore(final Object[] data)
		{
			this.score = ((Double)data[0]).doubleValue();
			this.addon = (Addon)data[1];
		}

		public double getScore()
		{
			return score;
		}

		public Addon getAddon()
		{
			return addon;
		}
	}
}
