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

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Action;
import javax.swing.ListSelectionModel;
import javax.swing.RowSorter;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.RowSorterEvent;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import org.apache.wicket.ResourceReference;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.navigation.paging.AjaxPagingNavigator;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.IHeaderContributor;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.wicketstuff.table.cell.CellEditor;
import org.wicketstuff.table.cell.CellRender;
import org.wicketstuff.table.cell.renders.BooleanRender;
import org.wicketstuff.table.cell.renders.NumberRender;
import org.wicketstuff.table.cell.renders.ObjectRender;
import org.wicketstuff.table.cell.renders.file.FileRender;
import org.wicketstuff.table.column.ActionRender;
import org.wicketstuff.table.column.ColGroup;
import org.wicketstuff.table.column.ColumnModel;
import org.wicketstuff.table.column.TableColumn;
import org.wicketstuff.table.repeaters.TableBody;
import org.wicketstuff.table.repeaters.TableFooter;
import org.wicketstuff.table.repeaters.TableHeader;
import org.wicketstuff.table.sorter.SerializableTableRowSorter;

/**
 * Table component to present an swing TableModel. Responsible to hold model
 * data and provide an functional API. Mostly delegate the calls to correct
 * place.
 * 
 * @author Pedro Henrique Oliveira dos Santos
 * 
 */
public class Table extends Panel implements IHeaderContributor
{

	private static final long serialVersionUID = 1L;
	public static final ResourceReference TABLE_CSS = new ResourceReference(Table.class,
			"res/table.css");
	private TableBody tableBody;
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
		setOutputMarkupId(true);
		setDefaultModel(new TableModelAdapter(swingTableModel));
		columnsModelAdapter = new ColumnModel((IModel<TableModel>)getDefaultModel());
		add(new ColGroup("colGroup", this));
		add(new TableHeader("headers", this));
		add(tableBody = new TableBody("rows", this)
		{
			@Override
			public void onSelection(SelectableListItem selectableListItem, AjaxRequestTarget target)
			{
				Table.this.onSelection(target);
			}
		});
		add(new TableFooter("footers", this));
	}

	public AjaxPagingNavigator getRowsAjaxPagingNavigator(String id)
	{
		return new AjaxPagingNavigator(id, tableBody);
	}

	public TableBody getBodyRepeater()
	{
		return tableBody;
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
		tableBody.setRowsPerPage(rowsPerPage);
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
		tableBody.getListSelectionModel().setSelectionMode(selectionMode);
	}

	/**
	 * @see {@link javax.swing.JTable#getSelectionModel()}
	 */
	public ListSelectionModel getListSelectionModel()
	{
		return tableBody.getListSelectionModel();
	}

	/**
	 * @see {@link javax.swing.JTable#setSelectionModel(ListSelectionModel)}
	 */
	public void setSelectionModel(ListSelectionModel listSelectionModel)
	{
		tableBody.setListSelectionModle(listSelectionModel);
	}

	/**
	 * Add a listener to the list that's notified each time a change to the
	 * selection occurs.
	 */
	public void addListSelectionListener(ListSelectionListener x)
	{
		tableBody.getListSelectionModel().addListSelectionListener(x);
	}

	public void setSelectionIndex(Integer newSelectionIndex)
	{
		tableBody.getListSelectionModel()
				.setSelectionInterval(newSelectionIndex, newSelectionIndex);
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
		this.tableBody.setRowSorter(sorter);
	}

	public RowSorter getRowSorter()
	{
		return this.tableBody.getRowSorter();
	}

	/**
	 * @param widths
	 * @see org.wicketstuff.table.column.ColGroup#setWidths(java.lang.String[])
	 */
	public void setWidths(String... widths)
	{
		for (int i = 0; i < widths.length; i++)
		{
			columnsModelAdapter.getColumn(i, true).setCssStyle(
					String.format("width:%s;", widths[i]));
		}
	}

	public int getSelectedRowCount()
	{
		return tableBody.getSelectedRowCount();
	}

	/**
	 * @see {@link javax.swing.JTable#getSelectedRows()}
	 */
	public int[] getSelectedRows()
	{
		return tableBody.getSelectedRows();
	}

	/**
	 * @see {@link javax.swing.JTable#getSelectedRow()}
	 */
	public int getSelectedRow()
	{
		return getListSelectionModel().getMinSelectionIndex();
	}

	/**
	 * @see {@link javax.swing.JTable#convertRowIndexToView(int)}
	 */
	public int convertRowIndexToView(int modelRowIndex)
	{
		RowSorter sorter = getRowSorter();
		if (sorter != null)
		{
			return sorter.convertRowIndexToView(modelRowIndex);
		}
		return modelRowIndex;
	}

	/**
	 * @see {@link javax.swing.JTable#convertRowIndexToModel(int)}
	 */
	public int convertRowIndexToModel(int viewRowIndex)
	{
		RowSorter sorter = getRowSorter();
		if (sorter != null)
		{
			return sorter.convertRowIndexToModel(viewRowIndex);
		}
		return viewRowIndex;
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
	{
		/*
		 * TODO from the table model we can get much more informations. Is
		 * possible to add checkboxes for booleans, image components for images,
		 * date components for dates, etc.
		 */
		ObjectRender defaultRender = new ObjectRender();
		defaultRenderersByColumnClass.put(Object.class, defaultRender);
		defaultEditorsByColumnClass.put(Object.class, defaultRender);
		BooleanRender booleanRender = new BooleanRender();
		defaultRenderersByColumnClass.put(Boolean.class, booleanRender);
		defaultEditorsByColumnClass.put(Boolean.class, booleanRender);
		NumberRender numberRender = new NumberRender();
		defaultRenderersByColumnClass.put(Number.class, numberRender);
		defaultEditorsByColumnClass.put(Number.class, numberRender);
		ActionRender actionRender = new ActionRender();
		defaultRenderersByColumnClass.put(Action.class, actionRender);
		defaultEditorsByColumnClass.put(Action.class, actionRender);
		
		defaultRenderersByColumnClass.put(File.class, new FileRender());
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
			// setObject((Serializable)tableModel);
		}

		@Override
		public void setObject(Serializable object)
		{
			super.setObject(object);
			TableModel tableModel = (TableModel)object;
			if (autoCreateRowSorter)
			{
				setRowSorter(new SerializableTableRowSorter(tableModel));
			}
			tableModel.addTableModelListener(new TableModelListener()
			{

				@Override
				public void tableChanged(TableModelEvent e)
				{
					Table.this.tableChanged(e);
				}
			});
		}
	}

	protected ResourceReference getCss()
	{
		return TABLE_CSS;
	}

	@Override
	public void renderHead(IHeaderResponse response)
	{
		ResourceReference css = getCss();
		if (css != null && !response.wasRendered(css))
		{
			css.setStyle(getSession().getStyle());
			response.renderCSSReference(css);
			response.markRendered(css);
		}
	}

	/**
	 * @see {@link javax.swing.JTable#sorterChanged(RowSorterEvent)}
	 */
	public void sorterChanged(RowSorterEvent e)
	{
		if (e.getType() == RowSorterEvent.Type.SORTED)
		{
			sortedTableChanged(e, null);
		}
	}

	/**
	 * @see {@link javax.swing.JTable#tableChanged(TableModelEvent)}
	 */
	public void tableChanged(TableModelEvent e)
	{
		if (getRowSorter() != null)
		{
			sortedTableChanged(null, e);
			return;
		}
	}

	private void sortedTableChanged(RowSorterEvent sortedEvent, TableModelEvent e)
	{
		ModelChange change = (e != null) ? new ModelChange(e) : null;
		if (e != null)
		{
			notifySorter(change);
		}
	}

	/**
	 * @see {@link javax.swing.JTable#notifySorter(ModelChange)}
	 */
	private void notifySorter(ModelChange change)
	{
		switch (change.type)
		{
			case TableModelEvent.UPDATE :
				if (change.event.getLastRow() == Integer.MAX_VALUE)
				{
					getRowSorter().allRowsChanged();
				}
				else if (change.event.getColumn() == TableModelEvent.ALL_COLUMNS)
				{
					getRowSorter().rowsUpdated(change.startModelIndex, change.endModelIndex);
				}
				else
				{
					getRowSorter().rowsUpdated(change.startModelIndex, change.endModelIndex,
							change.event.getColumn());
				}
				break;
			case TableModelEvent.INSERT :
				getRowSorter().rowsInserted(change.startModelIndex, change.endModelIndex);
				break;
			case TableModelEvent.DELETE :
				getRowSorter().rowsDeleted(change.startModelIndex, change.endModelIndex);
				break;
		}
	}

	/**
	 * @see {@link javax.swing.JTable#ModelChange}
	 */
	private final class ModelChange
	{
		int startModelIndex;
		int endModelIndex;
		int type;
		int modelRowCount;
		TableModelEvent event;

		ModelChange(TableModelEvent e)
		{
			startModelIndex = Math.max(0, e.getFirstRow());
			endModelIndex = e.getLastRow();
			modelRowCount = getTableModel().getRowCount();
			if (endModelIndex < 0)
			{
				endModelIndex = Math.max(0, modelRowCount - 1);
			}
			type = e.getType();
			event = e;
		}
	}
}
