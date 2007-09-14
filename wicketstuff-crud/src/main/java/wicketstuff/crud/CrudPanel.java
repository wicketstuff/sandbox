package wicketstuff.crud;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Stack;

import org.apache.wicket.Component;
import org.apache.wicket.IClusterable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.ISortableDataProvider;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

import wicketstuff.crud.view.DeletePanel;
import wicketstuff.crud.view.EditPanel;
import wicketstuff.crud.view.ListPanel;
import wicketstuff.crud.view.ViewPanel;


public abstract class CrudPanel extends Panel
{
	private static final String VIEW_ID = "view";

	private final ICrudListener listener = new CrudListenerAdapter();

	private List<Property> properties = new ArrayList<Property>(1);
	private IModel filterModel;
	private final ISortableDataProvider dataProvider;

	private ICreateBeanModelFactory createBeanModelFactory;

	private ViewsManager views = new ViewsManager();


	public CrudPanel(String id, ISortableDataProvider dp)
	{
		super(id);
		this.dataProvider = dp;
	}

	public void add(Property property)
	{
		properties.add(property);
	}

	public void add(Collection<Property> properties)
	{
		for (Property prop : properties)
		{
			add(prop);
		}
	}


	public void add(PropertySource source)
	{
		add(source.getProperties());
	}


	public void setCreateBeanModelFactory(ICreateBeanModelFactory createBeanModelFactory)
	{
		this.createBeanModelFactory = createBeanModelFactory;
	}

	@Override
	protected void onBeforeRender()
	{
		if (views.isEmpty())
		{
			// initialize with the list view
			ListPanel list = new ListPanel(VIEW_ID, properties, dataProvider, listener);
			if (views.isEmpty())
			{
				views.push(list);
			}


		}

		super.onBeforeRender();
	}

	protected abstract void onDelete(IModel model);

	protected abstract void onSave(IModel model);

	public void setFilterModel(IModel filterModel)
	{
		this.filterModel = filterModel;
	}

	private class CrudListenerAdapter implements ICrudListener
	{

		public void onCancel()
		{
			views.pop();
		}

		public void onCreate()
		{
			final IModel create = createBeanModelFactory.newModel();
			views.push(new EditPanel(VIEW_ID, create, properties, this));
		}

		public void onDelete(IModel selected)
		{
			views.push(new DeletePanel(VIEW_ID, selected, properties, this));
		}

		public void onDeleteConfirmed(IModel selected)
		{
			CrudPanel.this.onDelete(selected);
			// rewind back to listview
			views.popUntilFirst();
		}

		public void onEdit(IModel selected)
		{
			views.push(new EditPanel(VIEW_ID, selected, properties, this));
		}

		public void onSave(IModel selected)
		{
			CrudPanel.this.onSave(selected);
			views.pop();
		}

		public void onView(IModel selected)
		{
			views.push(new ViewPanel(VIEW_ID, selected, properties, this));
		}
	}

	private class ViewsManager implements IClusterable
	{
		private Stack<Component> views = new Stack<Component>();

		public void push(Component view)
		{
			addOrReplace(view);
			views.push(view);
		}

		public void pop()
		{
			views.pop();
			addOrReplace(views.peek());
		}

		public void popUntilFirst()
		{
			while (views.size() > 1)
			{
				views.pop();
			}
			addOrReplace(views.peek());
		}

		public boolean isEmpty()
		{
			return views.isEmpty();
		}

	}


}
