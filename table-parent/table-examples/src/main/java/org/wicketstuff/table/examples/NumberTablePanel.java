package org.wicketstuff.table.examples;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import org.apache.wicket.ResourceReference;
import org.wicketstuff.table.Table;
import org.wicketstuff.table.cell.StylizedCell;
import org.wicketstuff.table.cell.renders.AjaxActionRender;

public class NumberTablePanel extends TableTestPanel
{

	public NumberTablePanel(String id)
	{
		super(id);
		setRows(10);
		setColumns(10);
	}

	@Override
	protected void modifyTable(Table table)
	{
		super.modifyTable(table);
		table.setWidths("80px", "30px", "30px", "30px", "30px", "30px");
		table.setDefaultRenderer(Action.class, new AjaxActionRender());
	}

	@Override
	protected TableModel createTableModelUnderTest()
	{
		return new NumberTableModel();
	}

	public static class NumberTableModel extends DefaultTableModel implements StylizedCell
	{
		@Override
		public String getCellCss(int row, int column)
		{
			Number value = (Number)getValueAt(row, column + 1);
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
			if (column == 0)
			{
				Action a = new AbstractAction()
				{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						System.out.println("Executed");
					}
				};
				a.putValue(Action.NAME, "teste");
				a.putValue(Action.SMALL_ICON, new ResourceReference(NumberTablePanel.class,
						"agt_softwareD.png"));
				return a;
			}
			return row * column;
		}

		@Override
		public Class<?> getColumnClass(int columnIndex)
		{
			if (columnIndex == 0)
			{
				return Action.class;
			}
			return super.getColumnClass(columnIndex);
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
