/*
 * Created on May 5, 2005
 */
package net.sf.ipn.app.page;

import java.util.List;

import net.sf.ipn.app.IpnBorderWebPage;
import net.sf.ipn.app.component.ActivePanelManager;
import net.sf.ipn.app.component.CayenneDataObjectModel;
import net.sf.ipn.app.component.CayenneListPanel;
import net.sf.ipn.app.component.CayenneQueryBuilder;
import net.sf.ipn.app.component.CayenneQueryModel;
import net.sf.ipn.app.component.QueryFilter;
import net.sf.ipn.data.Lang;
import net.sf.ipn.data.PrayerItem;
import net.sf.ipn.dynweb.DynEditPanel;

import org.objectstyle.cayenne.exp.Expression;
import org.objectstyle.cayenne.exp.ExpressionFactory;
import org.objectstyle.cayenne.query.GenericSelectQuery;
import org.objectstyle.cayenne.query.SelectQuery;

import wicket.AttributeModifier;
import wicket.markup.html.WebMarkupContainer;
import wicket.markup.html.basic.Label;
import wicket.markup.html.link.Link;
import wicket.markup.html.list.ListItem;
import wicket.markup.html.list.PageableListView;
import wicket.markup.html.list.PageableListViewNavigator;
import wicket.markup.html.panel.Panel;
import wicket.model.Model;
import wicket.model.PropertyModel;

/**
 * @author Jonathan Carlson
 */
public class PrayerItems extends IpnBorderWebPage
{
	private final ActivePanelManager panelManager = new ActivePanelManager();
	private final ListPanel listPanel;
	private final EditPanel editPanel;

	/**
	 * 
	 */
	public PrayerItems()
	{
		super();

		this.listPanel = new ListPanel("itemListPanel");
		addPanel("listButton", "listLink", listPanel);

		this.editPanel = new EditPanel("itemEditPanel");
		this.editPanel.complete();
		addPanel("createButton", "createLink", editPanel);

		// Activate the default panel
		this.panelManager.activate(this.listPanel);
	}

	/**
	 * Sets up an action "button" and link that activates the given panel.
	 * @param buttonId - the wicket:id for the tag surrounding the link.
	 * @param linkId - the wicket id for the link
	 * @param panel - the panel to add
	 */
	private void addPanel(String buttonId, String linkId, final Panel panel)
	{
		final WebMarkupContainer button = new WebMarkupContainer(buttonId);
		add(panel);
		add(button);
		button.add(new Link(linkId)
		{
			public void onClick()
			{
				PrayerItems.this.panelManager.activate(panel);
				// This is a little hokey. It might be nice to find a different way.
				// Tell the EditPanel to create a new PrayerItem.
				if (panel == editPanel)
					editPanel.setPrayerItemId(null);
			}
		});
		this.panelManager.addPanel(button, panel);
	}

	/**
	 * @see net.sf.ipn.app.IpnWebPage#getTabLabel()
	 */
	public String getTabLabel()
	{
		return PRAYER_ITEMS_TAB;
	}

	/**
	 * Lists prayer items and allows dynamic sorting and filtering of the results.
	 * @author Jonathan Carlson
	 */
	public class ListPanel extends CayenneListPanel
	{

		public ListPanel(String id)
		{
			super(id);

			CayenneQueryBuilder builder = new CayenneQueryBuilder()
			{
				public SelectQuery buildBaseQuery()
				{
					SelectQuery query = new SelectQuery(PrayerItem.class);
					query.setPageSize(10);
					query.setCachePolicy(GenericSelectQuery.LOCAL_CACHE);
					query.setQualifier(ExpressionFactory.matchExp("prayerGroup.owningUser",
							getIpnSession().getUser()));
					return query;
				}
			};

			CayenneQueryModel model = new CayenneQueryModel(getDataContext(), builder);

			PageableListView listView = new PageableListView("dataRow", model, 10)
			{
				protected void populateItem(ListItem listItem)
				{
					final PrayerItem prayerItem = (PrayerItem)listItem.getModelObject();
					listItem.add(new Link("editLink")
					{
						public void onClick()
						{
							editPanel.setPrayerItemId(prayerItem.getId());
							panelManager.activate(editPanel);
						}
					});
					listItem.add(new Label("group", new PropertyModel(prayerItem,
							"prayerGroup.name")));
					listItem.add(new Label("type",
							new PropertyModel(prayerItem, "type.description")));
					listItem.add(new Label("status", new PropertyModel(prayerItem,
							"status.description")));
					listItem.add(new Label("subject", new PropertyModel(prayerItem, "subject")));
					listItem.add(new AttributeModifier("class", new Model(
							listItem.getIndex() % 2 == 0 ? "evenRow" : "oddRow")));
				}
			};
			add(listView);

			add(new PageableListViewNavigator("pageableNav", listView));

			List groups = getIpnSession().getUser().getGroupList();
			add(newFilterDropDown("groupFilter", "prayerGroup", builder, groups));

			List types = getDataContext().performQuery("AllPrayerItemTypes", false);
			add(newFilterDropDown("typeFilter", "type", builder, types));

			List statuses = getDataContext().performQuery("AllPrayerItemStatuses", false);
			add(newFilterDropDown("statusFilter", "status", builder, statuses));

			add(newFilterTextField("subjectFilter", "subject", builder));

			add(newSortLink("groupHeader", "prayerGroup.name", builder));
			add(newSortLink("statusHeader", "status.description", builder));
			add(newSortLink("typeHeader", "type.description", builder));
			add(newSortLink("subjectHeader", "subject", builder));
		}

	} // ListPanel

	/**
	 * Creates and modifies prayer items
	 * @author Jonathan Carlson
	 */
	public class EditPanel extends DynEditPanel
	{
		private Long prayerItemId;
		private CayenneDataObjectModel model;
		private QueryFilter idFilter;

		/** Constructor */
		public EditPanel(String id)
		{
			super(id);
			CayenneQueryBuilder queryBuilder = new CayenneQueryBuilder()
			{
				public SelectQuery buildBaseQuery()
				{
					return new SelectQuery(PrayerItem.class);
				}
			};
			this.idFilter = new IdQueryFilter("ID");
			queryBuilder.addFilter(this.idFilter);

			this.model = new CayenneDataObjectModel(getDataContext(), queryBuilder,
					PrayerItem.class);
			setModel(this.model);

			addTextField("subject").setTitle("Subject").setMaxLength(100).setSize(20).setRequired(
					true);
			addTextArea("body").setTitle("Body").setColumns(50).setRows(5);

			List groups = getIpnSession().getUser().getGroupList();
			addDropDown("prayerGroup", groups).setTitle("Group").setRequired(true);

			List statuses = getDataContext().performQuery("AllPrayerItemStatuses", false);
			addDropDown("status", statuses).setTitle("Status").setRequired(true);
			// TODO: Set default status value

			List types = getDataContext().performQuery("AllPrayerItemTypes", false);
			addDropDown("type", types).setTitle("Type").setRequired(true);
			// TODO: Set default type value

			// TODO: define date type
			// addDateAttr("expirationDate").setRequired(false).setTitle("Expires
			// (YYYY-MM-DD)");
		}

		/**
		 * Called by the DynEditPanel#completePanel() method. Don't forget to have
		 * referring class call #completePanel()!
		 * @see net.sf.ipn.dynweb.DynEditPanel#setup()
		 */
		protected void setup()
		{
			CayenneQueryBuilder queryBuilder = new CayenneQueryBuilder()
			{
				public SelectQuery buildBaseQuery()
				{
					return new SelectQuery(PrayerItem.class);
				}
			};
			this.idFilter = new IdQueryFilter("ID");
			queryBuilder.addFilter(this.idFilter);

			this.model = new CayenneDataObjectModel(getDataContext(), queryBuilder,
					PrayerItem.class);
			setModel(this.model);

			addTextField("subject").setTitle("Subject").setMaxLength(100).setSize(20).setRequired(
					true);
			addTextArea("body").setTitle("Body").setColumns(50).setRows(5);

			List groups = getIpnSession().getUser().getGroupList();
			addDropDown("prayerGroup", groups).setTitle("Group").setRequired(true);

			List statuses = getDataContext().performQuery("AllPrayerItemStatuses", false);
			addDropDown("status", statuses).setTitle("Status").setRequired(true);
			// TODO: Set default status value

			List types = getDataContext().performQuery("AllPrayerItemTypes", false);
			addDropDown("type", types).setTitle("Type").setRequired(true);
			// TODO: Set default type value

			// TODO: define date type
			// addDateAttr("expirationDate").setRequired(false).setTitle("Expires
			// (YYYY-MM-DD)");
		}

		public void setPrayerItemId(Long id)
		{
			System.out.println(" setPrayerItemId: " + id);
			this.idFilter.setFilterValue(id);
			this.model.setNewDataObject(id == null);
		}

		protected void formSubmitted()
		{
			PrayerItem item = (PrayerItem)getModelObject();
			getDataContext().commitChanges();
		}

	} // EditPanel

	/**
	 * Provides an implementation of the QueryFilter interface for finding one thing.
	 */
	public static class IdQueryFilter implements QueryFilter
	{
		String filterProperty;
		Object filterValue;

		private IdQueryFilter()
		{
		}

		/**
		 * Constructs an instance with all it needs to get started.
		 * @param property - a property of the data objects. Example: "status.name"
		 */
		public IdQueryFilter(String property)
		{
			this.filterProperty = property;
			this.filterValue = null;
		}

		public Object getFilterValue()
		{
			return this.filterValue;
		}

		public void setFilterValue(Object o)
		{
			this.filterValue = o;
		}

		public Expression getQualifier()
		{
			if (null == this.filterValue)
				return null;
			return ExpressionFactory.matchDbExp(this.filterProperty, this.filterValue);
		}
	}
}
