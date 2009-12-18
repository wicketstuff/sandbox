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

import java.awt.event.ActionEvent;

import javax.swing.Action;

import org.apache.wicket.ResourceReference;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;

/**
 * 
 * @author Pedro Henrique Oliveira dos Santos
 * 
 */
public class AjaxButtonPanel extends Panel
{
	private Button button;

	public AjaxButtonPanel(String id, final IModel<Action> actionModel)
	{
		super(id, actionModel);
		setRenderBodyOnly(true);
		add(button = new Button("button"));
		button.add(new AjaxEventBehavior("onclick")
		{

			@Override
			protected void onEvent(AjaxRequestTarget target)
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
				return getString(name, null, name);
			}
		}));
	}

}
