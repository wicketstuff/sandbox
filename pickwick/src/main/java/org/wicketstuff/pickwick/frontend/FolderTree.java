package org.wicketstuff.pickwick.frontend;

import java.io.File;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

import org.apache.wicket.ResourceReference;
import org.apache.wicket.extensions.markup.html.tree.Tree;
import org.apache.wicket.model.CompoundPropertyModel;
import org.wicketstuff.pickwick.backend.ImageUtils;
import org.wicketstuff.pickwick.bean.Folder;
import org.wicketstuff.pickwick.bean.Sequence;
import org.wicketstuff.pickwick.frontend.pages.DisplaySequence;
import org.wicketstuff.pickwick.frontend.pages.BasePage;

import com.google.inject.Inject;

/**
 * A folderTree representing the image directory
 * @author Vincent Demay
 *
 * 
 * TODO : add some methods to control click on item and current page
 */
public class FolderTree extends Tree {
	@Inject
	private ImageUtils imageUtils;
	
	public FolderTree(String id, TreeModel model) {
		super(id, model);
	}
	
	protected String renderNode(TreeNode node) {
		Folder folder = (Folder) ((DefaultMutableTreeNode) node).getUserObject();
		Sequence sequence = imageUtils.readSequence(folder.getFile());
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
