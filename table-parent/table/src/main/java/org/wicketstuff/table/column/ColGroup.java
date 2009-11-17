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
package org.wicketstuff.table.column;

import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.wicketstuff.table.Table;

/**
 * @author Pedro Henrique Oliveira dos Santos
 * 
 */
public class ColGroup extends ListView
{

	private Table table;

	public ColGroup(String id, Table table)
	{
		super(id, table.getColumnModel());
		this.table = table;
	}

	@Override
	protected void populateItem(ListItem item)
	{
		int modelColumn = table.getColumnModel().convertIndexToModel(item.getIndex());
		TableColumn tableColumn = table.getColumnModel().getColumn(modelColumn, true);
		if (tableColumn.getCssStyle() != null)
		{
			item.add(new SimpleAttributeModifier("style", tableColumn.getCssStyle()));
		}
		if (tableColumn.getCssClass() != null)
		{
			item.add(new SimpleAttributeModifier("class", tableColumn.getCssClass()));
		}
	}

	@Override
	public boolean isVisible()
	{
		return table.getColumnModel().hasCssSpecification();
	}

}