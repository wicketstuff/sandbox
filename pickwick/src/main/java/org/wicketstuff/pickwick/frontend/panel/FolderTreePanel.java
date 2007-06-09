package org.wicketstuff.pickwick.frontend.panel;

import java.util.Iterator;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;

import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.html.tree.AbstractTree;
import org.apache.wicket.markup.html.tree.Tree;
import org.wicketstuff.pickwick.PickWickApplication;
import org.wicketstuff.pickwick.bean.Folder;
import org.wicketstuff.pickwick.bean.provider.FolderProvider;
import org.wicketstuff.pickwick.frontend.FolderTree;

/**
 * Panel displaying the image folder structure
 * @author Vincent Demay
 *
 */
public class FolderTreePanel extends Panel{

	/**
	 * There isn't any model because model is auto binded using {@link FolderProvider}
	 * @param id
	 */
	public FolderTreePanel(String id) {
		super(id);
		tree = new FolderTree("folderTree", createTreeModel());
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
