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
import javax.management.MBeanParameterInfo;
import javax.management.ObjectName;
import javax.swing.tree.TreeNode;

import org.apache.wicket.extensions.markup.html.tree.table.AbstractTreeColumn;
import org.apache.wicket.extensions.markup.html.tree.table.ColumnLocation;
import org.apache.wicket.util.string.Strings;

/**
 * This column renders the label of either an ObjectName, MBeanAttributeInfo or
 * MBeanOperationInfo object.
 * 
 * @author Gerolf Seitz
 * 
 */
public class JmxTreeColumn extends AbstractTreeColumn
{

	private static final long serialVersionUID = 1L;

	public JmxTreeColumn(ColumnLocation location, String header)
	{
		super(location, header);
	}
	
	@Override
	public String renderNode(TreeNode node)
	{
		JmxTreeNode n = (JmxTreeNode)node;
		Object obj = n.getUserObject();
		if (obj instanceof MBeanAttributeInfo)
		{
			return ((MBeanAttributeInfo)obj).getName();
		}
		else if (obj instanceof MBeanOperationInfo)
		{
			StringBuffer sb = new StringBuffer();
			MBeanOperationInfo op = (MBeanOperationInfo)obj;
			sb.append(op.getName());
			sb.append(" (");
			if (op.getSignature().length > 0)
			{
				for (MBeanParameterInfo param : op.getSignature())
				{
					sb.append(param.getType());
					sb.append(" ");
					sb.append(param.getName());
					sb.append((", "));
				}
				sb.delete(sb.length() - 2, sb.length());
			}
			sb.append(")");
			return sb.toString();
		}
		else if (obj instanceof ObjectName)
		{
			return Strings.afterLast(((ObjectName)obj).getKeyPropertyListString(), '=');
		}
		else
		{
			return node.toString();
		}
	}
}
