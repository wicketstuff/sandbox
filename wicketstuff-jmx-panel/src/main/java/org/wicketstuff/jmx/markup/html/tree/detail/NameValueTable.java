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

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.Model;
import org.wicketstuff.jmx.util.JmxMBeanWrapper;

/**
 * This panel is used to display properties (key/value) in a table.
 * 
 * @author Gerolf Seitz
 * 
 */
public abstract class NameValueTable extends Panel
{
	private static final long serialVersionUID = 1L;

	public static final String SECTION = "section__";

	public NameValueTable(String id, JmxMBeanWrapper bean)
	{
		super(id);
		RepeatingView view = new RepeatingView("info");
		add(view);
		Map<String, Object> props = new LinkedHashMap<String, Object>();
		populateInfoProperties(bean, props);
		for (String key : props.keySet())
		{
			WebMarkupContainer row = new WebMarkupContainer(view.newChildId());
			String value = props.get(key).toString();
			if (key.startsWith(SECTION))
			{
				key = value;
				value = "";
				row.add(new AttributeModifier("class", true, new Model("section")));
			}
			row.add(new Label("name", key.substring((key.lastIndexOf(']') + 1))));
			row.add(new Label("value", value));
			view.add(row);
		}
	}

	protected abstract void populateInfoProperties(JmxMBeanWrapper bean, Map<String, Object> props);
}
