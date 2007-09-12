package wicketstuff.crud;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.apache.wicket.extensions.markup.html.repeater.data.table.ISortableDataProvider;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.version.undo.Change;

import wicketstuff.crud.view.DeletePanel;
import wicketstuff.crud.view.EditPanel;
import wicketstuff.crud.view.ListPanel;
import wicketstuff.crud.view.ViewPanel;


public abstract class CrudPanel extends Panel
{
	private IModel selected;

	private boolean dirty = true;
	private List<Property> properties = new ArrayList<Property>(1);
	private IModel filterModel;
	private final ISortableDataProvider dataProvider;

	private ICreateBeanModelFactory createBeanModelFactory;

	private static enum Mode {
		LIST, EDIT, DELETE, VIEW
	}

	private Stack<Mode> mode = new Stack<Mode>();


	public CrudPanel(String id, ISortableDataProvider dp)
	{
		super(id);
		this.dataProvider = dp;
		mode.push(Mode.LIST);

	}

	public void add(Property property)
	{
		// TODO add state change
		properties.add(property);
		dirty = true;
	}

	public void add(PropertySource source)
	{
		for (Property prop : source.getProperties())
		{
			add(prop);
		}
	}


	public ICreateBeanModelFactory getCreateBeanModelFactory()
	{
		return createBeanModelFactory;
	}

	public void setCreateBeanModelFactory(ICreateBeanModelFactory createBeanModelFactory)
	{
		this.createBeanModelFactory = createBeanModelFactory;
	}

	@Override
	protected void onBeforeRender()
	{
		if (dirty)
		{
			switch (mode.peek())
			{
				case LIST :
					addOrReplace(new ListPanel("view", properties, dataProvider)
					{

						@Override
						protected void onEdit(IModel model)
						{
							pushEdit(model);
						}

						@Override
						protected void onDelete(IModel model)
						{
							pushDelete(model);
						}

						@Override
						protected void onView(IModel model)
						{
							pushView(model);
						}

						@Override
						protected void onCreate()
						{
							pushEdit(createBeanModelFactory.newModel());
						}

						@Override
						protected boolean allowCreateNewBean()
						{
							return createBeanModelFactory != null;
						}

					}.setFilterModel(filterModel));
					break;
				case EDIT :
					addOrReplace(new EditPanel("view", selected, properties)
					{

						@Override
						protected void onCancel()
						{
							back();
						}

						@Override
						protected void onSave(IModel model)
						{
							CrudPanel.this.onSave(model);
							back();
						}

					});
					break;
				case VIEW :
					addOrReplace(new ViewPanel("view", selected, properties)
					{

						@Override
						protected void onBack()
						{
							back();
						}

						@Override
						protected void onDelete(IModel model)
						{
							pushDelete(model);

						}

						@Override
						protected void onEdit(IModel model)
						{
							pushEdit(model);
						}

					});
					break;
				case DELETE :
					addOrReplace(new DeletePanel("view", selected, properties)
					{

						@Override
						protected void onCancel()
						{
							back();
						}

						@Override
						protected void onConfirm(IModel model)
						{
							onDelete(model);
							backToList();
						}

					});
					break;
			}

			dirty = false;
			addStateChange(new PropertiesDirtyChange());

		}
		super.onBeforeRender();
	}

	protected void backToList()
	{
		while (mode.peek() != Mode.LIST)
		{
			mode.pop();
		}

		dirty = true;
	}

	private void pushView(IModel model)
	{
		selected = model;
		mode.push(Mode.VIEW);
		dirty = true;
		// TODO state change

	}

	private void pushEdit(IModel model)
	{
		selected = model;
		mode.push(Mode.EDIT);
		dirty = true;
		// TODO state change

	}

	private void pushList()
	{
		mode.push(Mode.LIST);
		dirty = true;
		// TODO state change

	}

	private void pushDelete(IModel model)
	{
		selected = model;
		mode.push(Mode.DELETE);
		dirty = true;
		// TODO state change

	}


	private void back()
	{
		mode.pop();
		dirty = true;
	}

	private class PropertiesDirtyChange extends Change

	{

		@Override
		public void undo()
		{
			dirty = !dirty;
		}

	}

	protected abstract void onDelete(IModel model);

	protected abstract void onSave(IModel model);

	public IModel getFilterModel()
	{
		return filterModel;
	}

	public void setFilterModel(IModel filterModel)
	{
		this.filterModel = filterModel;
		dirty = true;
	}


}
