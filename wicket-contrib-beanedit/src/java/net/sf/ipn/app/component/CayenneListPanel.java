/*
 * Created on May 5, 2005
 */
package net.sf.ipn.app.component;

import java.util.ArrayList;
import java.util.List;

import org.objectstyle.cayenne.query.SelectQuery;

import wicket.markup.html.form.DropDownChoice;
import wicket.markup.html.form.TextField;
import wicket.markup.html.link.Link;
import wicket.markup.html.panel.Panel;
import wicket.model.PropertyModel;

/**
 * @author Jonathan Carlson Provides a framework superclass for creating a sortable (click
 *         on the column header) and/or filterable table of instances.
 */
public abstract class CayenneListPanel extends Panel
{

	/**
	 * 
	 */
	public CayenneListPanel(String id)
	{
		super(id);

		SelectQuery query;

		// Set up the Cayenne Query
		/*
		 * Example: query = new SelectQuery(PrayerItem.class); query.setPageSize(10);
		 * query.setCachePolicy(GenericSelectQuery.LOCAL_CACHE); query.setQualifier(
		 * ExpressionFactory.matchExp("prayerGroup.owningUser",
		 * getIpnSession().getUser())); SpecializableCayenneQueryModel model = new
		 * SpecializableCayenneQueryModel(getDataContext(), query); PageableListView
		 * listView = new PageableListView("dataRow", model, 10) { protected void
		 * populateItem(ListItem listItem) { final PrayerItem prayerItem = (PrayerItem)
		 * listItem.getModelObject(); listItem.add(new Label("group", new
		 * PropertyModel(prayerItem, "prayerGroup.name"))); listItem.add(new Label("type",
		 * new PropertyModel(prayerItem, "type.description"))); listItem.add(new
		 * Label("status", new PropertyModel(prayerItem, "status.description")));
		 * listItem.add(new Label("subject", new PropertyModel(prayerItem, "subject")));
		 * listItem.add(new AttributeModifier("class", new Model(listItem.getIndex() % 2 ==
		 * 0 ? "evenRow" : "oddRow"))); } }; add(listView); add(new
		 * PageableListViewNavigator("pageableNav", listView)); List groups =
		 * getIpnSession().getUser().getGroupList(); add(newFilterDropDown("groupFilter",
		 * "prayerGroup", model, groups)); List types =
		 * getDataContext().performQuery("AllPrayerItemTypes", false);
		 * add(newFilterDropDown("typeFilter", "type", model, types)); List statuses =
		 * getDataContext().performQuery("AllPrayerItemStatuses", false);
		 * add(newFilterDropDown("statusFilter", "status", model, statuses));
		 * add(newFilterTextField("subjectFilter", "subject", model));
		 * add(newSortLink("groupHeader", "prayerGroup.name", model));
		 * add(newSortLink("statusHeader", "status.description", model));
		 * add(newSortLink("typeHeader", "type.description", model));
		 * add(newSortLink("subjectHeader", "subject", model));
		 */
	}

	protected TextField newFilterTextField(String id, String property,
			CayenneQueryBuilder queryBuilder)
	{
		final QueryFilter filter = queryBuilder.newLikeQueryFilter(property);
		queryBuilder.addFilter(filter);
		TextField textField = new OnChangeTextField(id)
		{
			protected void onSelectionChanged(final Object newObj)
			{
				filter.setFilterValue(newObj);
			}
		};
		textField.setModel(new PropertyModel(filter, "filterValue"));
		return textField;
	}

	/**
	 * Creates a new DropDownChoice that notifies the model when something changes.
	 * @param id
	 * @param property
	 * @param queryBuilder
	 * @param choices
	 * @return
	 */
	protected DropDownChoice newFilterDropDown(String id, String property,
			CayenneQueryBuilder queryBuilder, List choices)
	{
		final QueryFilter filter = queryBuilder.newDiscreteQueryFilter(property);
		queryBuilder.addFilter(filter);

		List newChoices = new ArrayList();
		newChoices.add("Any");
		newChoices.addAll(choices);
		DropDownChoice dropDown = new DropDownChoice(id, newChoices)
		{
			public boolean isNullValid()
			{
				return true;
			}

			protected boolean wantOnSelectionChangedNotifications()
			{
				return true;
			}

			public void onSelectionChanged(Object newObj)
			{
				filter.setFilterValue(newObj);
			}
		};
		dropDown.setModel(new PropertyModel(filter, "filterValue"));
		return dropDown;
	}

	protected Link newSortLink(String id, String property, CayenneQueryBuilder queryBuilder)
	{
		// sortable type header
		final QueryOrdering ordering = queryBuilder.newQueryOrdering(property);
		queryBuilder.addOrdering(ordering);
		return new Link(id)
		{
			public void onClick()
			{
				ordering.onClick();
			}
		};
	}

}
