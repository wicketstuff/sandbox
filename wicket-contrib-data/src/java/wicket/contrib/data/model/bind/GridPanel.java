package wicket.contrib.data.model.bind;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import wicket.Component;
import wicket.MarkupContainer;
import wicket.contrib.data.model.OrderedPageableList;
import wicket.contrib.data.model.bind.IObjectDataSource.EntityField;
import wicket.markup.html.list.ListItem;
import wicket.markup.html.list.ListView;
import wicket.markup.html.panel.Panel;
import wicket.model.IModel;
import wicket.util.string.Strings;

/**
 * A panel that browses and edits a list of objects. Each row maintains an
 * "edit" status that can be toggled by the user. By default, all string fields
 * are turned into text boxes on edit, booleans are turned into check boxes,
 * associations are turned into selects, everything else is a turned into label,
 * and all columns are sortable. The column names are also taken directly from
 * the property names of the class and show up in the order they are defined in
 * the class. This can all be changed, however, by supplying your own list of
 * {@link IColumn}s.
 * 
 * @author Phil Kulak
 */
public class GridPanel<T> extends Panel<T>
{
	private static final long serialVersionUID = 1L;

	private List<IColumn> columns;

	private IListDataSource<T> dataSource;

	private int perPage;

	private LocalGridView gView;

	/**
	 * @param id
	 *            the id of this panel
	 * @param dataSource
	 *            the dataSource to use to modify and select data
	 */
	public GridPanel(MarkupContainer parent, String id, IListDataSource<T> dataSource)
	{
		this(parent, id, dataSource, 10, null);
	}

	/**
	 * @param id
	 *            the id of this panel
	 * @param dataSource
	 *            the dataSource to use to modify and select data
	 * @param perPage
	 *            the number or rows to display per page
	 * @param columns
	 *            a custom list of columns
	 * @param feedback
	 *            the feedback collector to use for validation errors
	 */
	public GridPanel(MarkupContainer parent, String id, IListDataSource<T> dataSource,
			int perPage, List<IColumn> columns)
	{
		super(parent, id);
		this.dataSource = dataSource;
		this.perPage = perPage;

		if (columns == null)
		{
			this.columns = makeColumns(dataSource.getFields());
		}
		else
		{
			this.columns = columns;
		}

		gView = new LocalGridView(this);
		new PageNav(this, "pageNav", gView.getListView());
	}

	/**
	 * @see GridView#setList(List)
	 */
	public void setList(OrderedPageableList list)
	{
		gView.setList(list);
	}

	private class LocalGridView extends GridView<T>
	{
		private static final long serialVersionUID = 1L;

		public LocalGridView(MarkupContainer parent)
		{
			super(parent, "form", dataSource, perPage);
			new Orderings(this, getListView());
		}

		@Override
		protected void populateItem(ListItem item)
		{
			new Columns(item, item.getModel());
		}
	}

	private class Columns extends ListView<IColumn>
	{
		private static final long serialVersionUID = 1L;
		IModel model;

		public Columns(MarkupContainer parent, IModel model)
		{
			super(parent, "columns", columns);
			this.model = model;
			setReuseItems(true);
		}

		@Override
		protected void populateItem(ListItem<IColumn> item)
		{
			IColumn col = item.getModelObject();
			Component component = col.getComponent(item, "column", model);
			component.setRenderBodyOnly(true);
		}
	}

	private class Orderings extends ListView<IColumn>
	{
		private static final long serialVersionUID = 1L;
		ListView mainList;

		public Orderings(MarkupContainer parent, ListView mainList)
		{
			super(parent, "orderings", columns);
			this.mainList = mainList;
			setReuseItems(true);
		}

		@Override
		protected void populateItem(ListItem<IColumn> item)
		{
			IColumn col = item.getModelObject();
			String field = "e." + col.getOrderByPath();

			if (col.allowOrderBy())
			{
				new OrderByPanel(item, "orderBy", field, col.getDisplayName(), mainList);
			}
			else
			{
				new LabelPanel(item, "orderBy", col.getDisplayName());
			}
		}
	}

	private String makeColHeading(String s)
	{
		return Strings.capitalize(s);
	}

	@SuppressWarnings("unchecked")
	private List<IColumn> makeColumns(List fields)
	{
		List<IColumn> cols = new ArrayList<IColumn>(fields.size());

		// Add the edit and delete links.
		cols.add(new MultiColumn().add(new EditColumn()).add(new DeleteColumn()));

		for (Iterator i = fields.iterator(); i.hasNext();)
		{
			EntityField field = (EntityField) i.next();
			Class clazz = field.getClazz();
			String name = field.getName();
			int type = field.getType();

			if (type == EntityField.FIELD)
			{
				// Are we able to convert the given type?
				if (getConverter(clazz) != null)
				{
					cols.add(new TextFieldColumn(makeColHeading(name), name)
							.setType(clazz));
				}
				else if (clazz.isAssignableFrom(Boolean.class))
				{
					cols.add(new CheckBoxColumn(makeColHeading(name), name));
				}
				else
				{
					cols.add(new LabelColumn(makeColHeading(name), name));
				}
			}
			else if (type == EntityField.ENTITY)
			{
				List allEntities = dataSource.findAll(clazz);
				if (allEntities instanceof OrderedPageableList)
				{
					((OrderedPageableList) allEntities).setUsePaging(false);
				}
				cols
						.add(new DropDownChoiceColumn(makeColHeading(name), name,
								allEntities));
			}
		}

		return cols;
	}
}
