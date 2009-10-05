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
public class ColumnsModel extends Model implements IPageable
{
	private IModel<TableModel> tableModel;
	private int columnsPerPage = Integer.MAX_VALUE;
	private int currentPage;
	private Map<Integer, TableColumn> columnMap = new HashMap<Integer, TableColumn>();

	public ColumnsModel(IModel<TableModel> tableModel)
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
	};
}