/*
 * Created on Dec 28, 2004
 */
package net.sf.ipn.dynweb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.sf.ipn.dynweb.metadata.panel.EditPanelData;
import net.sf.ipn.dynweb.metadata.panel.ListPanelData;
import net.sf.ipn.dynweb.metadata.panel.PanelData;
import net.sf.ipn.dynweb.metadata.panel.SubPanelData;
import ognl.OgnlException;
import wicket.markup.ComponentTag;
import wicket.markup.html.basic.Label;
import wicket.markup.html.link.Link;
import wicket.markup.html.panel.Panel;
import wicket.markup.html.list.ListItem;
import wicket.markup.html.list.ListView;
import wicket.model.IModel;
import wicket.model.Model;
import wicket.model.PropertyModel;

/**
 * @author Jonathan Carlson Builds a tabbed area where each tab displays some related
 *         information.
 */
public class DynRelationshipsPanel extends Panel
{

	private List panelInfoList = new ArrayList();
	private PanelInfo selectedPanelInfo;

	/**
	 * @param model - the bean whose relationships will be displayed.
	 */
	public DynRelationshipsPanel(String componentName, final List subPanelDatas, final IModel model)
	{
		super(componentName);
		final DynRelationshipsPanel mainPanel = this;

		for (Iterator iter = subPanelDatas.iterator(); iter.hasNext();)
		{
			SubPanelData subPanelData = (SubPanelData)iter.next();
			Panel panel = newTabContentPanel(subPanelData, model);
			panelInfoList.add(new PanelInfo(subPanelData, panel));
		}

		selectedPanelInfo = (PanelInfo)panelInfoList.get(0);

		// Creates a row of tabs, "highlighting" the selected one.
		ListView tabs = new ListView("tabs", panelInfoList)
		{
			protected void populateItem(ListItem listItem)
			{
				// Create a Label for each SubPanel/tab
				final PanelInfo panelInfo = (PanelInfo)listItem.getModelObject();
				final Link tabLink = new Link("tabLink")
				{
					/**
					 * Sets the selected panel to be visible
					 * @see wicket.markup.html.link.ILinkListener#linkClicked()
					 */
					public void onClick()
					{
						selectedPanelInfo = panelInfo;
						// Set the selected panel to be visible and the others invisible
						for (int i = 0; i < panelInfoList.size(); i++)
						{
							PanelInfo temp = (PanelInfo)panelInfoList.get(i);
							boolean visible = temp.equals(selectedPanelInfo);
							temp.getPanel().setVisible(visible);
						}
						getRequestCycle().setRedirect(true);
					}
				}; // end Link subclass
				listItem.add(tabLink);
				tabLink.add(new Label("tabLabel", panelInfo.getPanelDataName()));
			}

			protected ListItem newItem(final int index)
			{
				// final PanelInfo panelInfo = (PanelInfo) getListObject(index);
				final PanelInfo panelInfo = (PanelInfo)getModelObject();
				return new ListItem(index, getListItemModel(getModel(), index))
				{
					protected void onComponentTag(ComponentTag tag)
					{
						super.onComponentTag(tag);
						String tagClass = "unselectedTab";
						if (selectedPanelInfo.equals(panelInfo))
						{
							tagClass = "selectedTab";
						}
						tag.put("class", tagClass);
					}
				};
			}
		};
		add(tabs);

		// Create a panel for each "tab". Make visible only the selected one.
		ListView contentListView = new ListView("contentListView", this.panelInfoList)
		{
			protected void populateItem(ListItem listItem)
			{
				PanelInfo p = (PanelInfo)listItem.getModelObject();
				listItem.add(p.getPanel());
				p.getPanel().setVisible((p.equals(selectedPanelInfo)));
			}
		};
		add(contentListView);
	} // constructor


	private Panel newTabContentPanel(SubPanelData subPanelData, IModel modelObj)
	{
		// Get the relationship
		Object relatedObj = getValue(subPanelData.getExpression(), modelObj);
		IModel relatedModel = new PropertyModel(modelObj, subPanelData.getExpression());
		// Create a DynListPanel or DynFormPanel for the selected SubPanel
		Panel subpanel = null;
		PanelData panelData = subPanelData.getPanelData();
		if (panelData instanceof ListPanelData)
			subpanel = new DynListPanel("tabContent", (ListPanelData)panelData, new Model(
					(Serializable)relatedObj));
		else if (panelData instanceof EditPanelData)
			subpanel = new DynEditPanelOld("tabContent", (EditPanelData)panelData, relatedModel);
		else
			throw new RuntimeException("Invalid PanelData type: " + panelData);
		return subpanel;
	}


	/**
	 * Executes the given ognl expression against the given object and returns the result.
	 * @param expression an OGNL expression to be executed against the given object
	 * @param obj the subject against which the OGNL expression is used.
	 * @return the result of the ognl expression against the given object.
	 */
	private Object getValue(String expression, Object obj)
	{
		Object result = null;
		try
		{
			result = ognl.Ognl.getValue(expression, obj);
		}
		catch (OgnlException e)
		{
			throw new RuntimeException("LovOptionList found an invalid Ognl expression: '"
					+ expression + "'", e);
		}
		return result;
	}

	/**
	 * @author Jonathan Carlson Links a Dyn*Panel with it's associated meta data
	 *         (*PanelData)
	 */
	private class PanelInfo implements Serializable
	{
		SubPanelData subPanelData = null;
		Panel panel = null;

		PanelInfo(SubPanelData subPanelData, Panel panel)
		{
			if (subPanelData == null)
				throw new IllegalArgumentException("subPanelData arg may not be null.");
			if (panel == null)
				throw new IllegalArgumentException("panel arg may not be null.");
			this.subPanelData = subPanelData;
			this.panel = panel;
		}

		SubPanelData getSubPanelData()
		{
			return this.subPanelData;
		}

		Panel getPanel()
		{
			return this.panel;
		}

		String getSubPanelDataExpression()
		{
			return this.subPanelData.getExpression();
		}

		String getPanelDataName()
		{
			return this.subPanelData.getPanelData().getName();
		}

		public boolean equals(Object o)
		{
			if (!(o instanceof PanelInfo))
				return false;
			PanelInfo other = (PanelInfo)o;
			return (this.subPanelData.getExpression().equals(other.getSubPanelData()
					.getExpression()));
		}

		public int hashCode()
		{
			return this.subPanelData.getExpression().hashCode();
		}

	}


} // class
