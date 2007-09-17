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
import org.apache.wicket.version.undo.Change;

import wicketstuff.crud.view.DeletePanel;
import wicketstuff.crud.view.EditPanel;
import wicketstuff.crud.view.ListPanel;
import wicketstuff.crud.view.ViewPanel;


public abstract class CrudPanel extends Panel
{
	/** id of view panels */
	private static final String VIEW_ID = "view";

	/** listener used by views to invoke events on the crud panel */
	private final ICrudListener listener = new CrudListenerAdapter();

	/** list of properties */
	private List<Property> properties = new ArrayList<Property>(1);

	/** model for the filter bean */
	private IModel filterModel;

	/** data provider */
	private final ISortableDataProvider dataProvider;

	/** factory used to create models for new beans */
	private ICreateBeanModelFactory createBeanModelFactory;

	/** internal view manager */
	private ViewsManager views = new ViewsManager();


	/**
	 * Constructor
	 * 
	 * @param id
	 *            component id
	 * @param dp
	 *            data provider
	 */
	public CrudPanel(String id, ISortableDataProvider dataProvider)
	{
		super(id);
		if (dataProvider == null)
		{
			throw new IllegalArgumentException("Argument `dataProvider` cannot be null");
		}
		this.dataProvider = dataProvider;
	}

	/**
	 * Adds a property
	 * 
	 * @param property
	 */
	public void add(Property property)
	{
		if (property == null)
		{
			throw new IllegalArgumentException("Argument `property` cannot be null");
		}
		properties.add(property);
	}

	/**
	 * Adds a collection of properties
	 * 
	 * @param properties
	 */
	public void add(Collection<Property> properties)
	{
		if (properties == null)
		{
			throw new IllegalArgumentException("Argument `properties` cannot be null");
		}
		for (Property prop : properties)
		{
			add(prop);
		}
	}


	/**
	 * Adds properties from a property source
	 * 
	 * @param source
	 */
	public void add(PropertySource source)
	{
		if (source == null)
		{
			throw new IllegalArgumentException("Argument `source` cannot be null");
		}
		add(source.getProperties());
	}


	/**
	 * Sets the factory used to create models for new beans. If this factory is
	 * set the "create new object" link will be enabled.
	 * 
	 * @param createBeanModelFactory
	 */
	public void setCreateBeanModelFactory(ICreateBeanModelFactory createBeanModelFactory)
	{
		// TODO state change
		this.createBeanModelFactory = createBeanModelFactory;
	}

	@Override
	protected void onBeforeRender()
	{
		if (views.isEmpty())
		{
			// initialize with the list view
			ListPanel list = new ListPanel(VIEW_ID, properties, dataProvider, listener);
			list.setFilterModel(filterModel);
			if (views.isEmpty())
			{
				views.push(list);
			}


		}

		super.onBeforeRender();
	}

	/**
	 * Listener for the delete event
	 * 
	 * @param model
	 */
	protected abstract void onDelete(IModel model);

	/**
	 * Listener for the save event
	 * 
	 * @param model
	 */
	protected abstract void onSave(IModel model);

	/**
	 * Sets model used to provide a bean for the filter toolbar. If this model
	 * is set to a non-null value the filter toolbar will be shown in the list
	 * view.
	 * 
	 * @param filterModel
	 */
	public void setFilterModel(IModel filterModel)
	{
		// TODO state change
		this.filterModel = filterModel;
	}

	/**
	 * Crud listener for the crud panel
	 * 
	 * @author igor.vaynberg
	 * 
	 */
	private class CrudListenerAdapter implements ICrudListener
	{

		/** {@inheritDoc} */
		public void onCancel()
		{
			views.pop();
		}

		/** {@inheritDoc} */
		public void onCreate()
		{
			final IModel create = createBeanModelFactory.newModel();
			views.push(new EditPanel(VIEW_ID, create, properties, this));
		}

		/** {@inheritDoc} */
		public void onDelete(IModel selected)
		{
			views.push(new DeletePanel(VIEW_ID, selected, properties, this));
		}

		/** {@inheritDoc} */
		public void onDeleteConfirmed(IModel selected)
		{
			CrudPanel.this.onDelete(selected);
			// rewind back to listview
			views.popUntilFirst();
		}

		/** {@inheritDoc} */
		public void onEdit(IModel selected)
		{
			views.push(new EditPanel(VIEW_ID, selected, properties, this));
		}

		/** {@inheritDoc} */
		public void onSave(IModel selected)
		{
			CrudPanel.this.onSave(selected);
			views.pop();
		}

		/** {@inheritDoc} */
		public void onView(IModel selected)
		{
			views.push(new ViewPanel(VIEW_ID, selected, properties, this));
		}
	}

	/**
	 * Maintains the view stack of the crud view.
	 * 
	 * @author igor.vaynberg
	 * 
	 */
	private class ViewsManager implements IClusterable
	{
		private Stack<Component> views = new Stack<Component>();

		/**
		 * Pushes a new view onto the view stack
		 * 
		 * @param view
		 */
		public void push(Component view)
		{
			addOrReplace(view);
			views.push(view);
			addStateChange(new ViewAddedChange());
		}

		/**
		 * Pops a view off the view stack
		 */
		public void pop()
		{
			Component removed = views.pop();
			addOrReplace(views.peek());
			addStateChange(new ViewRemovedChange(removed));
		}

		/**
		 * Pops all but the first view off the view stack
		 */
		public void popUntilFirst()
		{
			while (views.size() > 1)
			{
				views.pop();
			}
			addOrReplace(views.peek());
		}

		/**
		 * 
		 * @return true if the view stack is empty, false otherwise
		 */
		public boolean isEmpty()
		{
			return views.isEmpty();
		}

	}

	/**
	 * {@link Change} object used to track view-removed event
	 * 
	 * @author igor.vaynberg
	 * 
	 */
	private class ViewRemovedChange extends Change
	{
		private final Component removed;

		/**
		 * Constructor
		 * 
		 * @param removed
		 *            removed view
		 */
		public ViewRemovedChange(Component removed)
		{
			if (removed == null)
			{
				throw new IllegalArgumentException("Argument `removed` cannot be null");
			}
			this.removed = removed;
		}

		/** {@inheritDoc} */
		@Override
		public void undo()
		{
			views.push(removed);
		}
	}

	/**
	 * {@link Change} object used to track view-added event
	 * 
	 * @author igor.vaynberg
	 * 
	 */
	private class ViewAddedChange extends Change
	{

		/** {@inheritDoc} */
		@Override
		public void undo()
		{
			views.pop();
		}

	}


}
