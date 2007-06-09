package org.wicketstuff.pickwick.frontend;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

import org.apache.wicket.PageParameters;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.tree.Tree;
import org.wicketstuff.pickwick.PickWickApplication;
import org.wicketstuff.pickwick.bean.Folder;
import org.wicketstuff.pickwick.frontend.pages.ImagePage;
import org.wicketstuff.pickwick.frontend.pages.SequencePage;

/**
 * A folderTree representing the image directory
 * @author Vincent Demay
 *
 * 
 * TODO : add some methods to control click on item and current page
 */
public class FolderTree extends Tree{

	public FolderTree(String id, TreeModel model) {
		super(id, model);
	}
	
	protected String renderNode(TreeNode node) {
		Folder folder = (Folder) ((DefaultMutableTreeNode) node).getUserObject();
		return folder.getName();
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
	
	@Override
	protected void populateTreeItem(final WebMarkupContainer item, int level) {
		super.populateTreeItem(item, level);
		item.add(new AjaxEventBehavior("onCLick"){

			@Override
			protected void onEvent(AjaxRequestTarget target) {
				Folder folder = (Folder)((DefaultMutableTreeNode)item.getModelObject()).getUserObject();
				
				target.appendJavascript("window.location='/"+ PickWickApplication.SEQUENCE_PAGE_PATH + folder.getPath() + "';");
			}
			
		});
	}

}
