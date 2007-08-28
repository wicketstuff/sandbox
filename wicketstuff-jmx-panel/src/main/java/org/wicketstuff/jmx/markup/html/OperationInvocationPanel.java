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
package org.wicketstuff.jmx.markup.html;

import java.util.HashMap;
import java.util.Map;

import javax.management.MBeanOperationInfo;
import javax.management.MBeanParameterInfo;

import org.apache.wicket.Component;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.wicketstuff.jmx.util.JmxMBeanWrapper;
import org.wicketstuff.jmx.util.JmxUtil;

/**
 * This panel displays FormComponents for the various parameters of an
 * operation.
 * 
 * @author Gerolf Seitz
 * 
 */
public class OperationInvocationPanel extends Panel
{
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	public OperationInvocationPanel(String id, final JmxMBeanWrapper mbean,
			final MBeanOperationInfo operation)
	{
		super(id);
		setOutputMarkupId(true);
		final Map modelMap = new HashMap();
		Form form = new Form("form", new CompoundPropertyModel(modelMap));
		add(form);

		RepeatingView fields = new RepeatingView("fields");
		form.add(fields);

		for (MBeanParameterInfo param : operation.getSignature())
		{
			WebMarkupContainer row = new WebMarkupContainer(fields.newChildId());
			fields.add(row);

			IModel labelModel = new Model(param.getName());

			// Create a model to bind our editor component to the bean.
			IModel model = new PropertyModel(modelMap, param.getName());

			final Class type = JmxUtil.getType(param.getType());

			Component editor = new EditorPanel("editor", model, labelModel, type, false);

			row.add(editor);
			row.add(new Label("label", labelModel));
		}

		form.add(new FeedbackPanel("feedback"));

		form.add(new AjaxButton("invoke", form)
		{
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form form)
			{
				MBeanParameterInfo[] params = operation.getSignature();
				Object[] values = new Object[params.length];
				String[] signature = new String[params.length];
				Object result = null;
				for (int i = 0; i < params.length; i++)
				{
					values[i] = modelMap.get(params[i].getName());
					signature[i] = JmxUtil.getType(params[i].getType()).getName();
				}
				try
				{
					result = mbean.invoke(operation, values, signature);
				}
				catch (WicketRuntimeException e)
				{
					result = e;
				}
				Panel panel = new OperationInvocationResultPanel(OperationInvocationPanel.this
						.getId(), operation, result, mbean, OperationInvocationPanel.this);
				OperationInvocationPanel.this.replaceWith(panel);
				target.addComponent(panel);
			}
		});

	}
}
