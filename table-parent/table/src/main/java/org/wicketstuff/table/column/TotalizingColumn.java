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


import java.util.Iterator;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.IHeaderContributor;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.WebComponent;
import org.apache.wicket.model.IModel;
import org.wicketstuff.table.SelectableListItem;
import org.wicketstuff.table.cell.CellEditor;
import org.wicketstuff.table.cell.CellRender;

/**
 * Javascript column that present the total of the values on his row.
 * 
 * @author Pedro Henrique Oliveira dos Santos
 * 
 */
public class TotalizingColumn extends TableColumn implements FooterData
{
	public TotalizingColumn(int modelIndex)
	{
		super(modelIndex);
		JavascriptTotalComponentRender javascriptTotalComponentRender = new JavascriptTotalComponentRender();
		setCellEditor(javascriptTotalComponentRender);
		setCellRender(javascriptTotalComponentRender);
	}

	@Override
	public Component getFooterComponent(String wicketId, List<Component> columnComponents)
	{
		return new JavascriptColumnTotal(wicketId, columnComponents);
	}

	public static class JavascriptColumnTotal extends WebComponent implements IHeaderContributor
	{
		private List<Component> atColumnComponents;

		public JavascriptColumnTotal(String id, List<Component> atColumnComponents)
		{
			super(id);
			setOutputMarkupId(true);
			this.atColumnComponents = atColumnComponents;
			for (Component atColumnComponent : atColumnComponents)
			{
				atColumnComponent.setOutputMarkupId(true);
			}
		}

		@Override
		protected void onComponentTag(ComponentTag tag)
		{
			super.onComponentTag(tag);
			tag.setName("label");
		}

		@Override
		public void renderHead(IHeaderResponse response)
		{
			String componentIds = "[";
			for (Iterator i = atColumnComponents.iterator(); i.hasNext();)
			{
				Component columnComponent = (Component)i.next();
				componentIds += String.format("'%s'", columnComponent.getMarkupId());
				if (i.hasNext())
				{
					componentIds += ", ";
				}
			}
			componentIds += "]";
			response.renderOnDomReadyJavascript(String.format("turnColumnTotalizer('%s', %s)",
					getMarkupId(), componentIds));
		}
	}

	public static class JavascriptTotalComponentRender implements CellEditor, CellRender
	{

		@Override
		public Component getEditorComponent(String id, IModel model, SelectableListItem parent,
				int row, int column)
		{
			return new JavascriptRowTotal(id);
		}

		@Override
		public Component getRenderComponent(String id, IModel model, SelectableListItem parent,
				int row, int column)
		{
			return new JavascriptRowTotal(id);
		}

	}

	public static class JavascriptRowTotal extends WebComponent implements IHeaderContributor
	{
		public JavascriptRowTotal(String id)
		{
			super(id);
			setOutputMarkupId(true);
		}

		@Override
		protected void onComponentTag(ComponentTag tag)
		{
			super.onComponentTag(tag);
			tag.setName("label");
		}

		@Override
		public void renderHead(IHeaderResponse response)
		{
			response.renderOnDomReadyJavascript(String.format("turnRowTotalizer('%s')",
					getMarkupId()));
		}
	}
}
