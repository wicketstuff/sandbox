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
package org.wicketstuff.jmx.markup.html.table;

import javax.management.MBeanOperationInfo;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.StringResourceModel;
import org.wicketstuff.jmx.util.JmxMBeanWrapper;

/**
 * Renders the result of an operation invocation.
 * 
 * @author Gerolf Seitz
 * 
 */
public class OperationInvocationResultPanel extends Panel
{

	private static final long serialVersionUID = 1L;

	public OperationInvocationResultPanel(String id, final MBeanOperationInfo operation,
			Object result, final JmxMBeanWrapper mbean, final OperationInvocationPanel invokePanel)
	{
		super(id);
		this.setOutputMarkupId(true);

		IModel messageModel = null;

		// if the result is an exception, something went wrong -> provide error
		// message
		if (result instanceof Exception)
		{
			messageModel = new StringResourceModel("jmx.invocation.exception", this, null,
					new Object[] { result });
		}
		else
		{
			// check if the return type is void -> result is of no interest
			if (operation.getReturnType().equals("void"))
			{
				messageModel = new StringResourceModel("jmx.invocation.success", this, null);
			}
			else
			{
				messageModel = new StringResourceModel("jmx.invocation.success.with.result", this,
						null, new Object[] { result });
			}
		}

		add(new Label("message", messageModel));
		AjaxLink backLink = new AjaxLink("backLink")
		{
			private static final long serialVersionUID = 1L;

			public void onClick(AjaxRequestTarget target)
			{
				OperationInvocationResultPanel.this.replaceWith(invokePanel);
				target.addComponent(invokePanel);
			}
		};
		add(backLink);
		backLink.add(new Label("linkLabel", new StringResourceModel("jmx.backLink.label", backLink,
				null)));
	}
}
