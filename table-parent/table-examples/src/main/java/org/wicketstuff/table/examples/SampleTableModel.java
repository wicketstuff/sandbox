package org.wicketstuff.table.examples;

import java.util.Calendar;

import javax.swing.table.DefaultTableModel;

public class SampleTableModel extends DefaultTableModel
{

	private Object[][] values;

	@Override
	public int getColumnCount()
	{
		return getValues()[0].length;
	}

	@Override
	public int getRowCount()
	{
		return getValues().length;
	}

	@Override
	public boolean isCellEditable(int row, int column)
	{
		return column == 0 || column == 3;
	}

	@Override
	public Class<?> getColumnClass(int columnIndex)
	{
		if (columnIndex == 0)
		{
			return Boolean.class;
		}
		else
		{
			return super.getColumnClass(columnIndex);
		}
	}

	@Override
	public Object getValueAt(int row, int column)
	{
		if (column == 2)
		{
			return String.format("%tT", Calendar.getInstance().getTime());
		}
		else
		{
			return getValues()[row][column];
		}
	}

	protected Object[][] getValues()
	{
		if (values == null)
		{
			initValues();
		}
		return values;
	}

	protected void initValues()
	{
		values = new Object[][] {
				{ true, "0", "0", "Words ", "Names ", "coupled", "working", "probability",
						"effects", "effects", "breakage" },
				{ false, "1", "0", "Sequential", "Registered", "Window", "Label", "MultiLineLabel",
						"Panel", "Border", "Include" },
				{ true, "2", "0", "Different  ", "Listener", "Browser", "Wicketstuff", "table",
						"swing", "javax", "RowSorter" },
				{ false, "3", "0", "Operators ", "Interface", "Jar", "TabbedPanel", "Fragment",
						"Link", "ExternalLink", "PageLink" },
				{ false, "4", "0", "Entry  ", "RequestListenerInterface", "Version",
						"BookmarkablePageLink", "Form", "Button", "SubmitLink", "TextField" },
				{ true, "5", "0", "Usually  ", "ILinkListener", "File", "Palette", "Select",
						"ListMultipleChoice", "Radio", "RadioChoice" },
				{ false, "6", "0", "Programming ", "Log", "Users", "PageParameters", "HomePage",
						"WebPage", "Model", "TableModel" },
				{ false, "7", "0", "Largely  ", "IResourceListener", "markup", "form", "markup",
						"link", "panel", "basic" },
				{ false, "8", "0", "Place  ", "IActivePageBehaviorListener", "abstract",
						"TextArea", "CheckBox", "CheckBoxMultipleChoice", "Palette",
						"DropDownChoice" } };
	}

}