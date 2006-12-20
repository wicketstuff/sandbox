/*
 * Created on Mar 21, 2005
 */
package net.sf.ipn.app;

import java.io.Serializable;

import wicket.Page;
import wicket.markup.ComponentTag;
import wicket.markup.html.WebMarkupContainer;
import wicket.markup.html.basic.Label;
import wicket.markup.html.link.IPageLink;
import wicket.markup.html.link.Link;
import wicket.markup.html.link.PageLink;
import wicket.markup.html.list.ListItem;
import wicket.markup.html.list.ListView;
import wicket.markup.html.panel.Panel;
import wicket.model.IModel;
import wicket.model.Model;

/**
 * Border component.
 */
public class IpnHeader extends Panel
{

	/**
	 * Constructor
	 * @param componentName The name of this component
	 */
	public IpnHeader(final String componentName)
	{
		super(componentName);
		add(new ListView("tabs")
		{
			protected IModel initModel()
			{
				return new Model((Serializable)getIpnBorderWebPage().getMainTabs());
			}

			protected void populateItem(final ListItem listItem)
			{
				final IpnTab tab = (IpnTab)listItem.getModelObject();
				WebMarkupContainer tabContainer = new WebMarkupContainer("tab")
				{
					protected void onComponentTag(final ComponentTag tag)
					{
						tag.put("class", getIpnBorderWebPage().belongsToTab(tab)
								? "horizontalSelectedTab"
								: "horizontalUnselectedTab");
						super.onComponentTag(tag);
					}
				};
				listItem.add(tabContainer);

				final Link tabLink = new PageLink("tabLink", new IPageLink()
				{
					public Page getPage()
					{
						return tab.getPage();
					}

					public Class getPageIdentity()
					{
						return tab.getPage().getClass();
					}
				}); // end Link subclass
				tabContainer.add(tabLink);
				tabLink.add(new Label("tabLabel", tab.getLabel()));
			} // populateItem
		});
	}

	public IpnBorderWebPage getIpnBorderWebPage()
	{
		return (IpnBorderWebPage)getPage();
	}

}