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
package org.wicketstuff.table.cell.renders;

import org.apache.wicket.Component;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.wicketstuff.table.SelectableListItem;
import org.wicketstuff.table.cell.CellEditor;
import org.wicketstuff.table.cell.CellRender;
import org.wicketstuff.table.cell.components.LenientTextField;

/**
 * Default implementation for CellRender and CellEditor
 * 
 * @author Pedro Henrique Oliveira dos Santos
 * 
 */
public class ObjectRender implements CellRender, CellEditor
{
	@Override
	public Component getRenderComponent(String id, IModel model, SelectableListItem parent,
			int row, int column)
	{
		return new Label(id, model);
	}

	@Override
	public Component getEditorComponent(String id, IModel model, SelectableListItem parent,
			int row, int column)
	{
		TextField field = new LenientTextField(id, model)
		{
			@Override
			protected void onComponentTag(ComponentTag tag)
			{
				super.onComponentTag(tag);
				tag.put("class", "editCell");
			}
		};
		return field;
	}
}
