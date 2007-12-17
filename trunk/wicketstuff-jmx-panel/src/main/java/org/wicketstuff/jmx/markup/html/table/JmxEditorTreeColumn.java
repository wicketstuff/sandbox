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

import javax.management.MBeanAttributeInfo;
import javax.management.MBeanOperationInfo;
import javax.swing.tree.TreeNode;

import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.extensions.markup.html.tree.table.AbstractRenderableColumn;
import org.apache.wicket.extensions.markup.html.tree.table.ColumnLocation;
import org.apache.wicket.extensions.markup.html.tree.table.IRenderable;
import org.apache.wicket.markup.html.basic.Label;
import org.wicketstuff.jmx.util.JmxUtil;

/**
 * Column that renders either a label for a readonly attribute, an
 * AjaxEditable*Label for a writeable attribute, or panel with input fields for
 * operation parameters.
 * 
 * @author Gerolf Seitz
 * 
 */
public class JmxEditorTreeColumn extends AbstractRenderableColumn
{

	private static final long serialVersionUID = 1L;

	public JmxEditorTreeColumn(ColumnLocation location, String header)
	{
		super(location, header);
	}

	@Override
	public IRenderable newCell(TreeNode node, int level)
	{
		return null;
	}

	@Override
	public Component newCell(MarkupContainer parent, String id, final TreeNode node, int level)
	{
		Component component;
		if (!(node instanceof JmxTreeNode))
		{
			component = new Label(id);
		}
		else
		{
			JmxTreeNode n = (JmxTreeNode)node;
			Object object = n.getUserObject();

			if (object instanceof MBeanAttributeInfo)
			{
				component = JmxUtil.getEditor(id, n.getJmxMBeanWrapper(),
						(MBeanAttributeInfo)object);
			}
			else if (object instanceof MBeanOperationInfo)
			{
				component = new OperationInvocationPanel(id, n.getJmxMBeanWrapper(),
						(MBeanOperationInfo)object);
			}
			else
			{
				component = new Label(id);
			}
		}
		parent.add(component);
		return component;
	}

	@Override
	public String getNodeValue(TreeNode node)
	{
		return null;
	}
}
