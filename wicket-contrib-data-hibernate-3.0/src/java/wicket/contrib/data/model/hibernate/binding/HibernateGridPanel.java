package wicket.contrib.data.model.hibernate.binding;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.metadata.ClassMetadata;
import org.hibernate.type.Type;

import wicket.Component;
import wicket.contrib.data.model.hibernate.sandbox.HibernateQueryList;
import wicket.contrib.data.model.hibernate.sandbox.IHibernateDao;
import wicket.markup.html.list.ListItem;
import wicket.markup.html.list.ListView;
import wicket.markup.html.panel.Panel;
import wicket.model.IModel;
import wicket.model.PropertyModel;
import wicket.util.string.Strings;

public class HibernateGridPanel extends Panel {
	private List columns;
	private IHibernateDao dao;
	private String entityName;
	
	public HibernateGridPanel(String id, String entityName, IHibernateDao dao) {
		super(id);
		this.columns = makeColumns(dao.getSessionFactory().getClassMetadata(entityName));
		this.dao = dao;
		this.entityName = entityName;
		init();
	}
	
	public HibernateGridPanel(String id, String entityName, IHibernateDao dao,
			List columns) {
		super(id);
		this.columns = columns;
		this.dao = dao;
		this.entityName = entityName;
		init();
	}
	
	private void init() {
		IModel items = new HibernateQueryList("FROM " + entityName + " e", dao)
			.getDetachableModel();
	
		add(new GridView(items));
	}
	
	public class GridView extends HibernateGridView {
		
		public GridView(IModel items) {
			super("form", items, null, 10, dao);
			add(new Orderings(getListView()));
		}

		protected void populateItem(ListItem item) {
			item.add(new InlineDeleteLink("delete"));
			item.add(new InlineEditLink("edit"));
			item.add(new InlineSubmitButton("save"));
			item.add(new Columns(item.getModel()));
		}
	}
	
	public class Columns extends ListView {
		IModel model;
		
		public Columns(IModel model) {
			super("columns", columns);
			this.model = model;
		}

		protected void populateItem(ListItem item) {
			IColumn col = (IColumn) item.getModelObject();
			Component component = col.getComponent("column",
				new PropertyModel(model, col.getModelPath()));
			
			component.setRenderBodyOnly(true);
			item.add(component);
		}
	}
	
	public class Orderings extends ListView {
		ListView mainList;
		
		public Orderings(ListView mainList) {
			super("orderings",  columns);
			this.mainList = mainList;
			setOptimizeItemRemoval(true);
		}

		protected void populateItem(ListItem item) {
			IColumn col = (IColumn) item.getModelObject();
			String field = "e." + col.getOrderByPath();
			
			if (col.allowOrderBy()) {
				item.add(new OrderByPanel("orderBy", field,
					col.getDisplayName(), mainList));
			} else {
				item.add(new LabelPanel("orderBy", col.getDisplayName()));
			}
		}
	}
	
	private String makeColHeading(String s) {
		return Strings.capitalize(s);
	}
	
	private List makeColumns(ClassMetadata meta) {
		String[] propNames = meta.getPropertyNames();
		List columns = new ArrayList(propNames.length);
		
		for (int i = 0; i < propNames.length; i++) {
			String prop = propNames[i];
			Type type = meta.getPropertyType(prop);
			
			if (type.getReturnedClass().isAssignableFrom(String.class)) {
				columns.add(new TextFieldColumn(makeColHeading(prop), prop));
			}
			else if (type.getReturnedClass().isAssignableFrom(Boolean.class)) {
				columns.add(new CheckBoxColumn(makeColHeading(prop), prop));
			} else {
				columns.add(new LabelColumn(makeColHeading(prop), prop));
			}
		}
		
		return columns;
	}
}
