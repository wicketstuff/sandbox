package wicket.contrib.data.model.bind;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import wicket.Component;
import wicket.IFeedback;
import wicket.contrib.data.model.bind.IDataSource.EntityField;
import wicket.markup.html.list.ListItem;
import wicket.markup.html.list.ListView;
import wicket.markup.html.panel.Panel;
import wicket.model.IModel;
import wicket.model.PropertyModel;
import wicket.util.string.Strings;

/**
 * A panel that browses and edits a list of objects. Each row
 * maintains an "edit" status that can be toggled by the user. By default, all
 * string fields are turned into text boxes on edit, booleans are turned into
 * check boxes, associations are turned into selects, everything else is 
 * a turned into label, and all columns are sortable. The column names are also 
 * taken directly from the property names of the class and show up in the order 
 * they are defined in the class. This can all be changed, however, by 
 * supplying your own list of {@link IColumn}s.
 * 
 * @author Phil Kulak
 */
public class GridPanel extends Panel
{
	private List columns;

	private IDataSource dataSource;
	
	private int perPage;

	/**
	 * @param id
	 *            the id of this panel
	 * @param dataSource
	 *            the dataSource to use to modify and select data
	 */
	public GridPanel(String id, IDataSource dataSource)
	{
		this(id, dataSource, 10, null, null);
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
	public GridPanel(String id, IDataSource dataSource, int perPage, 
			List columns, IFeedback feedback)
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
		
		LocalGridView gView = new LocalGridView(feedback);
		
		add(gView);
		add(new PageNav("pageNav", gView.getListView()));
	}

	private class LocalGridView extends GridView
	{

		public LocalGridView(IFeedback feedback)
		{
			super("form", dataSource, feedback, perPage);
			add(new Orderings(getListView()));
		}

		protected void populateItem(ListItem item)
		{
			item.add(new InlineDeleteLink("delete"));
			item.add(new InlineEditLink("edit"));
			item.add(new InlineSubmitButton("save"));
			item.add(new Columns(item.getModel()));
		}
	}

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
			Component component = col.getComponent("column",
				new PropertyModel(model, col.getModelPath()));

			component.setRenderBodyOnly(true);
			item.add(component);
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

	private String makeColHeading(String s)
	{
		return Strings.capitalize(s);
	}

	private List makeColumns(List fields)
	{
		List cols = new ArrayList(fields.size());
		for (Iterator i = fields.iterator(); i.hasNext();)
		{
			EntityField field = (EntityField) i.next();
			Class clazz = field.getClazz();
			String name = field.getName();
			int type = field.getType();
			
			if (type == EntityField.FIELD)
			{
				if (clazz.isAssignableFrom(String.class))
				{
					cols.add(new TextFieldColumn(makeColHeading(name), name));
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
				Object firstEntity = allEntities.get(0);
				
				if (firstEntity instanceof Comparable)
				{
					Collections.sort(allEntities);
				}
				
				cols.add(new DropDownChoiceColumn(makeColHeading(name), name, allEntities));
			}
		}

		return cols;
	}
}
