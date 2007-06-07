package org.wicketstuff.pickwick.frontend.panel;

import java.util.Iterator;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.html.tree.AbstractTree;
import org.apache.wicket.markup.html.tree.Tree;
import org.wicketstuff.pickwick.PickWickApplication;
import org.wicketstuff.pickwick.bean.Folder;
import org.wicketstuff.pickwick.bean.provider.FolderProvider;

/**
 * Panel displaying the image folder structure
 * @author Vincent Demay
 * TODO : add some methods to control click on item and current page
 * TODO : change default tree images
 *
 */
public class FolderTreePanel extends Panel{

	/**
	 * There isn't any model because model is auto binded using {@link FolderProvider}
	 * @param id
	 */
	public FolderTreePanel(String id) {
		super(id);
		tree = new Tree("folderTree", createTreeModel()) {
			protected String renderNode(TreeNode node) {
				Folder bean = (Folder) ((DefaultMutableTreeNode) node)
						.getUserObject();
				return bean.getName();
			}
		};
		add(tree);
	}
	private Tree tree;

	protected AbstractTree getTree() {
		return tree;
	}
	
	/**
	 * Creates the model that feeds the tree.
	 * @return
	 * 		New instance of tree model.
	 */
	protected TreeModel createTreeModel() 
	{
		FolderProvider folderProvider = new FolderProvider(((PickWickApplication)getApplication()).getSettings());
		return convertToTreeModel(folderProvider.getFolder());
	}
	
	private TreeModel convertToTreeModel(Folder folder)
	{
		TreeModel model = null;
		DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(folder);
		add(rootNode, folder.getSubFolders());
		model = new DefaultTreeModel(rootNode);		
		return model;
	}

	private void add(DefaultMutableTreeNode parent, List<Folder> sub)
	{
		for (Iterator<Folder> folderIt = sub.iterator(); folderIt.hasNext();)
		{
			Folder folder = folderIt.next();
			DefaultMutableTreeNode child = new DefaultMutableTreeNode(folder);
			parent.add(child);
			add(child, folder.getSubFolders());
		}
	}

}
