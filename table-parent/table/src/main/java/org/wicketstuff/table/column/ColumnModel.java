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
package org.wicketstuff.table.column;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.swing.table.TableModel;

import org.apache.wicket.markup.html.navigation.paging.IPageable;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

/**
 * Adapter class to return a list with columns count on table model size,
 * publish pageable operations, and provide TableColumns instances containing
 * cells rendering and editing custom implementations.
 * 
 * @author Pedro Henrique Oliveira dos Santos
 * 
 */
public class ColumnModel extends Model implements IPageable
{
	private IModel<TableModel> tableModel;
	private int columnsPerPage = Integer.MAX_VALUE;
	private int currentPage;
	private Map<Integer, TableColumn> columnMap = new HashMap<Integer, TableColumn>();

	public ColumnModel(IModel<TableModel> tableModel)
	{
		this.tableModel = tableModel;
	}

	@Override
	public Serializable getObject()
	{
		int colunsSize = 0;
		if (columnsPerPage < getTotalColumns() && isTheLastPage())
		{
			colunsSize = getTotalColumns() - (columnsPerPage * getCurrentPage());
		}
		else
		{
			colunsSize = Math.min(columnsPerPage, getTotalColumns());
		}
		return (Serializable)Arrays.asList(new Object[colunsSize]);
	}

	@Override
	public void setCurrentPage(int page)
	{
		this.currentPage = page;
	}

	@Override
	public int getPageCount()
	{
		return Math.max(1, (int)getTotalColumns() / columnsPerPage)
				+ (getTotalColumns() % columnsPerPage == 0 ? 0 : 1);
	}

	@Override
	public int getCurrentPage()
	{
		return currentPage;
	}

	public void setColumnsPerPage(int columnsPerPage)
	{
		this.columnsPerPage = columnsPerPage;
	}

	public int convertIndexToModel(int column)
	{
		return column + (columnsPerPage * currentPage);
	}

	private int getTotalColumns()
	{
		return tableModel.getObject().getColumnCount();
	}

	private boolean isTheLastPage()
	{
		return columnsPerPage * (getCurrentPage() + 1) >= getTotalColumns();
	}

	/**
	 * @see javax.swing.table.TableColumnModel#getColumn(int)
	 */
	public TableColumn getColumn(Integer columnIndex)
	{
		return columnMap.get(columnIndex);
	};

	public TableColumn getColumn(int i, boolean create)
	{
		if (getColumn(i) == null)
		{
			addColumn(new TableColumn(i));
		}
		return getColumn(i);
	};

	/**
	 * @see javax.swing.table.TableColumnModel#addColumn(javax.swing.table.TableColumn)
	 */
	public void addColumn(TableColumn column)
	{
		columnMap.put(column.getModelIndex(), column);
	};

	/**
	 * @see javax.swing.table.TableColumnModel#removeColumn(javax.swing.table.TableColumn)
	 */
	public void removeColumn(TableColumn column)
	{
		columnMap.remove(column.getModelIndex());
	}

	public boolean hasFooter()
	{
		for (TableColumn tableColumn : columnMap.values())
		{
			if (tableColumn instanceof FooterData)
			{
				return true;
			}
		}
		return false;
	}

	public boolean hasCssSpecification()
	{
		for (TableColumn tableColumn : columnMap.values())
		{
			if ((tableColumn.getCssClass() != null && !"".equals(tableColumn.getCssClass()))
					|| (tableColumn.getCssStyle() != null && !"".equals(tableColumn.getCssStyle())))
			{
				return true;
			}
		}
		return false;
	}
}