/*
 * Created on Jan 6, 2005
 */
package net.sf.ipn.dynweb;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.sf.ipn.dynweb.metadata.Attribute;
import net.sf.ipn.dynweb.metadata.panel.ListPanelData;
import net.sf.ipn.dynweb.visitor.CellBuilderVisitor;
import wicket.Page;
import wicket.markup.ComponentTag;
import wicket.markup.html.basic.Label;
import wicket.markup.html.link.IPageLink;
import wicket.markup.html.link.PageLink;
import wicket.markup.html.list.ListItem;
import wicket.markup.html.list.ListView;
import wicket.markup.html.list.PageableListView;
import wicket.markup.html.list.PageableListViewNavigator;
import wicket.markup.html.panel.Panel;
import wicket.model.IModel;

/**
 * @author Jonathan Carlson Lists a bunch of beans in a table (one bean per row)
 */
public class DynListPanel extends Panel
{

	/**
	 * Main constructor
	 * @param componentName - name of the component
	 * @param panelData - an instance of ListPanelData
	 * @param listModel - a List of models/beans to display
	 */
	public DynListPanel(String componentName, final ListPanelData panelData, final IModel listModel)
	{
		super(componentName);

		final CellBuilderVisitor cellBuilder = new CellBuilderVisitor();

		List colHeaderStrings = new ArrayList();
		colHeaderStrings.add("");
		for (Iterator iter = panelData.getAttributes().iterator(); iter.hasNext();)
		{
			Attribute attr = (Attribute)iter.next();
			colHeaderStrings.add(attr.getName());
		}

		ListView colHeaderListView = new ListView("columnHeaderRow", colHeaderStrings)
		{
			/**
			 * This is executed once for each column/attribute in the table/list.
			 * @see wicket.markup.html.list.ListPanelData#populateItem(wicket.markup.html.list.ListItem)
			 */
			public void populateItem(final ListItem colHeaderItem)
			{
				colHeaderItem.add(new Label("columnName", (String)colHeaderItem.getModelObject()));
			}
		};
		add(colHeaderListView);

		PageableListView table = new PageableListView("listRow", listModel, 3)
		{
			/**
			 * This is executed once for each row in the table.
			 * @see wicket.markup.html.list.ListPanelData#populateItem(wicket.markup.html.list.ListItem)
			 */
			public void populateItem(final ListItem row)
			{
				// Link to edit the row
				PageLink editLink = new PageLink("editLink", new IPageLink()
				{
					public Page getPage()
					{
						return new DynEditPage(panelData.getEditPanelData(), row.getModel());
					}

					public Class getPageIdentity()
					{
						return DynEditPage.class;
					}
				});
				if (panelData.getEditPanelData() == null)
					editLink.setVisible(false);
				row.add(editLink);

				// Loop to create each cell
				cellBuilder.setRowBean((Serializable)row.getModelObject());
				ListView listView = new ListView("listCell", panelData.getAttributes())
				{
					/**
					 * This is executed once for each cell in the row.
					 * @see wicket.markup.html.list.ListPanelData#populateItem(wicket.markup.html.list.ListItem)
					 */
					public void populateItem(final ListItem cell)
					{
						cellBuilder.setListItem(cell);
						final Attribute attr = (Attribute)cell.getModelObject();
						attr.visit(cellBuilder);
					}
				};
				row.add(listView);

			}

			protected ListItem newItem(final int index)
			{
				return new ListItem(index, getListItemModel(getModel(), index))
				{
					protected void onComponentTag(final ComponentTag tag)
					{
						// Alternate class as being "even" or "odd"
						tag.put("class", (getIndex() % 2) == 0 ? "even" : "odd");
						super.onComponentTag(tag);
					}
				};
			}

		};

		add(new PageableListViewNavigator("pagedTableNav", table));
		add(table);
	}

	public static void main(String[] args)
	{
		System.out.print("&&&&&&&& ");
		int i = 5;
		int j = 2;
		System.out.println(i / j);
		System.out.println(i = 2 % 2);
		System.out.println(i = 3 % 2);
		System.out.println(i = 4 % 2);
	}

}