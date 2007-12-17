package org.wicketstuff.pickwick.frontend;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

import org.apache.wicket.ResourceReference;
import org.apache.wicket.extensions.markup.html.tree.Tree;
import org.wicketstuff.pickwick.backend.ImageUtils;
import org.wicketstuff.pickwick.bean.DisplaySequence;
import org.wicketstuff.pickwick.bean.Folder;
import org.wicketstuff.pickwick.bean.Sequence;
import org.wicketstuff.pickwick.frontend.pages.BasePage;

/**
 * A folderTree representing the image directory
 * @author Vincent Demay
 *
 * 
 * TODO : add some methods to control click on item and current page
 */
public class FolderTree extends Tree {
	
	public FolderTree(String id, TreeModel model) {
		super(id, model);
	}
	
	protected String renderNode(TreeNode node) {
		Folder folder = (Folder) ((DefaultMutableTreeNode) node).getUserObject();
		Sequence sequence = ImageUtils.readSequence(folder.getFile());
		return new DisplaySequence(sequence, folder.getFile()).getTitle();
	}
	
	@Override
	protected ResourceReference getFolderClosed() {
		return new ResourceReference(FolderTree.class, "folder-closed.png");
	}

	@Override
	protected ResourceReference getFolderOpen() {
		return new ResourceReference(FolderTree.class, "folder-open.png");
	}

	@Override
	protected ResourceReference getNodeIcon(TreeNode node) {
		return getFolderClosed();
	}

	protected ResourceReference getCSS()
	{
		return new ResourceReference(BasePage.class, "css/tree.css");
	}
	
	
}
