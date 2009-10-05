package org.wicketstuff.table.column;

import java.io.Serializable;

import org.wicketstuff.table.cell.CellEditor;
import org.wicketstuff.table.cell.CellRender;


/**
 * @see javax.swing.table.TableColumn
 */
public class TableColumn implements Serializable
{
	private CellRender cellRender;
	private CellEditor cellEditor;
	private int modelIndex;

	public TableColumn(int modelIndex)
	{
		this(modelIndex, null, null);
	}

	public TableColumn(int modelIndex, CellRender cellRender, CellEditor cellEditor)
	{
		this.cellRender = cellRender;
		this.cellEditor = cellEditor;
		this.modelIndex = modelIndex;
	}


	public CellRender getCellRender()
	{
		return cellRender;
	}

	public void setCellRender(CellRender cellRender)
	{
		this.cellRender = cellRender;
	}

	public CellEditor getCellEditor()
	{
		return cellEditor;
	}

	public void setCellEditor(CellEditor cellEditor)
	{
		this.cellEditor = cellEditor;
	}

	/**
	 * @see javax.swing.table.TableColumn#getModelIndex()
	 */
	public int getModelIndex()
	{
		return modelIndex;
	}
}
