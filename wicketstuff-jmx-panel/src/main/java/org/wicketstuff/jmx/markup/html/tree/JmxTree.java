package org.wicketstuff.jmx.markup.html.tree;

import java.io.Serializable;

import javax.management.MBeanAttributeInfo;
import javax.management.MBeanFeatureInfo;
import javax.management.MBeanOperationInfo;
import javax.management.ObjectName;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.panel.EmptyPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.html.tree.BaseTree;
import org.apache.wicket.markup.html.tree.ITreeState;
import org.apache.wicket.markup.html.tree.LinkIconPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.lang.Objects;
import org.apache.wicket.util.string.Strings;
import org.wicketstuff.jmx.markup.html.JmxPanel;
import org.wicketstuff.jmx.markup.html.table.JmxTreeNode;
import org.wicketstuff.jmx.markup.html.tree.detail.AttributeDetailPanel;
import org.wicketstuff.jmx.markup.html.tree.detail.AttributesOverviewPanel;
import org.wicketstuff.jmx.markup.html.tree.detail.ObjectNameDetailPanel;
import org.wicketstuff.jmx.markup.html.tree.detail.OperationDetailPanel;
import org.wicketstuff.jmx.markup.html.tree.detail.OperationsOverviewPanel;

/**
 * @author Gerolf Seitz
 * 
 */
public class JmxTree extends BaseTree
{
	private static final long serialVersionUID = 1L;

	private Panel detailPanel;

	public JmxTree(String id, TreeModel model, Panel detailPanel)
	{
		super(id, new Model((Serializable)model));
		getTreeState().collapseAll();
		setRootLess(true);
		this.detailPanel = detailPanel;
		add(new AttributeModifier("class", true, new Model("jmxTree")));
	}

	@Override
	protected Component newNodeComponent(String id, IModel model)
	{
		return new LinkIconPanel(id, model, this)
		{
			private static final long serialVersionUID = 1L;

			@Override
			protected ResourceReference getResourceItemLeaf(Object node)
			{
				Object obj = ((DefaultMutableTreeNode)node).getUserObject();
				if (obj instanceof MBeanAttributeInfo)
				{
					return JmxPanel.ATTRIBUTE_ICON;
				}
				else if (obj instanceof MBeanOperationInfo)
				{
					return JmxPanel.OPERATION_ICON;
				}
				return super.getResourceItemLeaf(node);
			}

			@Override
			protected ResourceReference getResourceFolderOpen(Object node)
			{
				Object obj = ((DefaultMutableTreeNode)node).getUserObject();
				if ("operations".equals(obj.toString()))
				{
					return JmxPanel.OPERATIONS_ICON;
				}
				else if ("attributes".equals(obj.toString()))
				{
					return JmxPanel.ATTRIBUTES_ICON;
				}
				return super.getResourceFolderOpen(node);
			}

			@Override
			protected ResourceReference getResourceFolderClosed(Object node)
			{
				Object obj = ((DefaultMutableTreeNode)node).getUserObject();
				if ("operations".equals(obj.toString()))
				{
					return JmxPanel.OPERATIONS_ICON;
				}
				else if ("attributes".equals(obj.toString()))
				{
					return JmxPanel.ATTRIBUTES_ICON;
				}
				return super.getResourceFolderClosed(node);
			}

			@Override
			protected Component newContentComponent(String componentId, BaseTree tree, IModel model)
			{
				DefaultMutableTreeNode node = (DefaultMutableTreeNode)model.getObject();
				Object obj = node.getUserObject();
				IModel labelModel = null;
				if (obj instanceof MBeanFeatureInfo)
				{
					labelModel = new Model(((MBeanFeatureInfo)obj).getName());
				}
				else if (obj instanceof ObjectName)
				{
					labelModel = new Model(Strings.afterLast(((ObjectName)obj)
							.getKeyPropertyListString(), '='));
				}
				else
				{
					labelModel = new Model(obj.toString());
				}
				return super.newContentComponent(componentId, tree, labelModel);
			}

			@Override
			protected void onNodeLinkClicked(Object node, BaseTree tree, AjaxRequestTarget target)
			{
				ITreeState state = tree.getTreeState();
				if (state.isNodeExpanded(node) && state.isNodeSelected(node))
				{
					state.collapseNode(node);
				}
				else
				{
					state.expandNode(node);
				}
				state.selectNode(node, true);


				JmxTreeNode jmxNode = (JmxTreeNode)node;
				Object object = jmxNode.getUserObject();
				Panel panel = null;
				if (object instanceof ObjectName)
				{
					// DetailPanel.forObjectName
					panel = new ObjectNameDetailPanel(detailPanel.getId(), jmxNode
							.getJmxMBeanWrapper());
				}
				else if (object instanceof MBeanAttributeInfo)
				{
					// DetailPanel.forAttributeDetail
					panel = new AttributeDetailPanel(detailPanel.getId(), jmxNode
							.getJmxMBeanWrapper(), (MBeanAttributeInfo)object);
				}
				else if (object instanceof MBeanOperationInfo)
				{
					panel = new OperationDetailPanel(detailPanel.getId(), jmxNode
							.getJmxMBeanWrapper(), (MBeanOperationInfo)object);
				}
				else if (Objects.isEqual("attributes", object))
				{
					// DetailPanel.forAttributesOverview
					panel = new AttributesOverviewPanel(detailPanel.getId(), jmxNode
							.getJmxMBeanWrapper());
				}
				else if (Objects.isEqual("operations", object))
				{
					panel = new OperationsOverviewPanel(detailPanel.getId(), jmxNode
							.getJmxMBeanWrapper());
				}
				else
				{
					panel = (Panel)new EmptyPanel(detailPanel.getId()).setOutputMarkupId(true);
				}

				detailPanel.replaceWith(panel);
				detailPanel = panel;
				target.addComponent(detailPanel);
				tree.updateTree(target);
			}
		};
	}
}
