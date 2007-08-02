package org.wicketstuff.pickwick.frontend.panel;

import java.util.Iterator;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

import org.apache.wicket.MarkupContainer;
import org.apache.wicket.PageParameters;
import org.apache.wicket.extensions.markup.html.tree.Tree;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.html.tree.AbstractTree;
import org.wicketstuff.pickwick.PickwickApplication;
import org.wicketstuff.pickwick.auth.PickwickSession;
import org.wicketstuff.pickwick.backend.ImageUtils;
import org.wicketstuff.pickwick.bean.Folder;
import org.wicketstuff.pickwick.frontend.FolderTree;
import org.wicketstuff.pickwick.frontend.pages.SequencePage;

import com.google.inject.Inject;

/**
 * Panel displaying the image folder structure
 * @author Vincent Demay
 *
 */
public class FolderTreePanel extends Panel{
	@Inject
	private ImageUtils imageUtils;
	
	private Tree tree;

	/**
	 * There isn't any model because model is auto binded using {@link FolderProvider}
	 * @param id
	 */
	public FolderTreePanel(String id) {
		super(id);

		tree = new FolderTree("folderTree", createTreeModel()) {

			@Override
			protected MarkupContainer newNodeLink(MarkupContainer parent, String id, TreeNode node) {
				return FolderTreePanel.this.newNodeLink(parent, id, node);
			}
		};

		add(tree);
	}
	
	public MarkupContainer newNodeLink(MarkupContainer parent, String id, TreeNode node) {		
		PageParameters params = new PageParameters();
		Folder folder = (Folder)((DefaultMutableTreeNode)node).getUserObject();
		BookmarkablePageLink nodeLink = new BookmarkablePageLink(id, SequencePage.class, params);
		params.add("uri", imageUtils.getRelativePath(folder.getFile()));
		return nodeLink;
	}

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
		return convertToTreeModel(imageUtils.getFolderFor(PickwickSession.get().getUser().getRole()));
	}
	
	protected TreeModel convertToTreeModel(Folder folder)
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
			if (folder.getSubFolders() != null){
				add(child, folder.getSubFolders());
			}
		}
	}

}
