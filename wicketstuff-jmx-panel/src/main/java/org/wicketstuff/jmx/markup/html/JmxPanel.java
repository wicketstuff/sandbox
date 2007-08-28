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
package org.wicketstuff.jmx.markup.html;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javax.management.MBeanAttributeInfo;
import javax.management.MBeanOperationInfo;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.wicketstuff.jmx.markup.html.table.IDomainFilter;
import org.wicketstuff.jmx.markup.html.table.JmxTreeNode;
import org.wicketstuff.jmx.markup.html.table.JmxTreeTable;
import org.wicketstuff.jmx.util.JmxMBeanServerWrapper;
import org.wicketstuff.jmx.util.JmxMBeanWrapper;

/**
 * @author Gerolf
 * 
 */
public class JmxPanel extends Panel
{

	private static final long serialVersionUID = 1L;

	private ObjectName appBeanName;

	private JmxMBeanServerWrapper serverModel = new JmxMBeanServerWrapper();

	public JmxPanel(String id)
	{
		super(id);
		add(new JmxTreeTable("jmxBeanTable", createTreeModel(appBeanName)));
	}

	@SuppressWarnings("unchecked")
	private TreeModel createTreeModel(ObjectName name)
	{
		TreeModel model = null;
		DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(new Model("ROOT"));
		String[] domains = serverModel.getServer().getDomains();
		Arrays.sort(domains);

		// process all available domains
		for (int i = 0; i < domains.length; i++)
		{
			// skip unwanted domains
			if (!getDomainFilter().accept(domains[i]))
			{
				continue;
			}
			// create domain tree node and add to root
			JmxTreeNode domain = new JmxTreeNode(domains[i], null);
			rootNode.add(domain);
			// find all MBeans in the current domain
			List<ObjectName> mBeans = new ArrayList<ObjectName>(serverModel.getServer().queryNames(
					getObjectName(domains[i] + ":*"), null));
			Collections.sort(mBeans, new Comparator<ObjectName>()
			{
				public int compare(ObjectName object1, ObjectName object2)
				{
					// (1) Compare domains
					//
					int domainValue = object1.getDomain().compareTo(object2.getDomain());
					if (domainValue != 0)
						return domainValue;

					// (2) Compare "type=" keys
					//
					// Within a given domain, all names with missing or empty
					// "type="
					// come before all names with non-empty type.
					//
					// When both types are missing or empty, canonical-name
					// ordering
					// applies which is a total order.
					//
					String thisTypeKey = object1.getKeyProperty("type");
					String anotherTypeKey = object2.getKeyProperty("type");
					if (thisTypeKey == null)
						thisTypeKey = "";
					if (anotherTypeKey == null)
						anotherTypeKey = "";
					int typeKeyValue = thisTypeKey.compareTo(anotherTypeKey);
					if (typeKeyValue != 0)
						return typeKeyValue;

					// (3) Compare canonical names
					//
					return object1.getCanonicalName().compareTo(object2.getCanonicalName());
				}
			});

			// add all MBeans to the current domain
			for (Iterator iter = mBeans.iterator(); iter.hasNext();)
			{
				add(domain, (ObjectName)iter.next());
			}
		}
		model = new DefaultTreeModel(rootNode);
		return model;
	}

	private void add(JmxTreeNode parent, ObjectName name)
	{
		String n = name.getDomain() + ":" + name.getKeyPropertyListString();

		JmxTreeNode current = parent;
		String[] parts = n.split(",");

		// create nodes for all subpaths
		for (int level = 0; level < parts.length; level++)
		{
			ObjectName node = getObjectName(getPath(parts, level));
			// check whether node is already a child of the current node
			JmxTreeNode child = current.findNodeWithObjectName(node);
			if (child == null)
			{
				// node was not found, create a new node
				JmxMBeanWrapper mbean = new JmxMBeanWrapper(node, serverModel);
				child = new JmxTreeNode(node, mbean);
				current.add(child);
				// create nodes for all attributes
				for (MBeanAttributeInfo attribute : mbean.getAttributes())
				{
					child.add(new JmxTreeNode(attribute, mbean));
				}

				// create nodes for all operations
				for (MBeanOperationInfo operation : mbean.getOperations())
				{
					child.add(new JmxTreeNode(operation, mbean));
				}
			}
			current = child;
		}
	}

	private String getPath(final String path[], int depth)
	{
		String p = path[0];
		for (int i = 0; i < depth; i++)
		{
			p += "," + path[i + 1];
		}
		return p;
	}

	/**
	 * Override this method to provide a different IDomainFilter. The default
	 * implementation returns {@link IDomainFilter#CONTAINING_APPLICATION}.
	 * 
	 * @return an IDomainFilterImplementation.
	 */
	protected IDomainFilter getDomainFilter()
	{
		return IDomainFilter.CONTAINING_APPLICATION;
	}

	private ObjectName getObjectName(String s)
	{
		try
		{
			return new ObjectName(s);
		}
		catch (MalformedObjectNameException e)
		{
			throw new WicketRuntimeException(e);
		}
		catch (NullPointerException e)
		{
			throw new WicketRuntimeException(e);
		}
	}
}
