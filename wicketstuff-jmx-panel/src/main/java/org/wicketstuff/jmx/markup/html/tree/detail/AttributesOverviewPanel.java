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

import javax.management.MBeanAttributeInfo;
import javax.management.ObjectName;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.wicketstuff.jmx.util.JmxMBeanWrapper;
import org.wicketstuff.jmx.util.JmxUtil;

/**
 * Renders all attributes of an {@link ObjectName}
 * 
 * @author Gerolf Seitz
 * 
 */
public class AttributesOverviewPanel extends DetailPanel
{
	private static final long serialVersionUID = 1L;

	/**
	 * @param id
	 *            the id of the panel
	 * @param bean
	 *            the wrapper of an {@link ObjectName}
	 */
	public AttributesOverviewPanel(String id, JmxMBeanWrapper bean)
	{
		super(id);

		RepeatingView view = new RepeatingView("attributes");
		add(view);
		for (MBeanAttributeInfo attribute : bean.getAttributes())
		{
			WebMarkupContainer row = new WebMarkupContainer(view.newChildId());
			row.add(new Label("name", attribute.getName()));
			row.add(JmxUtil.getEditor("editor", bean, attribute));
			view.add(row);
		}
	}
}
