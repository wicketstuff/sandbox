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
package wicket.addons.admin;

import java.io.Serializable;
import java.util.List;

import wicket.AttributeModifier;
import wicket.Page;
import wicket.PageParameters;
import wicket.addons.BaseHtmlPage;
import wicket.addons.db.Addon;
import wicket.markup.html.basic.Label;
import wicket.markup.html.link.IPageLink;
import wicket.markup.html.link.PageLink;
import wicket.markup.html.list.ListItem;
import wicket.markup.html.list.ListView;
import wicket.model.Model;

/**
 * @author Juergen Donnerstag
 */
public final class Addons extends BaseHtmlPage /* AuthenticateHtmlPage */
{
	private static final String PAGE_TITLE = "Wicket-Addons: Maintain addons";

	private ListView table;

	/**
	 * Constructor
	 * 
	 * @param parameters
	 */
	public Addons(final PageParameters parameters)
	{
		super(parameters, PAGE_TITLE);

		final List addons = getAddons();

		this.table = new ListView("rows", addons)
		{
			public void populateItem(final ListItem listItem)
			{
				// alternating row styles
				listItem.add(new AttributeModifier("class", new Model(
						(listItem.getIndex() % 2) == 0 ? "even" : "odd")));

				final Addon value = (Addon)listItem.getModelObject();

				listItem.add(new MyPageLink("name", value, value.getName() + " ("
						+ value.getVersion() + ")", Addons.this));
				listItem.add(new MyPageLink("category", value, value.getCategory().getName(),
						Addons.this));
				listItem
						.add(new MyPageLink("modified", value, value.getLastUpdated(), Addons.this));
				listItem.add(new MyPageLink("homepage", value, value.getHomepage(), Addons.this));
				listItem.add(new MyPageLink("description", value,
						Addons.this.getDescription(value), Addons.this));
			}
		};

		add(table);

		get("sidebarsRight").setVisible(false);
	}

	private String getDescription(final Addon addon)
	{
		String descr = addon.getDescription();
		if (descr == null)
		{
			return "";
		}

		if (descr.length() < 20)
		{
			return descr;
		}

		return descr.substring(0, 20) + "...";
	}

	private List getAddons()
	{
		return getAddonService().getAddonsSortedByName(0, 99);
	}

	/**
	 * @see wicket.Component#onRender(wicket.RequestCycle)
	 */
	protected void onBeginRequest()
	{
		final List addons = getAddons();
		this.table.setModelObject(addons);
	}

	private class MyPageLink extends PageLink
	{
		public MyPageLink(final String componentName, final Addon addon, final Serializable value,
				final Page page)
		{
			super(componentName + "Link", new IPageLink()
			{
				public Page getPage()
				{
					return new AddOrModifyAddon(addon, page);
				}

				public Class getPageIdentity()
				{
					return addon.getClass();
				}
			});

			String label = (value != null ? value.toString() : "&nbsp");
			if (label.length() == 0)
			{
				label = "&nbsp;";
			}

			add(new Label(componentName, label).setEscapeModelStrings(false));
		}
	}
}
