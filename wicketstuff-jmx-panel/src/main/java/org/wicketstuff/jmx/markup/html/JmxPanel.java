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

import org.apache.wicket.ResourceReference;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.markup.html.IHeaderContributor;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.panel.EmptyPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.wicketstuff.jmx.markup.html.table.IDomainFilter;
import org.wicketstuff.jmx.markup.html.table.JmxTreeNode;
import org.wicketstuff.jmx.markup.html.table.JmxTreeTable;
import org.wicketstuff.jmx.markup.html.tree.JmxTree;
import org.wicketstuff.jmx.util.JmxMBeanServerWrapper;
import org.wicketstuff.jmx.util.JmxMBeanWrapper;

/**
 * Use this panel to display a Tree or a TreeTable filled with
 * {@link ObjectName}s and it's operations and attributes.<br/>
 * 
 * By default the Tree implementation is used, but you can tell JmxPanel to use
 * the TreeTable implementation by passing {@link JmxPanelRenderer#TreeTable} to
 * the constructor.
 * 
 * @author Gerolf Seitz
 * 
 */
public class JmxPanel extends Panel implements IHeaderContributor
{
	private static final long serialVersionUID = 1L;

	public static final ResourceReference CSS = new ResourceReference(JmxPanel.class,
			"res/JmxTreeTable.css");

	private JmxMBeanServerWrapper serverModel = new JmxMBeanServerWrapper();

	/**
	 * Constructs the JmxPanel with the Tree implementation.
	 * 
	 * @param id
	 *            the id of the panel
	 */
	public JmxPanel(String id)
	{
		this(id, JmxPanelRenderer.Tree);

	}

	/**
	 * Constructs the JmxPanel and uses the param <code>renderer</code> to
	 * decide which implementation should be used.
	 * 
	 * @param id
	 *            the id of the panel
	 * @param renderer
	 *            indicates which renderer (Tree/TreeTable) should be used.
	 */
	public JmxPanel(String id, JmxPanelRenderer renderer)
	{
		super(id);
		Panel detailPanel = new EmptyPanel("detailPanel");
		add(detailPanel.setOutputMarkupId(true));
		if (JmxPanelRenderer.Tree.equals(renderer))
		{
			add(new JmxTree("jmxBeanTable", createTreeModel(), detailPanel));
		}
		else
		{
			add(new JmxTreeTable("jmxBeanTable", createTreeModel()));
		}
	}

	@SuppressWarnings("unchecked")
	private TreeModel createTreeModel()
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

	/**
	 * This method creates a {@link JmxTreeNode} for the given
	 * {@link ObjectName} and adds it as a child to the "parent" parameter).
	 * Also, nodes for operations and attributes are created.
	 * 
	 * @param parent
	 *            the parent JmxTreeNode
	 * @param name
	 *            the {@link ObjectName} instance
	 */
	private void add(JmxTreeNode parent, ObjectName name)
	{
		String n = name.getDomain() + ":" + name.getKeyPropertyListString();

		JmxTreeNode current = parent;
		String[] parts = n.split(",");

		// create nodes for all subpaths
		for (int level = 0; level < parts.length; level++)
		{
			ObjectName objectName = getObjectName(getPath(parts, level));
			// check whether node is already a child of the current node
			JmxTreeNode child = current.findNodeWithObjectName(objectName);
			if (child == null)
			{
				// node was not found, create a new node
				JmxMBeanWrapper mbean = new JmxMBeanWrapper(objectName, serverModel);
				child = new JmxTreeNode(objectName, mbean);
				current.add(child);

				addAttributes(child, mbean);
				addOperations(child, mbean);
			}
			current = child;
		}
	}

	/**
	 * Adds nodes for operations
	 * 
	 * @param current
	 *            the current node for which operation nodes should be created.
	 * @param mbean
	 *            the wrapper around the current {@link ObjectName}
	 */
	private void addOperations(JmxTreeNode current, JmxMBeanWrapper mbean)
	{
		MBeanOperationInfo[] operations = mbean.getOperations();
		if (operations.length == 0)
		{
			return;
		}

		JmxTreeNode tmp = new JmxTreeNode("operations", mbean);
		current.add(tmp);
		// create nodes for all operations
		for (MBeanOperationInfo operation : operations)
		{
			tmp.add(new JmxTreeNode(operation, mbean));
		}
	}

	/**
	 * Adds nodes for attributes
	 * 
	 * @param current
	 *            the current node for which attribute nodes should be created.
	 * @param mbean
	 *            the wrapper around the current {@link ObjectName}
	 */
	private void addAttributes(JmxTreeNode current, JmxMBeanWrapper mbean)
	{
		MBeanAttributeInfo[] attributes = mbean.getAttributes();
		if (attributes.length == 0)
		{
			return;
		}

		JmxTreeNode tmp = new JmxTreeNode("attributes", mbean);
		current.add(tmp);
		// create nodes for all attributes
		for (MBeanAttributeInfo attribute : mbean.getAttributes())
		{
			tmp.add(new JmxTreeNode(attribute, mbean));
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

	/**
	 * @see org.apache.wicket.markup.html.IHeaderContributor#renderHead(org.apache.wicket.markup.html.IHeaderResponse)
	 */
	public void renderHead(IHeaderResponse response)
	{
		response.renderCSSReference(CSS);
	}
}
