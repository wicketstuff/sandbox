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

import org.apache.wicket.ResourceReference;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;

/**
 * Use an button object to render the cell. Since an row can to be inserted,
 * removed, or some cell get new data at time, is important to render the table
 * again.
 * 
 * By default all page will to be re-rendered since no AJAX is used by this
 * component.
 * 
 * To customize the component, Action.SMALL_ICON or Action.NAME parameters can
 * to be set on the action. The Action.SMALL_ICON parameter is expected as an
 * ResourceReference object.
 * 
 * @author Pedro Henrique Oliveira dos Santos
 */
public class ButtonPanel extends Panel
{
	private Button button;

	public ButtonPanel(String id, final IModel<Action> actionModel)
	{
		super(id, actionModel);
		setRenderBodyOnly(true);
		add(button = new Button("button")
		{
			@Override
			public void onSubmit()
			{
				ActionEvent e = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null);
				actionModel.getObject().actionPerformed(e);
			}
		});
		button.add(new Image("icon")
		{
			@Override
			protected ResourceReference getImageResourceReference()
			{
				Object icon = actionModel.getObject().getValue(Action.SMALL_ICON);
				if (icon instanceof ResourceReference)
				{
					return (ResourceReference)icon;
				}
				return super.getImageResourceReference();
			}

			@Override
			public boolean isVisible()
			{
				return actionModel.getObject().getValue(Action.SMALL_ICON) != null;
			}
		});
		button.add(new Label("label", new AbstractReadOnlyModel()
		{
			@Override
			public Object getObject()
			{
				String name = (String)actionModel.getObject().getValue(Action.NAME);
				return name == null ? "" : getString(name, null, name);
			}
		}));
	}

}
