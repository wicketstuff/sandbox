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

import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.ResourceModel;
import org.wicketstuff.table.OrderingImage;
import org.wicketstuff.table.Table;
import org.wicketstuff.table.column.StylizedHeader;

/**
 * @author Pedro Henrique Oliveira dos Santos
 * 
 */
public class TableHeader extends ListView
{
	private Table table;

	public TableHeader(String id, Table table)
	{
		super(id, table.getColumnModel());
		this.table = table;
	}

	@Override
	protected void populateItem(final ListItem item)
	{
		final int columnIndex = table.getColumnModel().convertIndexToModel(item.getIndex());
		String header = table.getTableModel().getColumnName(columnIndex);
		if (table.getTableModel() instanceof StylizedHeader)
		{
			String style = ((StylizedHeader)table.getTableModel())
					.getColumnHeaderCssClass(columnIndex);
			if (style != null)
			{
				item.add(new SimpleAttributeModifier("class", style));
			}
		}
		item.add(new Label("header", new ResourceModel(header, header)));
		item.add(new OrderingImage("arrow", columnIndex, table));
		item.add(new AjaxEventBehavior("onclick")
		{
			@Override
			protected void onEvent(AjaxRequestTarget target)
			{
				if (table.getRowSorter() != null)
				{
					table.getRowSorter().toggleSortOrder(columnIndex);
					target.addComponent(table);
				}
			}
		});
	}
}