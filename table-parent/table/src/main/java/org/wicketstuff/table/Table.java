/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wicketstuff.table;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ListSelectionModel;
import javax.swing.RowSorter;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.RowSorterEvent;
import javax.swing.event.RowSorterListener;
import javax.swing.table.TableModel;

import org.apache.wicket.ResourceReference;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.navigation.paging.AjaxPagingNavigator;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;
import org.wicketstuff.table.cell.BooleanRender;
import org.wicketstuff.table.cell.CellEditor;
import org.wicketstuff.table.cell.CellRender;
import org.wicketstuff.table.cell.ObjectRender;
import org.wicketstuff.table.cell.TableCellModel;
import org.wicketstuff.table.column.TableColumn;
import org.wicketstuff.table.column.ColumnModel;
import org.wicketstuff.table.sorter.SerializableTableRowSorter;

/**
 * Table component to present an swing TableModel.
 * 
 * @author Pedro Henrique Oliveira dos Santos
 * 
 */
public class Table extends Panel
{

	private static final long serialVersionUID = 1L;
	public static final String CELL_ID = "cell";
	public static final ResourceReference TABLE_CSS = new ResourceReference(Table.class,
			"res/table.css");
	private TableListView rowsListView;
	private boolean autoCreateRowSorter;
	private ColumnModel columnsModelAdapter;

	/**
	 * @param id
	 * @param swingTableModel
	 *            the tableModel need to be serializable, to exist along the
	 *            session.
	 */
	public Table(String id, TableModel swingTableModel)
	{
		super(id);
		setDefaultModel(new TableModelAdapter(swingTableModel));
		columnsModelAdapter = new ColumnModel((IModel<TableModel>)getDefaultModel());
		setOutputMarkupId(true);
		add(new ListView("headers", columnsModelAdapter)
		{
			@Override
			protected void populateItem(final ListItem item)
			{
				final int columnIndex = columnsModelAdapter.convertIndexToModel(item.getIndex());
				String header = getTableModel().getColumnName(columnIndex);
				item.add(new Label("header", new ResourceModel(header, header)));
				item.add(new OrderingImage("arrow", columnIndex, Table.this));
				item.add(new AjaxEventBehavior("onclick")
				{
					@Override
					protected void onEvent(AjaxRequestTarget target)
					{
						if (getRowSorter() != null)
						{
							getRowSorter().toggleSortOrder(columnIndex);
							target.addComponent(Table.this);
						}
					}
				});
			}
		});
		add(rowsListView = new TableListView("rows", new ListModelAdapter(getTableModel())));
	}

	public AjaxPagingNavigator getRowsAjaxPagingNavigator(String id)
	{
		return new AjaxPagingNavigator(id, rowsListView);
	}

	/**
	 * Just an helper factory method. You can create your custom navigator by
	 * using the IPageable columnsModelAdapter getting it from
	 * {@link Table#getColumnModel()} method.
	 * 
	 * @param id
	 *            wicket id
	 * @return AjaxPagingNavigator
	 */
	public AjaxPagingNavigator getColumnsAjaxPagingNavigator(String id)
	{
		return new AjaxPagingNavigator(id, columnsModelAdapter)
		{
			@Override
			protected void onAjaxEvent(AjaxRequestTarget target)
			{
				target.addComponent(Table.this);
				target.addComponent(this);
			}
		};
	}

	public ColumnModel getColumnModel()
	{
		return columnsModelAdapter;
	}

	/**
	 * Number of rows to be presented per page on table.
	 * 
	 * @param rowsPerPage
	 */
	public void setRowsPerPage(int rowsPerPage)
	{
		rowsListView.setRowsPerPage(rowsPerPage);
	}

	/**
	 * Number of columns to be presented per page on table.
	 * 
	 * @param rowsPerPage
	 */
	public void setColumnsPerPage(int columnsPerPage)
	{
		columnsModelAdapter.setColumnsPerPage(columnsPerPage);
	}

	/**
	 * @see {@link javax.swing.JTable#setSelectionMode(int)}
	 */
	public void setSelectionMode(int selectionMode)
	{
		rowsListView.getListSelectionModel().setSelectionMode(selectionMode);
	}

	/**
	 * @see {@link javax.swing.JTable#getSelectionModel()}
	 */
	public ListSelectionModel getListSelectionModel()
	{
		return rowsListView.getListSelectionModel();
	}

	/**
	 * Add a listener to the list that's notified each time a change to the
	 * selection occurs.
	 */
	public void addListSelectionListener(ListSelectionListener x)
	{
		rowsListView.getListSelectionModel().addListSelectionListener(x);
	}

	public void setSelectionIndex(Integer newSelectionIndex)
	{
		rowsListView.getListSelectionModel().setSelectionInterval(newSelectionIndex,
				newSelectionIndex);
	}

	/**
	 * @see {@link javax.swing.JTable#getModel()}
	 */
	public TableModel getTableModel()
	{
		return (TableModel)getDefaultModelObject();
	}

	/**
	 * @see {@link javax.swing.JTable#setAutoCreateRowSorter(boolean)}
	 */
	public void setAutoCreateRowSorter(boolean autoCreateRowSorter)
	{
		this.autoCreateRowSorter = autoCreateRowSorter;
		if (autoCreateRowSorter)
		{
			setRowSorter(new SerializableTableRowSorter(getTableModel()));
		}
	}

	/**
	 * @see {@link javax.swing.JTable#setRowSorter(RowSorter)}
	 */
	public void setRowSorter(RowSorter sorter)
	{
		this.rowsListView.setRowSorter(sorter);
	}

	public RowSorter getRowSorter()
	{
		return this.rowsListView.getRowSorter();
	}

	public int getSelectedRowCount()
	{
		return rowsListView.getSelectedRowCount();
	}

	/**
	 * @see {@link javax.swing.JTable#getSelectedRows()}
	 */
	public int[] getSelectedRows()
	{
		int[] viewSelection = rowsListView.getSelectedRows();
		if (getRowSorter() != null)
		{

			int[] onModelSelection = Arrays.copyOf(viewSelection, viewSelection.length);
			for (int i = 0; i < onModelSelection.length; i++)
			{
				onModelSelection[i] = getRowSorter().convertRowIndexToModel(viewSelection[i]);
			}
			return onModelSelection;
		}
		else
		{
			return viewSelection;
		}
	}

	public CellRender getCellRenderer(int row, int column)
	{
		TableColumn tableColumn = columnsModelAdapter.getColumn(column);
		CellRender renderer = tableColumn == null ? null : tableColumn.getCellRender();
		if (renderer == null)
		{
			renderer = getDefaultRenderer(getTableModel().getColumnClass(column));
		}
		return renderer;
	}

	public CellEditor getCellEditor(int row, int column)
	{
		TableColumn tableColumn = columnsModelAdapter.getColumn(column);
		CellEditor editor = tableColumn == null ? null : tableColumn.getCellEditor();
		if (editor == null)
		{
			editor = getDefaultEditor(getTableModel().getColumnClass(column));
		}
		return editor;
	}


	private Map<Class, CellRender> defaultRenderersByColumnClass = new HashMap<Class, CellRender>();
	private Map<Class, CellEditor> defaultEditorsByColumnClass = new HashMap<Class, CellEditor>();
	private static ObjectRender defaultRender = new ObjectRender();
	private static BooleanRender booleanRender = new BooleanRender();
	{
		/*
		 * TODO from the table model we can get much more informations. Is
		 * possible to add checkboxes for booleans, image components for images,
		 * date components for dates, etc.
		 */
		defaultRenderersByColumnClass.put(Object.class, defaultRender);
		defaultEditorsByColumnClass.put(Object.class, defaultRender);
		defaultRenderersByColumnClass.put(Boolean.class, booleanRender);
		defaultEditorsByColumnClass.put(Boolean.class, booleanRender);
	}

	public void setDefaultRenderer(Class<?> columnClass, CellRender renderer)
	{
		if (renderer != null)
		{
			defaultRenderersByColumnClass.put(columnClass, renderer);
		}
		else
		{
			defaultRenderersByColumnClass.remove(columnClass);
		}
	}

	protected CellRender getDefaultRenderer(Class<?> columnClass)
	{
		CellRender render = defaultRenderersByColumnClass.get(columnClass);
		if (render != null)
		{
			return render;
		}
		else
		{
			return getDefaultRenderer(columnClass.getSuperclass());
		}
	}

	public void setDefaultEditor(Class<?> columnClass, CellEditor editor)
	{
		if (editor != null)
		{
			defaultEditorsByColumnClass.put(columnClass, editor);
		}
		else
		{
			defaultEditorsByColumnClass.remove(columnClass);
		}
	}

	protected CellEditor getDefaultEditor(Class<?> columnClass)
	{
		CellEditor render = defaultEditorsByColumnClass.get(columnClass);
		if (render != null)
		{
			return render;
		}
		else
		{
			return getDefaultEditor(columnClass.getSuperclass());
		}
	}

	@Override
	protected void onComponentTag(ComponentTag tag)
	{
		super.onComponentTag(tag);
		tag.setName("table");
	}

	protected ResourceReference getCss()
	{
		return TABLE_CSS;
	}

	/**
	 * Method called on item selection to asynchronous update needed by clients
	 * applications.
	 */
	protected void onSelection(AjaxRequestTarget target)
	{
	}

	/**
	 * Adapter model to decorate operations that require row sorter update.
	 * 
	 * @author Pedro Henrique Oliveira dos Santos
	 * 
	 */
	private class TableModelAdapter extends Model
	{
		public TableModelAdapter(TableModel tableModel)
		{
			super((Serializable)tableModel);
			if (autoCreateRowSorter)
			{
				setRowSorter(new SerializableTableRowSorter(tableModel));
			}
		}

		@Override
		public void setObject(Serializable object)
		{
			super.setObject(object);
			if (autoCreateRowSorter)
			{
				setRowSorter(new SerializableTableRowSorter((TableModel)object));
			}
		}
	}

	/**
	 * Repeating component that extends the AjaxSelectableListView. The extended
	 * behavior are the table model rendering complexity partially implemented.
	 * 
	 */
	class TableListView extends AjaxSelectableListView
	{
		private RowSorter sorter;

		public TableListView(String id, IModel model)
		{
			super(id, model);
		}

		@Override
		protected void onSelection(SelectableListItem selectableListItem, AjaxRequestTarget target)
		{
			Table.this.onSelection(target);
		}

		@Override
		protected void populateSelectableItem(final SelectableListItem rowItem)
		{
			rowItem.add(new ListView("columns", columnsModelAdapter)
			{
				@Override
				protected void populateItem(ListItem dataItem)
				{

					int columnIndex = columnsModelAdapter.convertIndexToModel(dataItem.getIndex());
					int modelRowIndex = sorter != null ? sorter.convertRowIndexToModel(rowItem
							.getIndex()) : rowItem.getIndex();
					TableCellModel cellModel = new TableCellModel(getTableModel(), modelRowIndex,
							columnIndex);
					if (getTableModel().isCellEditable(modelRowIndex, columnIndex))
					{

						dataItem.add(getCellEditor(modelRowIndex, columnIndex).getEditorComponent(
								CELL_ID, cellModel, rowItem, modelRowIndex, columnIndex));
					}
					else
					{
						dataItem.add(getCellRenderer(modelRowIndex, columnIndex)
								.getRenderComponent(CELL_ID, cellModel, rowItem, modelRowIndex,
										columnIndex));
					}
				}
			});
		}

		public void setRowSorter(RowSorter newSorter)
		{
			this.sorter = newSorter;
			sorter.addRowSorterListener(new RebuildSelectionOnSortedEvent());
		}

		@Override
		protected ResourceReference getCss()
		{
			return Table.this.getCss();
		}

		public RowSorter getRowSorter()
		{
			return sorter;
		}

		public class RebuildSelectionOnSortedEvent implements RowSorterListener
		{
			@Override
			public void sorterChanged(RowSorterEvent e)
			{
				if (e.getType() == RowSorterEvent.Type.SORTED)
				{
					int[] selection = getSelectedRows();
					int[] newSelection = Arrays.copyOf(selection, selection.length);
					for (int i = 0; i < newSelection.length; i++)
					{
						int oldModelIndex = e.convertPreviousRowIndexToModel(selection[i]);
						if (oldModelIndex == -1)
						{
							// means that the table wasn't sorted and the:
							oldModelIndex = selection[i];
						}
						newSelection[i] = sorter.convertRowIndexToView(oldModelIndex);
					}
					Arrays.sort(newSelection);
					listSelectionModel.clearSelection();
					for (int i = 0; i < newSelection.length; i++)
					{
						listSelectionModel.addSelectionInterval(newSelection[i], newSelection[i]);
					}
				}
			}
		}// inner class
	}// inner class
}
