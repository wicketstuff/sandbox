package wicket.contrib.data.model.bind;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.Component;
import wicket.contrib.data.model.OrderedPageableList;
import wicket.contrib.data.model.bind.IObjectDataSource.EntityField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.convert.ConverterLocator;
import org.apache.wicket.util.string.Strings;

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
public class GridPanel extends Panel
{
	private class Columns extends ListView
	{
		IModel model;

		public Columns(IModel model)
		{
			super("columns", columns);
			this.model = model;
			setOptimizeItemRemoval(true);
		}

		protected void populateItem(ListItem item)
		{
			IColumn col = (IColumn) item.getModelObject();
			Component component = col.getComponent("column", model);

			component.setRenderBodyOnly(true);
			item.add(component);
		}
	}

	private class LocalGridView extends GridView
	{

		public LocalGridView()
		{
			super("form", dataSource, perPage);
			add(new Orderings(getListView()));
		}

		protected void populateItem(ListItem item)
		{
			item.add(new Columns(item.getModel()));
		}
	}

	private class Orderings extends ListView
	{
		ListView mainList;

		public Orderings(ListView mainList)
		{
			super("orderings", columns);
			this.mainList = mainList;
			setOptimizeItemRemoval(true);
		}

		protected void populateItem(ListItem item)
		{
			IColumn col = (IColumn) item.getModelObject();
			String field = "e." + col.getOrderByPath();

			if (col.allowOrderBy())
			{
				item.add(new OrderByPanel("orderBy", field, col.getDisplayName(),
						mainList));
			}
			else
			{
				item.add(new LabelPanel("orderBy", col.getDisplayName()));
			}
		}
	}

	private List columns;

	private IListDataSource dataSource;

	private int perPage;

	private LocalGridView gView;

	/**
	 * @param id
	 *            the id of this panel
	 * @param dataSource
	 *            the dataSource to use to modify and select data
	 */
	public GridPanel(String id, IListDataSource dataSource)
	{
		this(id, dataSource, 10, null);
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
	public GridPanel(String id, IListDataSource dataSource, int perPage, List columns)
	{
		super(id);
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

		add(gView = new LocalGridView());
		add(new PageNav("pageNav", gView.getListView()));
	}

	/**
	 * @see GridView#setList(List)
	 */
	public void setList(OrderedPageableList list)
	{
		gView.setList(list);
	}

	private String makeColHeading(String s)
	{
		return Strings.capitalize(s);
	}

	private List makeColumns(List fields)
	{
		List cols = new ArrayList(fields.size());

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
				if (getConverter(clazz) instanceof ConverterLocator
						&& ((ConverterLocator) getConverter(clazz)).get(clazz) != null)
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
