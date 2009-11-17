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
package org.wicketstuff.table.repeaters;

import java.util.ArrayList;

import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.EmptyPanel;
import org.wicketstuff.table.Table;
import org.wicketstuff.table.TableUtil;
import org.wicketstuff.table.column.FooterData;
import org.wicketstuff.table.column.TableColumn;

/**
 * @author Pedro Henrique Oliveira dos Santos
 * 
 */
public class TableFooter extends ListView
{
	public static final String FOOTER_ID = "footer";
	private Table table;

	public TableFooter(String id, Table table)
	{
		super(id, table.getColumnModel());
		this.table = table;
	}

	@Override
	protected void populateItem(ListItem columnItem)
	{
		final int modelColumnIndex = table.getColumnModel().convertIndexToModel(
				columnItem.getIndex());
		TableColumn tableColumn = table.getColumnModel().getColumn(modelColumnIndex);
		if (tableColumn != null && tableColumn instanceof FooterData)
		{
			ArrayList components = table.getBodyRepeater().getColumnComponents(
					columnItem.getIndex());
			columnItem.add(((FooterData)tableColumn).getFooterComponent(FOOTER_ID, components));
			TableUtil.fillColumnCss(columnItem, table.getTableModel(), modelColumnIndex);
		}
		else
		{
			columnItem.add(new EmptyPanel(FOOTER_ID));
		}
	}

	@Override
	public boolean isVisible()
	{
		return table.getColumnModel().hasFooter();
	}
}