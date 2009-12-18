package org.wicketstuff.table.examples;

import javax.swing.DefaultListSelectionModel;
import javax.swing.table.TableModel;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.util.time.Duration;
import org.wicketstuff.table.Table;
import org.wicketstuff.table.cell.renders.AjaxRender;
import org.wicketstuff.table.cell.renders.ObjectRender;
import org.wicketstuff.table.column.SelfUpdateColumn;

public class AjaxColumnPanel extends TableTestPanel
{

	public AjaxColumnPanel(String id)
	{
		super(id);
	}

	@Override
	protected void modifyTable(Table table)
	{
		table.setDefaultEditor(Object.class, new AjaxRender(new ObjectRender())
		{
			@Override
			protected void onUpdate(AjaxRequestTarget target)
			{
				target.addComponent(editionLabel);
			}
		});
		table.getColumnModel().addColumn(new SelfUpdateColumn(2, Duration.seconds(5)));
		table.setAutoCreateRowSorter(true);
		DefaultListSelectionModel sm = new DefaultListSelectionModel()
		{
			@Override
			public void addSelectionInterval(int index0, int index1)
			{
				super.addSelectionInterval(index0, index1);
				super.removeSelectionInterval(2, 3);
			}

			@Override
			public void setSelectionInterval(int index0, int index1)
			{
				super.setSelectionInterval(index0, index1);
				super.removeSelectionInterval(2, 3);
			}
		};
		table.setSelectionModel(sm);
	}

	@Override
	protected TableModel getTableModelUnderTest()
	{
		SampleTableModel sampleTableModel = new SampleTableModel()
		{
			@Override
			public void setValueAt(Object aValue, int row, int column)
			{
				getValues()[row][column] = aValue;
				String out = String.format(" value at %d x %d changed to %s", row, column, aValue);
				editionLabel.setDefaultModelObject(out);
			}
		};
		return sampleTableModel;
	}
}
