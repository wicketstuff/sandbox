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


import org.apache.wicket.Component;
import org.apache.wicket.markup.html.IHeaderContributor;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.wicketstuff.table.SelectableListItem;
import org.wicketstuff.table.cell.CellEditor;
import org.wicketstuff.table.cell.CellRender;

/**
 * Javascript column that present the total of the values on his row. 
 * @author Pedro Henrique Oliveira dos Santos
 *
 */
public class TotalizingColumn extends TableColumn
{
	public TotalizingColumn(int modelIndex)
	{
		super(modelIndex);
		JavascriptTotalComponentRender javascriptTotalComponentRender = new JavascriptTotalComponentRender();
		setCellEditor(javascriptTotalComponentRender);
		setCellRender(javascriptTotalComponentRender);
	}

	public static class JavascriptTotalComponentRender implements CellEditor, CellRender
	{

		@Override
		public Component getEditorComponent(String id, IModel model, SelectableListItem parent,
				int row, int column)
		{
			return new JavascriptTotal(id);
		}

		@Override
		public Component getRenderComponent(String id, IModel model, SelectableListItem parent,
				int row, int column)
		{
			return new JavascriptTotal(id);
		}

	}
	public static class JavascriptTotal extends Label implements IHeaderContributor
	{

		/**
		 * @param id
		 */
		public JavascriptTotal(String id)
		{
			super(id);
			setOutputMarkupId(true);
		}

		@Override
		public void renderHead(IHeaderResponse response)
		{
			response
					.renderOnDomReadyJavascript(String.format("turnTotalizer('%s')", getMarkupId()));
		}

		/*
		 * Just prevent the component to get an model wrapped from his parents.
		 */
		@Override
		protected IModel<?> initModel()
		{
			return null;
		}
	}

}
