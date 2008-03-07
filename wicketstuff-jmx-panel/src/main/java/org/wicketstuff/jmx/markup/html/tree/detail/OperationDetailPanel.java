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

import javax.management.MBeanOperationInfo;
import javax.management.MBeanParameterInfo;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.model.Model;
import org.wicketstuff.jmx.markup.html.tree.DetailPanel;
import org.wicketstuff.jmx.util.JmxMBeanWrapper;

public class OperationDetailPanel extends DetailPanel
{
	private static final long serialVersionUID = 1L;

	public OperationDetailPanel(String id, JmxMBeanWrapper bean, final MBeanOperationInfo operation)
	{
		super(id);
		add(new OperationPanel("operation", bean, operation).add(new AttributeModifier("class",
				true, new Model("operation-last"))));
		add(new NameValueTable("mbeanOperationInfo", bean)
		{
			private static final long serialVersionUID = 1L;

			@Override
			protected void populateInfoProperties(JmxMBeanWrapper bean, Map<String, Object> props)
			{
				props.put(SECTION, "Operation:");
				props.put("Name", operation.getName());
				props.put("Description", operation.getDescription());
				props.put("Impact", impactToString(operation.getImpact()));
				props.put("ReturnType", operation.getReturnType());

				MBeanParameterInfo[] signature = operation.getSignature();
				for (int i = 0; i < signature.length; i++)
				{
					MBeanParameterInfo info = signature[i];
					props.put(SECTION + i, "Parameter-" + i);
					props.put(prependParam(info, "Name"), info.getName());
					props.put(prependParam(info, "Description"), info.getDescription());
					props.put(prependParam(info, "Type"), info.getType());
				}

			}

			private String prependParam(MBeanParameterInfo info, String name)
			{
				return String.format("[%s] %s", info.getName(), name);
			}

			private String impactToString(int impact)
			{
				switch (impact)
				{
					case MBeanOperationInfo.INFO :
						return "INFO";
					case MBeanOperationInfo.ACTION :
						return "ACTION";
					case MBeanOperationInfo.ACTION_INFO :
						return "ACTION_INFO";
					case MBeanOperationInfo.UNKNOWN :
						return "UNKNOWN";
				}
				throw new IllegalArgumentException("invalid impact value");
			}
		});
	}

}
