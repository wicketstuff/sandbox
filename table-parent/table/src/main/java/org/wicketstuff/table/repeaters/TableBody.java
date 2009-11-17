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
package org.wicketstuff.table.repeaters;

import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.RowSorter;
import javax.swing.event.RowSorterEvent;
import javax.swing.event.RowSorterListener;
import javax.swing.table.TableModel;

import org.apache.wicket.ResourceReference;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.wicketstuff.table.ListModelAdapter;
import org.wicketstuff.table.SelectableListItem;
import org.wicketstuff.table.Table;
import org.wicketstuff.table.TableUtil;
import org.wicketstuff.table.cell.TableCellModel;

/**
 * Repeating component that extends the AjaxSelectableListView to dial with
 * table model rendering complexity. The view state are keep on a
 * {@link javax.swing.ListSelectionModel} and an {@link javax.swing.RowSorter}
 * 
 * @author Pedro Henrique Oliveira dos Santos
 */
public abstract class TableBody extends AbstractSelectableListView
{
	public static final String CELL_ID = "cell";
	private RowSorter sorter;
	private Table table;

	public TableBody(String id, Table table)
	{
		super(id, new ListModelAdapter((IModel<TableModel>)table.getDefaultModel()));
		this.table = table;
	}

	@Override
	protected void populateItem(final ListItem rowItem)
	{
		rowItem.add(new ListView("columns", table.getColumnModel())
		{
			@Override
			protected void populateItem(final ListItem columnItem)
			{
				int modelColumnIndex = table.getColumnModel().convertIndexToModel(
						columnItem.getIndex());
				TableUtil.fillColumnCss(columnItem, table.getTableModel(), modelColumnIndex);
				int modelRowIndex = sorter != null ? sorter.convertRowIndexToModel(rowItem
						.getIndex()) : rowItem.getIndex();
				TableUtil.fillCellCss(columnItem, table.getTableModel(), modelColumnIndex, modelRowIndex);
				populateColumn((SelectableListItem)rowItem, columnItem, modelRowIndex,
						modelColumnIndex);
			}
		});
	}

	protected void populateColumn(final SelectableListItem rowItem, ListItem columnItem,
			int modelRowIndex, int modelColumnIndex)
	{
		TableCellModel cellModel = new TableCellModel(table.getTableModel(), modelRowIndex,
				modelColumnIndex);
		if (table.getTableModel().isCellEditable(modelRowIndex, modelColumnIndex))
		{
			columnItem.add(table.getCellEditor(modelRowIndex, modelColumnIndex).getEditorComponent(
					TableBody.CELL_ID, cellModel, rowItem, modelRowIndex, modelColumnIndex));
		}
		else
		{
			columnItem.add(table.getCellRenderer(modelRowIndex, modelColumnIndex)
					.getRenderComponent(TableBody.CELL_ID, cellModel, rowItem, modelRowIndex,
							modelColumnIndex));
		}
	}


	public void setRowSorter(RowSorter newSorter)
	{
		this.sorter = newSorter;
		sorter.addRowSorterListener(new RebuildSelectionOnSortedEvent());
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

	public ArrayList getColumnComponents(final int columnViewIndex)
	{

		final ArrayList components = new ArrayList();
		visitChildren(ListItem.class, new IVisitor<ListItem>()
		{
			@Override
			public Object component(ListItem listItem)
			{
				if (!(listItem instanceof SelectableListItem)
						&& listItem.getIndex() == columnViewIndex)
				{
					if (listItem.get(CELL_ID) != null)
					{
						components.add(listItem.get(CELL_ID));
					}
				}
				return null;
			}
		});
		return components;
	}

	/*
	 * Returning an null css reference. The table component already has one.
	 * 
	 * @see org.wicketstuff.table.repeaters.AbstractSelectableListView#getCss()
	 */
	@Override
	protected ResourceReference getCss()
	{
		return null;
	}
}