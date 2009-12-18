package org.wicketstuff.table.examples;

import java.io.Serializable;
import java.util.Arrays;

import javax.swing.ListSelectionModel;
import javax.swing.table.TableModel;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.Model;
import org.wicketstuff.table.Table;
import org.wicketstuff.table.TableUtil;

public abstract class TableTestPanel extends Panel
{

	private static Integer[] listSelectionModels = { ListSelectionModel.SINGLE_SELECTION,
			ListSelectionModel.SINGLE_INTERVAL_SELECTION,
			ListSelectionModel.MULTIPLE_INTERVAL_SELECTION };
	private static String[] cssStyles = { null, "blue" };
	protected Component viewSelection;
	protected Component modelSelection;
	protected Component editionLabel;

	private Table table;

	public TableTestPanel(String id)
	{
		super(id);

		table = new Table("table", getTableModelUnderTest())
		{
			@Override
			protected void onSelection(AjaxRequestTarget target)
			{
				target.addComponent(viewSelection);
				target.addComponent(modelSelection);
			}
		};
		modifyTable(table);
		add(table);

		viewSelection = new Label("viewSelection", new AbstractReadOnlyModel()
		{
			@Override
			public Object getObject()
			{
				return Arrays.toString(TableUtil.getSelectedRows(table.getListSelectionModel()));
			}
		}).setOutputMarkupId(true);
		add(viewSelection);
		modelSelection = new Label("modelSelection", new AbstractReadOnlyModel()
		{
			@Override
			public Object getObject()
			{
				return Arrays.toString(table.getSelectedRows());
			}
		}).setOutputMarkupId(true);
		add(modelSelection);
		editionLabel = new Label("editionnOut", new Model()).setOutputMarkupId(true);
		add(editionLabel);

		add(new SelectionModeCombo("modes"));
		DropDownChoice styles = null;
		add(styles = new DropDownChoice("styles", new Model(null), Arrays.asList(cssStyles))
		{
			@Override
			protected boolean wantOnSelectionChangedNotifications()
			{
				return true;
			}
		});
		table.add(new AttributeModifier("class", true, styles.getDefaultModel()));
		table.setRowsPerPage(getRows());
		add(table.getRowsAjaxPagingNavigator("rowsPaging"));
		table.setColumnsPerPage(getColumns());
		add(table.getColumnsAjaxPagingNavigator("columnsPaging"));
	}

	protected int getColumns()
	{
		return 6;
	}

	protected int getRows()
	{
		return 8;
	}

	protected void modifyTable(Table table)
	{
	}

	protected abstract TableModel getTableModelUnderTest();

	private class SelectionModeCombo extends DropDownChoice
	{
		public SelectionModeCombo(String id)
		{
			super(id, new Model(table.getListSelectionModel().getSelectionMode()), new Model(
					(Serializable)Arrays.asList(listSelectionModels)), new IChoiceRenderer()
			{
				@Override
				public Object getDisplayValue(Object object)
				{
					switch ((Integer)object)
					{
						case ListSelectionModel.SINGLE_SELECTION :
							return "SINGLE_SELECTION";
						case ListSelectionModel.SINGLE_INTERVAL_SELECTION :
							return "SINGLE_INTERVAL_SELECTION";
						case ListSelectionModel.MULTIPLE_INTERVAL_SELECTION :
							return "MULTIPLE_INTERVAL_SELECTION";
					}
					return null;
				}

				@Override
				public String getIdValue(Object object, int index)
				{
					return object.toString();
				}
			});
		}

		@Override
		public void updateModel()
		{
			super.updateModel();
			table.setSelectionMode((Integer)getConvertedInput());
		}

		@Override
		protected boolean wantOnSelectionChangedNotifications()
		{
			return true;
		}
	}

	public static Object[][] getTestData()
	{
		return new Object[][] {
				{ true, "0", "0", "0", "0", "Words ", "Names ", "coupled", "working",
						"probability", "effects", "effects", "breakage" },
				{ false, "1", "0", "0", "0", "Sequential", "Registered", "Window", "Label",
						"MultiLineLabel", "Panel", "Border", "Include" },
				{ true, "2", "0", "0", "0", "Different  ", "Listener", "Browser", "Wicketstuff",
						"table", "swing", "javax", "RowSorter" },
				{ false, "3", "0", "0", "0", "Operators ", "Interface", "Jar", "TabbedPanel",
						"Fragment", "Link", "ExternalLink", "PageLink" },
				{ false, "4", "0", "0", "0", "Entry  ", "RequestListenerInterface", "Version",
						"BookmarkablePageLink", "Form", "Button", "SubmitLink", "TextField" },
				{ true, "5", "0", "0", "0", "Usually  ", "ILinkListener", "File", "Palette",
						"Select", "ListMultipleChoice", "Radio", "RadioChoice" },
				{ false, "6", "0", "0", "0", "Programming ", "Log", "Users", "PageParameters",
						"HomePage", "WebPage", "Model", "TableModel" },
				{ false, "7", "0", "0", "0", "Largely  ", "IResourceListener", "markup", "form",
						"markup", "link", "panel", "basic" },
				{ false, "8", "0", "0", "0", "Place  ", "IActivePageBehaviorListener", "abstract",
						"TextArea", "CheckBox", "CheckBoxMultipleChoice", "Palette",
						"DropDownChoice" } };
	}

}
