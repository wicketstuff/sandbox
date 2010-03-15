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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.swing.ListSelectionModel;

import org.apache.wicket.Component;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.IHeaderContributor;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.wicketstuff.table.SelectableListItem;
import org.wicketstuff.table.Table;
import org.wicketstuff.table.TableUtil;

/**
 * Repeater component that add behavior to every row to handle clicks events and
 * manage the selection state.
 * 
 * @author Pedro Henrique Oliveira dos Santos
 * 
 */
public abstract class AbstractSelectableListView<T> extends PageableListView<T>
		implements
			IHeaderContributor
{
	private static final long serialVersionUID = 1L;
	public static final ResourceReference CSS = new ResourceReference(Table.class,
			"res/selectableListView.css");
	/*
	 * Used to store the index used as bound for selection ranget when user
	 * select an row with shift pressed.
	 */
	private Integer lastNonShiftSelection;
	private boolean preventSelectTwice = true;

	protected ListSelectionModel listSelectionModel;

	public AbstractSelectableListView(String id, IModel model)
	{
		this(id, model, Integer.MAX_VALUE);
	}

	public AbstractSelectableListView(String id, List list)
	{
		this(id, list, Integer.MAX_VALUE);
	}

	public AbstractSelectableListView(String id, List list, int rowsPerPage)
	{
		this(id, new Model((Serializable)list), rowsPerPage);
	}

	public AbstractSelectableListView(String id, IModel model, int rowsPerPage)
	{
		this(id, model, rowsPerPage, TableUtil.createSingleSelectionModel());
	}

	public AbstractSelectableListView(String id, IModel model, int rowsPerPage,
			ListSelectionModel selectionModel)
	{
		super(id, model, rowsPerPage);
		this.listSelectionModel = selectionModel;
	}

	public abstract void onSelection(SelectableListItem<T> selectableListItem,
			AjaxRequestTarget target);

	@Override
	protected ListItem<T> newItem(final int index)
	{
		return new DefaultSelectableListItem(this, index, getListItemModel(getModel(), index));
	}

	/**
	 * Method responsible to resolve items selection.
	 */
	void rowClicked(final SelectableListItem<T> clickedItem, final AjaxRequestTarget target,
			boolean shiftPressed, boolean ctrlPressed)
	{
		int[] oldSelections = getSelectedRows();
		int newSelection = clickedItem.getIndex();
		if (shiftPressed
				&& getListSelectionModel().getSelectionMode() != ListSelectionModel.SINGLE_SELECTION)
		{
			int referenceSelection = lastNonShiftSelection == null ? 0 : lastNonShiftSelection;
			listSelectionModel.setSelectionInterval(Math.min(referenceSelection, newSelection),
					Math.max(referenceSelection, newSelection));
		}
		else if (ctrlPressed)
		{
			if (listSelectionModel.isSelectedIndex(newSelection))
			{
				listSelectionModel.removeSelectionInterval(newSelection, newSelection);
			}
			else
			{
				listSelectionModel.addSelectionInterval(newSelection, newSelection);
			}
		}
		else
		{
			listSelectionModel.setSelectionInterval(clickedItem.getIndex(), clickedItem.getIndex());
		}
		int[] newSelections = getSelectedRows();
		if (!shiftPressed)
		{
			lastNonShiftSelection = clickedItem.getIndex();
		}
		final Set toUpdate = TableUtil.getRowsToUpdate(oldSelections, newSelections);
		visitChildren(SelectableListItem.class, new IVisitor()
		{
			public Object component(Component component)
			{
				SelectableListItem listItem = (SelectableListItem)component;
				if (toUpdate.contains(listItem.getIndex()))
				{
					listItem.updateOnAjaxRequest(target);
				}
				return IVisitor.CONTINUE_TRAVERSAL;
			}
		});
		onSelection(clickedItem, target);
	}

	public SelectableListItem<T> getSelection()
	{
		return getMinSelection();
	}

	public SelectableListItem<T> getMinSelection()
	{
		return (SelectableListItem)visitChildren(SelectableListItem.class, new IVisitor()
		{
			public Object component(Component component)
			{
				int rowIndex = ((SelectableListItem)component).getIndex();
				if (rowIndex == listSelectionModel.getMinSelectionIndex())
				{
					return component;
				}
				else
				{
					return IVisitor.CONTINUE_TRAVERSAL;
				}
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
		return TableUtil.getSelectedRows(listSelectionModel);
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

	public void setListSelectionModle(ListSelectionModel listSelectionModel)
	{
		this.listSelectionModel = listSelectionModel;
	}

	public void clearSelection()
	{
		listSelectionModel.clearSelection();
	}

	@Override
	public void renderHead(final IHeaderResponse response)
	{
		ResourceReference css = getCss();
		if (css != null && !response.wasRendered(css))
		{
			css.setStyle(getSession().getStyle());
			response.renderCSSReference(css);
			response.markRendered(css);
		}
		final List<String> itensIds = new ArrayList<String>();
		final List<Boolean> itensSelection = new ArrayList<Boolean>();
		visitChildren(SelectableListItem.class, new IVisitor<SelectableListItem>()
		{
			@Override
			public Object component(SelectableListItem component)
			{
				itensIds.add(component.getMarkupId());
				itensSelection.add(component.isSelected());
				return IVisitor.CONTINUE_TRAVERSAL;
			}
		});
		if (itensIds.size() > 0)
		{
			String selection = "[";
			for (Iterator i = itensSelection.iterator(); i.hasNext();)
			{
				selection += i.next() + (i.hasNext() ? "," : "]");
			}
			String ids = "['";
			for (Iterator i = itensIds.iterator(); i.hasNext();)
			{
				ids += i.next() + (i.hasNext() ? "','" : "']");
			}
			response.renderOnDomReadyJavascript("initRows(" + ids + ", " + selection + ")");
		}
	}

	/**
	 * Override this method to return an custom css.
	 * 
	 * @return custom css reference
	 */
	protected ResourceReference getCss()
	{
		return CSS;
	}

	public void selectItemWithObjectOnModel(final T obj)
	{
		for (Iterator i = getList().iterator(); i.hasNext();)
		{
			Object selectableItem = (Object)i.next();
			if (obj.equals(selectableItem))
			{
				int selection = getList().indexOf(selectableItem);
				listSelectionModel.addSelectionInterval(selection, selection);
			}

		}
	}

	/**
	 * @param preventSelectTwice
	 *            the preventSelectTwice to set
	 */
	public void setPreventSelectTwice(boolean preventSelectTwice)
	{
		this.preventSelectTwice = preventSelectTwice;
	}

	public boolean isPreventSelectTwice()
	{
		return this.preventSelectTwice;
	}
}