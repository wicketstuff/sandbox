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
package org.wicketstuff.table.cell.components;

import java.awt.event.ActionEvent;

import javax.swing.Action;

import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.IAjaxCallDecorator;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.model.IModel;
import org.wicketstuff.table.Table;
import org.wicketstuff.table.ajax.StopEventPropagation;

/**
 * Ajaxified ButtonPanel
 * 
 * @author Pedro Henrique Oliveira dos Santos
 */
public class AjaxButtonPanel extends ButtonPanel
{

	private static final long serialVersionUID = 1L;

	public AjaxButtonPanel(String id, IModel<Action> actionModel)
	{
		super(id, actionModel);
	}

	@Override
	protected WebMarkupContainer newButton(String id, final IModel<Action> actionModel)
	{
		WebMarkupContainer button = new Button(id);
		button.add(new AjaxEventBehavior("onclick")
		{
			@Override
			protected void onEvent(AjaxRequestTarget target)
			{
				ActionEvent e = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null);
				actionModel.getObject().actionPerformed(e);
				target.addComponent(getComponent().findParent(Table.class));
			}

			@Override
			protected IAjaxCallDecorator getAjaxCallDecorator()
			{
				return new StopEventPropagation();
			}
		});
		return button;
	}
}
