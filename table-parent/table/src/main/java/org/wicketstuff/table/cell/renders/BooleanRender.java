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
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.model.IModel;
import org.wicketstuff.table.SelectableListItem;
import org.wicketstuff.table.cell.CellEditor;
import org.wicketstuff.table.cell.CellRender;

/**
 * Default render for boolean values. Uses AjaxFormComponentUpdatingBehavior to
 * maintain model state on onchange events.
 * 
 * @author Pedro Henrique Oliveira dos Santos
 * 
 */
public class BooleanRender implements CellEditor, CellRender
{

	@Override
	public Component getEditorComponent(String id, IModel model, SelectableListItem parent,
			int row, int column)
	{
		LenientCheckBox checkBox = new LenientCheckBox(id, model);
		checkBox.add(new AjaxFormComponentUpdatingBehavior("onchange")
		{
			protected void onUpdate(AjaxRequestTarget target)
			{
			}
		});
		return checkBox;
	}

	@Override
	public Component getRenderComponent(String id, IModel model, SelectableListItem parent,
			int row, int column)
	{
		return new LenientCheckBox(id, model)
		{
			@Override
			public boolean isEnabled()
			{
				return false;
			}
		};
	}

}
