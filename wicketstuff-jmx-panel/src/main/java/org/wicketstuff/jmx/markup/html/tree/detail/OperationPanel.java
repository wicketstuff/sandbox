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
package org.wicketstuff.jmx.markup.html.tree.detail;

import javax.management.MBeanOperationInfo;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.MultiLineLabel;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.StringResourceModel;
import org.wicketstuff.jmx.markup.html.AbstractOperationPanel;
import org.wicketstuff.jmx.util.JmxMBeanWrapper;

/**
 * This panel displays {@link FormComponent}s for the parameters of an
 * {@link MBeanOperationInfo} and a button to execute the operation.
 * 
 * @author Gerolf Seitz
 * 
 */
public class OperationPanel extends AbstractOperationPanel
{
	private static final long serialVersionUID = 1L;

	private MultiLineLabel resultLabel;

	public OperationPanel(String id, JmxMBeanWrapper bean, final MBeanOperationInfo operation)
	{
		super(id, bean, operation);
		setOutputMarkupId(true);

		resultLabel = new MultiLineLabel("result");
		resultLabel.setOutputMarkupPlaceholderTag(true).setEscapeModelStrings(false);
		add(resultLabel.setVisible(false));
	}

	@Override
	protected void onOperationExecute(AjaxRequestTarget target, Object result)
	{
		IModel messageModel = null;

		// if the result is an exception, something went wrong ->
		// provide error message
		if (result instanceof Exception)
		{
			messageModel = new StringResourceModel("jmx.invocation.exception.multiline", this,
					null, new Object[] { result });
		}
		else
		{
			// check if the return type is void -> result is of no
			// interest
			if (operation.getReturnType().equals("void"))
			{
				messageModel = new StringResourceModel("jmx.invocation.success", this, null);
			}
			else
			{
				messageModel = new StringResourceModel(
						"jmx.invocation.success.with.result.multiline", this, null,
						new Object[] { result });
			}
		}
		resultLabel.setDefaultModel(messageModel).setVisible(true);
		target.addComponent(OperationPanel.this);

	}
}
