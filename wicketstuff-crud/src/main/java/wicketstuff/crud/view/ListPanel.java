package wicketstuff.crud.view;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.HeaderlessColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.HeadersToolbar;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.ISortableDataProvider;
import org.apache.wicket.extensions.markup.html.repeater.data.table.NavigationToolbar;
import org.apache.wicket.extensions.markup.html.repeater.data.table.NoRecordsToolbar;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;

import wicketstuff.crud.ICrudListener;
import wicketstuff.crud.Property;
import wicketstuff.crud.filter.ApplyFilter;
import wicketstuff.crud.filter.FilterToolbar;
import wicketstuff.crud.filter.IFilterableColumn;

/**
 * List screen
 * 
 * @author igor.vaynberg
 * 
 */
public class ListPanel extends Panel
{
	private IModel filterModel;
	private final ICrudListener crudListener;

	/**
	 * Constructor
	 * 
	 * @param id
	 * @param properties
	 * @param dp
	 * @param crudListener
	 */
	public ListPanel(String id, List<Property> properties, ISortableDataProvider dp,
			ICrudListener crudListener)
	{
		super(id);

		this.crudListener = crudListener;

		// create new object link
		add(new Link("create")
		{

			@Override
			public void onClick()
			{
				ListPanel.this.crudListener.onCreate();
			}

			@Override
			public boolean isVisible()
			{
				return allowCreateNewBean();
			}

		});


		// form for filter form components
		Form form = new Form("form");
		addOrReplace(form);

		// data table
		List<Property> props = properties;
		List<IColumn> cols = new ArrayList<IColumn>(props.size());
		for (Property prop : props)
		{
			if (prop.isVisibleInList())
			{
				cols.add(new PropertyColumnAdapter(prop));
			}
		}
		cols.add(new ActionsColumn());

		DataTable table = new DataTable("list", cols.toArray(new IColumn[cols.size()]), dp, 15);
		table.addTopToolbar(new NavigationToolbar(table));
		table.addTopToolbar(new HeadersToolbar(table, dp));
		table.addBottomToolbar(new NoRecordsToolbar(table));

		// filter toolbar
		table.addTopToolbar(new FilterToolbar(table, new PropertyModel(this, "filterModel"))
		{
			@Override
			public boolean isVisible()
			{
				return filterModel != null;
			}
		});


		form.add(table);
	}


	/**
	 * Actions column
	 * 
	 * @author igor.vaynberg
	 * 
	 */
	private class ActionsColumn extends HeaderlessColumn implements IFilterableColumn
	{
		/** {@inheritDoc} */
		public void populateItem(Item cellItem, String componentId, IModel rowModel)
		{
			Fragment actions = new Fragment(componentId, "actions-fragment", ListPanel.this);
			cellItem.add(actions);
			actions.add(new Link("edit", rowModel)
			{

				@Override
				public void onClick()
				{
					crudListener.onEdit(getModel());
				}

			});
			actions.add(new Link("delete", rowModel)
			{

				@Override
				public void onClick()
				{
					crudListener.onDelete(getModel());
				}

			});
			actions.add(new Link("view", rowModel)
			{

				@Override
				public void onClick()
				{
					crudListener.onView(getModel());
				}

			});


		}

		/** {@inheritDoc} */
		public Component getFilter(String id, IModel model)
		{
			return new ApplyFilter(id);
		}

	}

	/**
	 * Overridable flag for whether or not create-new-object link should be
	 * shown or not
	 * 
	 * @return
	 */
	protected boolean allowCreateNewBean()
	{
		return true;
	}

	/**
	 * Sets model used to store filter properties. If this model is set to a
	 * non-null value the filter toolbar will be shown.
	 * 
	 * @param filter
	 * @return
	 */
	public ListPanel setFilterModel(IModel filter)
	{
		this.filterModel = filter;
		return this;
	}
}
