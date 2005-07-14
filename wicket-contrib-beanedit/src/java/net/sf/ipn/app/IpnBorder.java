/*
 * Created on Mar 21, 2005
 */
package net.sf.ipn.app;

import java.io.Serializable;

import wicket.AttributeModifier;
import wicket.Page;
import wicket.markup.html.WebMarkupContainer;
import wicket.markup.html.basic.Label;
import wicket.markup.html.border.Border;
import wicket.markup.html.link.IPageLink;
import wicket.markup.html.link.Link;
import wicket.markup.html.link.PageLink;
import wicket.markup.html.list.ListItem;
import wicket.markup.html.list.ListView;
import wicket.model.IModel;
import wicket.model.Model;

/**
 * Provides the main navigation border for the prayer network
 */
public class IpnBorder extends Border
{

	public IpnBorder(final String componentName)
	{
		super(componentName);
		add(new ListView("subnavs")
		{
			protected IModel initModel()
			{
				return new Model((Serializable)getIpnBorderWebPage().getMainTabs());
			}

			protected void populateItem(final ListItem listItem)
			{
				final IpnTab tab = (IpnTab)listItem.getModelObject();
				WebMarkupContainer tabContainer = new WebMarkupContainer("subnav");
				String tabClass = getIpnBorderWebPage().belongsToTab(tab) ? "on" : "";
				if (listItem.getIndex() == 0)
					tabClass = "first " + tabClass;
				tabContainer.add(new AttributeModifier("class", new Model(tabClass)));
				listItem.add(tabContainer);

				final Link tabLink = new PageLink("subnavLink", new IPageLink()
				{
					public Page getPage()
					{
						return tab.getPage();
					}

					public Class getPageIdentity()
					{
						return tab.getPageClass();
					}
				}); // end Link subclass
				tabContainer.add(tabLink);
				tabLink.add(new Label("subnavLabel", tab.getLabel()));
			} // populateItem
		});
	}

	public IpnBorderWebPage getIpnBorderWebPage()
	{
		return (IpnBorderWebPage)getPage();
	}

}