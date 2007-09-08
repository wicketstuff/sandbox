package wicketstuff.crud;

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

import wicketstuff.crud.filter.ApplyAndClearFilter;
import wicketstuff.crud.filter.FilterToolbar;
import wicketstuff.crud.filter.IFilterableColumn;

public abstract class ListPanel extends Panel
{

	public ListPanel(String id, List<Property> properties, ISortableDataProvider dp, IModel filter)
	{
		super(id);

		add(new Link("create")
		{

			@Override
			public void onClick()
			{
				onCreate();
			}

			@Override
			public boolean isVisible()
			{
				return allowCreateNewBean();
			}

		});


		Form form = new Form("form");
		addOrReplace(form);

		List<Property> props = properties;
		List<IColumn> cols = new ArrayList<IColumn>(props.size());
		for (Property prop : props)
		{
			cols.add(new PropertyColumn(prop));
		}
		cols.add(new ActionsColumn());

		DataTable table = new DataTable("list", cols.toArray(new IColumn[cols.size()]), dp, 15);
		table.addTopToolbar(new NavigationToolbar(table));
		table.addTopToolbar(new FilterToolbar(table, filter));
		table.addTopToolbar(new HeadersToolbar(table, dp));
		table.addBottomToolbar(new NoRecordsToolbar(table));

		form.add(table);
	}


	private class ActionsColumn extends HeaderlessColumn implements IFilterableColumn
	{
		public void populateItem(Item cellItem, String componentId, IModel rowModel)
		{
			Fragment actions = new Fragment(componentId, "actions-fragment", ListPanel.this);
			cellItem.add(actions);
			actions.add(new Link("edit", rowModel)
			{

				@Override
				public void onClick()
				{
					onEdit(getModel());
				}

			});
			actions.add(new Link("delete", rowModel)
			{

				@Override
				public void onClick()
				{
					onDelete(getModel());
				}

			});
			actions.add(new Link("view", rowModel)
			{

				@Override
				public void onClick()
				{
					onView(getModel());
				}

			});


		}

		public Component getFilter(String id, IModel model)
		{
			return new ApplyAndClearFilter(id);
		}

	}

	protected abstract void onEdit(IModel model);

	protected abstract void onDelete(IModel model);

	protected abstract void onView(IModel model);

	protected abstract void onCreate();

	protected boolean allowCreateNewBean()
	{
		return true;
	}
}
