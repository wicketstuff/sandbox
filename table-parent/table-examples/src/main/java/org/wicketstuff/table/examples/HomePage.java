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

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import org.apache.wicket.Component;
import org.apache.wicket.PageParameters;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.Model;
import org.wicketstuff.table.Table;

/**
 * Homepage
 */
public class HomePage extends WebPage
{

	private static String[][] values = {
			{ "registered", "Window", "Label", "MultiLineLabel", "Panel", "Border", "Include" },
			{ "listener", "Browser", "wicketstuff", "table", "swing", "javax", "RowSorter" },
			{ "interface", "jar", "TabbedPanel", "Fragment", "Link", "ExternalLink", "PageLink" },
			{ "RequestListenerInterface", "version", "BookmarkablePageLink", "Form", "Button",
					"SubmitLink", "TextField" },
			{ "ILinkListener", "file", "Palette", "Select", "ListMultipleChoice", "Radio",
					"RadioChoice" },
			{ "log", "Users", "PageParameters", "HomePage", "WebPage", "Model", "TableModel" },
			{ "IResourceListener", "markup", "form", "markup", "link", "panel", "basic" },
			{ "IActivePageBehaviorListener", "abstract", "TextArea", "CheckBox",
					"CheckBoxMultipleChoice", "Palette", "DropDownChoice" } };

	public HomePage(final PageParameters parameters)
	{
		final Component selectionLabel = new Label("selectionOut", new Model())
				.setOutputMarkupId(true);
		final Component editionLabel = new Label("editionnOut", new Model())
				.setOutputMarkupId(true);
		add(selectionLabel);
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
				return values[row][column];
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
		Table table = null;
		add(table = new Table("table", tableModel)
		{
			@Override
			protected void onSelection(int newSelectionIndex, AjaxRequestTarget target)
			{
				selectionLabel.setDefaultModelObject(" " + newSelectionIndex);
				target.addComponent(selectionLabel);
			}
		});
		table.setAutoCreateRowSorter(true);
		add(table.getRowsAjaxPagingNavigator("rowsPaging", 4));
		// add(new EmptyPanel("columnsPaging"));
		add(table.getColumnsAjaxPagingNavigator("columnsPaging", 4));
	}
}
