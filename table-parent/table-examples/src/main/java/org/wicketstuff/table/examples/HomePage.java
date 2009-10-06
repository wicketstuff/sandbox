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
package org.wicketstuff.table.examples;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Calendar;

import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import org.apache.wicket.Component;
import org.apache.wicket.PageParameters;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.CSSPackageResource;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.time.Duration;
import org.wicketstuff.table.Table;
import org.wicketstuff.table.TableUtil;
import org.wicketstuff.table.column.SelfUpdateColumn;

/**
 * Homepage
 */
public class HomePage extends WebPage
{

	private static String[][] values = {
			{ "0", "Words ", "Names ", "coupled", "working", "probability", "effects", "effects",
					"breakage" },
			{ "1", "Sequential", "Registered", "Window", "Label", "MultiLineLabel", "Panel",
					"Border", "Include" },
			{ "2", "Different  ", "Listener", "Browser", "Wicketstuff", "table", "swing", "javax",
					"RowSorter" },
			{ "3", "Operators ", "Interface", "Jar", "TabbedPanel", "Fragment", "Link",
					"ExternalLink", "PageLink" },
			{ "4", "Entry  ", "RequestListenerInterface", "Version", "BookmarkablePageLink",
					"Form", "Button", "SubmitLink", "TextField" },
			{ "5", "Usually  ", "ILinkListener", "File", "Palette", "Select", "ListMultipleChoice",
					"Radio", "RadioChoice" },
			{ "6", "Programming ", "Log", "Users", "PageParameters", "HomePage", "WebPage",
					"Model", "TableModel" },
			{ "7", "Largely  ", "IResourceListener", "markup", "form", "markup", "link", "panel",
					"basic" },
			{ "8", "Place  ", "IActivePageBehaviorListener", "abstract", "TextArea", "CheckBox",
					"CheckBoxMultipleChoice", "Palette", "DropDownChoice" } };
	private static Integer[] listSelectionModels = { ListSelectionModel.SINGLE_SELECTION,
			ListSelectionModel.SINGLE_INTERVAL_SELECTION,
			ListSelectionModel.MULTIPLE_INTERVAL_SELECTION };
	private Table table;
	private Component viewSelection;
	private Component modelSelection;
	private Component editionLabel;

	public HomePage(final PageParameters parameters)
	{
		add(CSSPackageResource.getHeaderContribution(HomePage.class, "style.css"));
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
		TableModel tableModel = new DefaultTableModel(values.length, values[0].length)
		{
			@Override
			public boolean isCellEditable(int row, int column)
			{
				return column == 2;
			}

			@Override
			public Object getValueAt(int row, int column)
			{
				if (column == 1 || column == 2)
				{
					return Calendar.getInstance().getTime().toString();
				}
				else
				{
					return values[row][column];
				}
			}

			@Override
			public void setValueAt(Object aValue, int row, int column)
			{
				values[row][column] = aValue == null ? null : aValue.toString();
				editionLabel.setDefaultModelObject(" value at " + row + " x " + column
						+ " changed to " + aValue);
				AjaxRequestTarget.get().addComponent(editionLabel);
			}
		};
		table = new Table("table", tableModel)
		{
			@Override
			protected void onSelection(AjaxRequestTarget target)
			{
				target.addComponent(viewSelection);
				target.addComponent(modelSelection);
			}
		};
		table.getColumnModel().addColumn(new SelfUpdateColumn(1, Duration.seconds(5)));
		table.getColumnModel().addColumn(new SelfUpdateColumn(2, Duration.seconds(2)));
		table.setAutoCreateRowSorter(true);
		add(table);
		table.setRowsPerPage(4);
		add(table.getRowsAjaxPagingNavigator("rowsPaging"));
		table.setColumnsPerPage(4);
		add(table.getColumnsAjaxPagingNavigator("columnsPaging"));
		add(new SelectionModeCombo("modes"));
	}

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
}
