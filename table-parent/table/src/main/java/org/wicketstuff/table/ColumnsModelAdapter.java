package org.wicketstuff.table;

import java.io.Serializable;
import java.util.Arrays;

import javax.swing.table.TableModel;

import org.apache.wicket.markup.html.navigation.paging.IPageable;
import org.apache.wicket.model.Model;

/**
 * Just a adapter class to return a list, with the size equals to the columns
 * count on table model .
 * 
 * @author Pedro Henrique Oliveira dos Santos
 * 
 */
public class ColumnsModelAdapter extends Model implements IPageable
{
	private TableModel tableModel;
	private int columnsPerPage = Integer.MAX_VALUE;
	private int totalColumns;
	private int currentPage;


	public int convertIndexToModel(int column)
	{
		return column + (columnsPerPage * currentPage);
	}

	public ColumnsModelAdapter(TableModel tableModel)
	{
		this.tableModel = tableModel;
		totalColumns = tableModel.getColumnCount();
	}

	@Override
	public Serializable getObject()
	{
		int colunsSize = 0;
		if (columnsPerPage < totalColumns && isTheLastPage())
		{
			colunsSize = totalColumns - (columnsPerPage * getCurrentPage());
		}
		else
		{
			colunsSize = Math.min(columnsPerPage, totalColumns);
		}
		return (Serializable)Arrays.asList(new Object[colunsSize]);
	}


	private boolean isTheLastPage()
	{
		return columnsPerPage * (getCurrentPage() + 1) >= totalColumns;
	}

	@Override
	public void setCurrentPage(int page)
	{
		this.currentPage = page;
	}

	@Override
	public int getPageCount()
	{
		return Math.max(1, (int)tableModel.getColumnCount() / columnsPerPage)
				+ (tableModel.getColumnCount() % columnsPerPage == 0 ? 0 : 1);
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
}