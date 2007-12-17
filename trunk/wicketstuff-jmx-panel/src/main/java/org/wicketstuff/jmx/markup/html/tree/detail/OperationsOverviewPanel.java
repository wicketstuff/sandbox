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
import javax.management.ObjectName;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.Model;
import org.wicketstuff.jmx.markup.html.tree.DetailPanel;
import org.wicketstuff.jmx.util.JmxMBeanWrapper;

/**
 * This panel renders all available operations ({@link MBeanOperationInfo}) of
 * an {@link ObjectName} instance (wrapped by {@link JmxMBeanWrapper}).
 * 
 * @author Gerolf Seitz
 * 
 */
public class OperationsOverviewPanel extends DetailPanel
{

	private static final long serialVersionUID = 1L;

	public OperationsOverviewPanel(String id, JmxMBeanWrapper bean)
	{
		super(id);
		setOutputMarkupId(true);

		RepeatingView view = new RepeatingView("operations");
		for (int i = 0; i < bean.getOperations().length; i++)
		{
			Panel panel = new OperationPanel(view.newChildId(), bean, bean.getOperations()[i]);
			if (i == bean.getOperations().length - 1)
			{
				panel.add(new AttributeModifier("class", true, new Model("operation-last")));
			}
			view.add(panel);
		}
		add(view);
	}
}
