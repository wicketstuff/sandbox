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

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.wicketstuff.jmx.util.JmxMBeanWrapper;
import org.wicketstuff.jmx.util.JmxUtil;

/**
 * This panel displays {@link FormComponent}s for the parameters of an
 * {@link MBeanOperationInfo} and a button to execute the operation.
 * 
 * @author Gerolf Seitz
 * 
 */
public abstract class AbstractOperationPanel extends Panel
{
	private static final long serialVersionUID = 1L;

	protected JmxMBeanWrapper jmxBean;
	protected MBeanOperationInfo operation;


	public AbstractOperationPanel(String id, JmxMBeanWrapper bean,
			final MBeanOperationInfo operation)
	{
		super(id);
		setOutputMarkupId(true);
		this.jmxBean = bean;
		this.operation = operation;

		add(new AttributeModifier("style", true, new Model("float: none;")));
		Form form = new Form("form");
		add(form);


		RepeatingView view = new RepeatingView("parameters");
		form.add(view.setRenderBodyOnly(true));

		Map map = new HashMap();
		for (MBeanParameterInfo param : operation.getSignature())
		{
			WebMarkupContainer signature = new WebMarkupContainer(view.newChildId());
			IModel model = new PropertyModel(map, param.getName());
			IModel labelModel = new Model(param.getName());

			signature.add(new Label("type", param.getType()).add(new SimpleAttributeModifier(
					"class", "paramType")));
			signature.add(new Label("paramName", param.getName()).add(new SimpleAttributeModifier(
					"class", "paramName")));
			EditorPanel panel = new EditorPanel("editor", model, labelModel, JmxUtil.getType(param
					.getType()), false);
			signature.add(panel);
			signature.add(new SimpleAttributeModifier("class", "parameter"));
			view.add(signature);
		}

		// this button processes the result of the invocation and accordingly
		// loads a string resource.
		form.add(new OperationExecuteButton("operationName", form, bean, operation, map)
		{
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Object result)
			{
				AbstractOperationPanel.this.onOperationExecute(target, result);
			}
		}.add(new Label("label", operation.getName()).setRenderBodyOnly(true)));
	}


	protected abstract void onOperationExecute(AjaxRequestTarget target, Object result);
}
