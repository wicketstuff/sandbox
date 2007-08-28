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

import java.util.Iterator;

import javax.management.ObjectName;
import javax.swing.tree.DefaultMutableTreeNode;

import org.wicketstuff.jmx.util.JmxMBeanWrapper;


/**
 * TreeNode representing an ObjectName, MBeanAttributeInfo or MBeanOperationInfo
 * object.
 * 
 * @author Gerolf Seitz
 * 
 */
public class JmxTreeNode extends DefaultMutableTreeNode
{

	private static final long serialVersionUID = 1L;

	private JmxMBeanWrapper mbean;

	public JmxTreeNode(Object userObject)
	{
		this(userObject, null);
	}

	public JmxTreeNode(Object userObject, JmxMBeanWrapper mbean)
	{
		super(userObject);
		this.mbean = mbean;
	}

	/**
	 * Checks whether a child node represents a path equal to the given
	 * ObjectName's path.
	 * 
	 * @param name
	 *            The ObjectName to be used in the check.
	 * @return The child node representing the given ObjectName's path or null
	 *         if there is no such child node.
	 * 
	 */
	@SuppressWarnings("unchecked")
	public JmxTreeNode findNodeWithObjectName(ObjectName name)
	{
		if (children == null || !allowsChildren)
		{
			return null;
		}
		for (Iterator iter = children.iterator(); iter.hasNext();)
		{
			JmxTreeNode node = (JmxTreeNode)iter.next();
			Object obj = node.getUserObject();
			if (obj instanceof ObjectName)
			{
				if (((ObjectName)obj).getCanonicalName().equals(name.getCanonicalName()))
				{
					return node;
				}
			}
		}
		return null;
	}

	/**
	 * @return the JmxMBeanWrapper object
	 */
	public JmxMBeanWrapper getJmxMBeanWrapper()
	{
		return mbean;
	}
}
