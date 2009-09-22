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
import java.util.List;
import java.util.Set;

import javax.swing.ListSelectionModel;
import javax.swing.RowSorter;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Repeater component that add behavior to every row to handle clicks events and
 * manage the selection state. Actually the component only dial with
 * ListSelectionModel on mode: ListSelectionModel.SINGLE_SELECTION
 * 
 * @author Pedro Henrique Oliveira dos Santos
 * 
 */
public abstract class AjaxSelectableListView extends PageableListView
{
	private static final Logger log = LoggerFactory.getLogger(AjaxSelectableListView.class);
	private static final long serialVersionUID = 1L;
	protected ListSelectionModel listSelectionModel;
	protected RowSorter sorter;

	public AjaxSelectableListView(String id, IModel model)
	{
		this(id, model, Integer.MAX_VALUE);
	}

	public AjaxSelectableListView(String id, List list)
	{
		this(id, list, Integer.MAX_VALUE);
	}

	public AjaxSelectableListView(String id, List list, int rowsPerPage)
	{
		this(id, new Model((Serializable)list), rowsPerPage);
	}

	public AjaxSelectableListView(String id, IModel model, int rowsPerPage)
	{
		this(id, model, rowsPerPage, TableUtil.createSingleSelectionModel());
	}

	public AjaxSelectableListView(String id, IModel model, int rowsPerPage,
			ListSelectionModel selectionModel)
	{
		super(id, model, rowsPerPage);
		this.listSelectionModel = selectionModel;
	}

	@Override
	protected ListItem newItem(final int index)
	{
		final SelectableListItem listItem = new SelectableListItem(index, getListItemModel(
				getModel(), index), listSelectionModel)
		{
			@Override
			protected void onItemSelection(AjaxRequestTarget target, boolean shiftPressed)
			{
				AjaxSelectableListView.this.rowClicked(this, target, shiftPressed);
			}

			@Override
			protected int getIndexOnSelectionModel()
			{
				if (sorter != null)
				{
					return sorter.convertRowIndexToModel(getIndex());
				}
				else
				{
					return super.getIndexOnSelectionModel();
				}
			}
		};
		return listItem;
	}

	@Override
	protected void populateItem(ListItem rowItem)
	{
		int rowIndex = rowItem.getIndex();
		if (sorter != null)
		{
			rowIndex = sorter.convertRowIndexToModel(rowIndex);
		}
		log.debug("rendering: " + rowItem.getIndex() + " converted to: " + rowIndex + " using: "
				+ sorter);
		populateRow(rowItem, rowIndex);
	}

	abstract protected void populateRow(ListItem rowItem, int rowIndex);

	private int lastNonShiftSelection = 0;

	/**
	 * Method responsible to resolve items selection. The actual implementation
	 * only do that for an listSelectionModel in a
	 * ListSelectionModel.SINGLE_SELECTION mode
	 * 
	 * @param selectedItem
	 * @param target
	 */
	protected void rowClicked(final SelectableListItem selectedItem,
			final AjaxRequestTarget target, boolean shiftPressed)
	{
		int[] oldSelection = getSelectedRows();
		listSelectionModel.setSelectionInterval(shiftPressed ? lastNonShiftSelection : selectedItem
				.getIndexOnSelectionModel(), selectedItem.getIndexOnSelectionModel());
		int[] newSelection = getSelectedRows();
		if (!shiftPressed)
		{
			lastNonShiftSelection = selectedItem.getIndexOnSelectionModel();
		}
		final Set toUpdate = TableUtil.getRowsToUpdate(oldSelection, newSelection);
		visitChildren(SelectableListItem.class, new IVisitor()
		{
			public Object component(Component component)
			{
				SelectableListItem listItem = (SelectableListItem)component;
				if (toUpdate.contains(listItem.getIndexOnSelectionModel()))
				{
					listItem.updateOnAjaxRequest(target);
				}
				return null;
			}
		});
		onSelection(selectedItem, target);
	}

	public IModel getMinSelection()
	{
		return (IModel)visitChildren(SelectableListItem.class, new IVisitor()
		{
			public Object component(Component component)
			{
				int rowIndex = ((SelectableListItem)component).getIndex();
				if (sorter != null)
				{
					rowIndex = sorter.convertRowIndexToModel(rowIndex);
				}
				if (rowIndex == listSelectionModel.getMinSelectionIndex())
				{
					return component.getDefaultModel();
				}
				return null;
			}
		});
	}

	/**
	 * Returns the indices of all selected rows.
	 * 
	 * @return an array of integers containing the indices of all selected rows,
	 *         or an empty array if no row is selected
	 * @see #getSelectedRow
	 */
	public int[] getSelectedRows()
	{
		int iMin = listSelectionModel.getMinSelectionIndex();
		int iMax = listSelectionModel.getMaxSelectionIndex();

		if ((iMin == -1) || (iMax == -1))
		{
			return new int[0];
		}

		int[] rvTmp = new int[1 + (iMax - iMin)];
		int n = 0;
		for (int i = iMin; i <= iMax; i++)
		{
			if (listSelectionModel.isSelectedIndex(i))
			{
				rvTmp[n++] = i;
			}
		}
		int[] rv = new int[n];
		System.arraycopy(rvTmp, 0, rv, 0, n);
		return rv;
	}

	/**
	 * Returns the number of selected rows.
	 * 
	 * @return the number of selected rows, 0 if no rows are selected
	 */
	public int getSelectedRowCount()
	{
		int iMin = listSelectionModel.getMinSelectionIndex();
		int iMax = listSelectionModel.getMaxSelectionIndex();
		int count = 0;

		for (int i = iMin; i <= iMax; i++)
		{
			if (listSelectionModel.isSelectedIndex(i))
			{
				count++;
			}
		}
		return count;
	}

	public ListSelectionModel getListSelectionModel()
	{
		return listSelectionModel;
	};

	public void setRowSorter(RowSorter sorter)
	{
		this.sorter = sorter;
	}

	public RowSorter getRowSorter()
	{
		return sorter;
	}

	public void clearSelection()
	{
		listSelectionModel.clearSelection();
	}

	protected void onSelection(SelectableListItem selectableListItem, AjaxRequestTarget target)
	{

	}

}