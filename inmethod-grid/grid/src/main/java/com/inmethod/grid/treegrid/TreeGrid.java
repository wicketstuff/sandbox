package com.inmethod.grid.treegrid;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.tree.AbstractTree;
import org.apache.wicket.markup.html.tree.ITreeState;
import org.apache.wicket.markup.html.tree.ITreeStateListener;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import com.inmethod.grid.IGridColumn;
import com.inmethod.grid.common.AbstractGrid;

/**
 * Advanced grid with a tree. Supports resizable and reorderable columns.
 * 
 * @author Matej Knopp
 */
public class TreeGrid extends AbstractGrid {

	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new {@link TreeGrid} instance.
	 * 
	 * @param id
	 *            component id
	 * @param model
	 *            model used to access the {@link TreeModel}
	 * @param columns
	 *            list of {@link IGridColumn}s.
	 */
	public TreeGrid(String id, IModel model, List<IGridColumn> columns) {
		super(id, model, columns);

		WebMarkupContainer bodyContainer = (WebMarkupContainer) get("form:bodyContainer");
		bodyContainer.add(body = new TreeGridBody("body", model) {

			private static final long serialVersionUID = 1L;

			@Override
			protected Collection<IGridColumn> getActiveColumns() {
				return TreeGrid.this.getActiveColumns();
			}

			@Override
			protected void rowPopulated(WebMarkupContainer item) {
				TreeGrid.this.onRowPopulated(item);
			}
		});

		getTreeState().addTreeStateListener(new TreeStateListener());
	}

	private class TreeStateListener implements ITreeStateListener, Serializable {

		private static final long serialVersionUID = 1L;

		/**
		 * {@inheritDoc}
		 */
		public void allNodesCollapsed() {
		}

		/**
		 * {@inheritDoc}
		 */
		public void allNodesExpanded() {
		}

		/**
		 * {@inheritDoc}
		 */
		public void nodeCollapsed(Object node) {
		}

		/**
		 * {@inheritDoc}
		 */
		public void nodeExpanded(Object node) {
		}

		/**
		 * {@inheritDoc}
		 */
		public void nodeSelected(Object node) {
			onItemSelectionChanged(new Model((Serializable) node), true);
		}

		/**
		 * {@inheritDoc}
		 */
		public void nodeUnselected(Object node) {
			onItemSelectionChanged(new Model((Serializable) node), false);
		}
	};

	/**
	 * Creates a new {@link TreeGrid} instance.
	 * 
	 * @param id
	 *            component id
	 * @param model
	 *            tree model
	 * @param columns
	 *            list of {@link IGridColumn}s.
	 */
	public TreeGrid(String id, TreeModel model, List<IGridColumn> columns) {
		this(id, new Model((Serializable) model), columns);
	}

	private TreeGridBody body;

	/**
	 * Returns the inner tree of the {@link TreeGrid}.
	 * 
	 * @return inner tree
	 */
	public AbstractTree getTree() {
		return body;
	}

	/**
	 * Returns the tree state
	 * 
	 * @return tree state
	 */
	public ITreeState getTreeState() {
		return getTree().getTreeState();
	}

	/**
	 * During Ajax request updates the changed parts of tree.
	 */
	public final void update() {
		getTree().updateTree(AjaxRequestTarget.get());
	};

	/**
	 * Callback function called after user clicked on an junction link. The node
	 * has already been expanded/collapsed (depending on previous status).
	 * 
	 * @param target
	 *            Request target - may be null on non-ajax call
	 * 
	 * @param node
	 *            Node for which this callback is relevant
	 */
	protected void onJunctionLinkClicked(AjaxRequestTarget target, Object node) {
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Collection<IModel> getSelectedItems() {
		Collection<Object> nodes = getTreeState().getSelectedNodes();
		Collection<IModel> result = new ArrayList<IModel>(nodes.size());
		for (Object node : nodes) {
			result.add(new Model((Serializable) node));
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isAllowSelectMultiple() {
		return getTreeState().isAllowSelectMultiple();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setAllowSelectMultiple(boolean value) {
		getTreeState().setAllowSelectMultiple(value);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isItemSelected(IModel itemModel) {
		return getTreeState().isNodeSelected(itemModel.getObject());
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void resetSelectedItems() {
		Collection<Object> nodes = getTreeState().getSelectedNodes();
		for (Object node : nodes) {
			getTreeState().selectNode(node, false);
		}
		getTree().invalidateAll();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void selectAllVisibleItems() {
		WebMarkupContainer body = (WebMarkupContainer) get("form:bodyContainer:body:i");
		if (body != null) {
			for (Iterator<?> i = body.iterator(); i.hasNext();) {
				Component component = (Component) i.next();
				selectItem(component.getModel(), true);
			}
		}
		getTree().invalidateAll();
	}

	@Override
	protected WebMarkupContainer findRowComponent(IModel rowModel) {
		if (rowModel == null) {
			throw new IllegalArgumentException("rowModel may not be null");
		}
		WebMarkupContainer body = (WebMarkupContainer) get("form:bodyContainer:body:i");
		if (body != null) {
			for (Iterator<?> i = body.iterator(); i.hasNext();) {
				Component component = (Component) i.next();
				IModel model = component.getModel();
				if (rowModel.equals(model)) {
					return (WebMarkupContainer) component;
				}
			}
		}
		return null;
	}

	@Override
	public void markItemDirty(IModel model) {
		Object node = model.getObject();
		getTree().markNodeDirty(node);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void selectItem(IModel itemModel, boolean selected) {
		getTreeState().selectNode(itemModel.getObject(), selected);
	}

	@Override
	public WebMarkupContainer findParentRow(Component child) {
		if (child instanceof AbstractTreeGridRow == false) {
			child = (Component) child.findParent(AbstractTreeGridRow.class);
		}
		return (WebMarkupContainer) (child != null ? child.getParent() : null);
	}
}
