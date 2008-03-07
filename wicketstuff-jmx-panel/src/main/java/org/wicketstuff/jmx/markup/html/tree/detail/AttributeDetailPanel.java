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

import java.util.Map;

import javax.management.Descriptor;
import javax.management.MBeanAttributeInfo;

import org.wicketstuff.jmx.markup.html.tree.DetailPanel;
import org.wicketstuff.jmx.util.JmxMBeanWrapper;

/**
 * Renders a panel with containing details about an {@link MBeanAttributeInfo}
 * 
 * @author Gerolf Seitz
 * 
 */
public class AttributeDetailPanel extends DetailPanel
{

	private static final long serialVersionUID = 1L;

	public AttributeDetailPanel(String id, JmxMBeanWrapper bean, final MBeanAttributeInfo attribute)
	{
		super(id);
		add(new NameValueTable("attributeInfo", bean)
		{
			private static final long serialVersionUID = 1L;

			@Override
			protected void populateInfoProperties(JmxMBeanWrapper bean, Map props)
			{
				props.put("Name", attribute.getName());
				props.put("Description", attribute.getDescription());
				props.put("Readable", attribute.isReadable());
				props.put("Writable", attribute.isWritable());
				props.put("Is", attribute.isIs());
				props.put("Type", attribute.getType());
			}
		});

		add(new NameValueTable("descriptor", bean)
		{
			private static final long serialVersionUID = 1L;

			@Override
			protected void populateInfoProperties(JmxMBeanWrapper bean, Map props)
			{
				Descriptor desc = attribute.getDescriptor();
				String[] fieldNames = desc.getFieldNames();
				Object[] fieldValues = desc.getFieldValues(fieldNames);
				for (int i = 0; i < fieldNames.length; i++)
				{
					props.put(fieldNames[i], fieldValues[i]);
				}
			}

			@Override
			public boolean isVisible()
			{
				String[] fields = attribute.getDescriptor().getFieldNames();
				return fields != null && fields.length > 0;
			}
		});
	}
}
