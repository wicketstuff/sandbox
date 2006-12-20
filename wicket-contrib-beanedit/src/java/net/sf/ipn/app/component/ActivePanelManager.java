/*
 * Created on May 27, 2005
 */
package net.sf.ipn.app.component;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import wicket.AttributeModifier;
import wicket.Component;
import wicket.markup.html.WebMarkupContainer;
import wicket.markup.html.panel.Panel;
import wicket.model.Model;

/**
 * Manages a group of panels, of which only one is active/visible at any given time. It is
 * assumed that each panel has a Link component which, when clicked, calls the
 * #activate(linkComponent) method.
 * <p>
 * (This could work for a tabbed panel)
 * <p>
 * When a Link is clicked the class attribute of the Link component (or its parent, that
 * is, whatever is passed in as the linkComponent attribute to the addPanel() method)
 * <p>
 * The term "link component" here is a shorthand notation because it is actually possible
 * to use any component whose class attribute will be modified as it is activated and
 * inactivated.
 * <p>
 * To customize the class attribute value of the link, set these properties:
 * activeLinkClass and inactiveLinkClass
 * @author Jonathan Carlson
 * @created May 2005
 */
public class ActivePanelManager implements Serializable
{
	private String activeLinkClass = "on";
	private String inactiveLinkClass = "";
	List actions = new LinkedList();

	/**
	 * Associates the given link component with a panel. This class assumes the using
	 * class will make the link component call activate(WebMarkupContainer) when the link
	 * is clicked.
	 * @param linkComponent - Not necessarily a Link, but could be a surrounding
	 *            <ul>, <div> or other tag whose css class attribute changes as it is
	 *            activated and deactivated.
	 * @param panel - The panel to be made visible when #activate() is called for the
	 *            given link component.
	 */
	public void addPanel(WebMarkupContainer linkComponent, Panel panel)
	{
		LinkClassModel linkClassModel = new LinkClassModel();
		AttributeModifier attrMod = new AttributeModifier("class", linkClassModel);
		linkComponent.add(attrMod);
		this.actions.add(new LinkPanelAssoc(linkComponent, linkClassModel, panel));
	}

	/**
	 * Makes visible the associated panel and invisiable all the other panels as well as
	 * modifying the class attr of the linkComponents.
	 * @param linkComponent
	 */
	public void activate(Panel panel)
	{
		for (Iterator iter = this.actions.iterator(); iter.hasNext();)
		{
			LinkPanelAssoc temp = (LinkPanelAssoc)iter.next();
			boolean active = (temp.panel == panel);
			temp.linkClassModel.setActive(active);
			temp.panel.setVisible(active);
		}
	}

	/**
	 * Returns the class attribute value for the active link component. Defaults to "on".
	 */
	public String getActiveLinkClass()
	{
		return this.activeLinkClass;
	}

	public void setActiveLinkClass(String value)
	{
		this.activeLinkClass = value;
	}

	/**
	 * Returns the class attribute value for the inactive link components. Defaults to an
	 * empty string ("").
	 */
	public String getInactiveLinkClass()
	{
		return this.inactiveLinkClass;
	}

	public void setInactiveLinkClass(String value)
	{
		this.inactiveLinkClass = value;
	}

	/**
	 * Supplies the appropriate value for the class attribute for the panel links
	 * @author Jonathan Carlson
	 */
	private class LinkClassModel extends Model
	{
		private boolean active = false;

		public boolean getActive()
		{
			return this.active;
		}

		public void setActive(boolean active)
		{
			this.active = active;
		}

		public Object getObject(Component o)
		{
			return getActive() ? getActiveLinkClass() : getInactiveLinkClass();
		}
	}

	/**
	 * Provides a way to associate a link with a panel
	 * @author Jonathan Carlson
	 */
	private static class LinkPanelAssoc implements Serializable
	{
		public WebMarkupContainer link;
		/**
		 * The active/inactive class attribute modifier for the link (or surrounding)
		 * component
		 */
		public LinkClassModel linkClassModel;
		public Panel panel;

		public LinkPanelAssoc(WebMarkupContainer link, LinkClassModel linkClassModel, Panel panel)
		{
			this.link = link;
			this.linkClassModel = linkClassModel;
			this.panel = panel;
		}
	}

}
