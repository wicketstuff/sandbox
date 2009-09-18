package org.wicketstuff.pickwick.backend.pages;

import java.io.File;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.PageParameters;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.wicketstuff.pickwick.backend.ImageUtils;
import org.wicketstuff.pickwick.backend.panel.SequencePropertiesPanel;
import org.wicketstuff.pickwick.bean.DisplaySequence;
import org.wicketstuff.pickwick.bean.Folder;
import org.wicketstuff.pickwick.bean.Sequence;
import org.wicketstuff.pickwick.frontend.panel.ActionPanel;
import org.wicketstuff.pickwick.frontend.panel.FolderTreePanel;

import com.google.inject.Inject;

public class SequenceEditPage extends BaseAdminPage {
	SequencePropertiesPanel panel;
	
	@Inject
	private ImageUtils imageUtils;

	private FolderTreePanel tree;

	private String uri;
	
	private File imageDirectory;
	
	public SequenceEditPage(PageParameters params) {
		super(params);

		panel = new SequencePropertiesPanel("properties") {
			@Override
			public void onSave(AjaxRequestTarget target) {
				readSequence();
				target.addComponent(SequenceEditPage.this.getTree());
			}
		};
		panel.setImageDirectory(imageDirectory);
		panel.setOutputMarkupId(true);

		add(panel);

	}
	
	@Override
	protected Panel getWestPanel(String id) {
		tree = new FolderTreePanel(id) {
			@Override
			public MarkupContainer newNodeLink(MarkupContainer parent, String id, TreeNode node) {
				final Folder folder = (Folder) ((DefaultMutableTreeNode) node).getUserObject();
				AjaxLink nodeLink = new AjaxLink(id) {

					@Override
					public void onClick(AjaxRequestTarget target) {
						panel.setImageDirectory(folder.getFile());
						target.addComponent(panel);
					}
				};

				return nodeLink;
			}
			
			/**
			 * Creates the model that feeds the tree.
			 * @return
			 * 		New instance of tree model.
			 */
			protected TreeModel createTreeModel() 
			{
				return convertToTreeModel(imageUtils.getFolder());
			}
		};
		tree.setOutputMarkupId(true);
		
		return tree;
	}
	
	@Override
	protected void initPage(PageParameters parameters) {
		uri = parameters.getString("uri");
		if (uri != null){
			imageDirectory = imageUtils.toFile(uri);
		}else{
			imageDirectory = imageUtils.toFile("");
		}
	}

	protected Component getTree() {
		return tree;
	}
	
	public void readSequence() {
		if (uri != null) {
			File imageDir = imageUtils.toFile(uri);
			Sequence sequence = ImageUtils.readSequence(imageDir);
			setDefaultModel(new CompoundPropertyModel(new DisplaySequence(sequence, imageDir)));
		}
	}
}
