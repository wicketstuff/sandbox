package org.wicketstuff.table;

import java.io.Serializable;
import java.util.Arrays;

import javax.swing.table.TableModel;

import org.apache.wicket.markup.html.navigation.paging.IPageable;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

/**
 * Just a adapter class to return a list, with the size equals to the columns
 * count on table model. And publish pageable operations.
 * 
 * @author Pedro Henrique Oliveira dos Santos
 * 
 */
public class ColumnsModelAdapter extends Model implements IPageable
{
	private IModel<TableModel> tableModel;
	private int columnsPerPage = Integer.MAX_VALUE;
	private int currentPage;

	public ColumnsModelAdapter(IModel<TableModel> tableModel)
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
}