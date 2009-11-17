package org.wicketstuff.table.examples;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import org.wicketstuff.table.cell.StylizedCell;

public class NumberTablePanel extends TableTestPanel
{

	public NumberTablePanel(String id)
	{
		super(id);
	}

	@Override
	protected TableModel getTableModelUnderTest()
	{
		return new NumberTableModel();
	}

	@Override
	protected int getRows()
	{
		return 10;
	}

	@Override
	protected int getColumns()
	{
		return 10;
	}

	public static class NumberTableModel extends DefaultTableModel implements StylizedCell
	{

		@Override
		public String getCellCss(int row, int column)
		{
			Number value = (Number)getValueAt(row, column);
			if (value.doubleValue() >= 7)
			{
				return "high";
			}
			else if (value.doubleValue() >= 5)
			{
				return "average";
			}
			else
			{
				return "low";
			}
		}

		@Override
		public Object getValueAt(int row, int column)
		{
			return row * column;
		}

		@Override
		public int getColumnCount()
		{
			return 10;
		}

		@Override
		public int getRowCount()
		{
			return 30;
		}

		@Override
		public boolean isCellEditable(int row, int column)
		{
			return false;
		}
	}
}
