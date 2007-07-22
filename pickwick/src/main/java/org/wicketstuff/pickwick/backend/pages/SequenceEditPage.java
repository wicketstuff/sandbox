package org.wicketstuff.pickwick.backend.pages;

import java.io.File;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.PageParameters;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.wicketstuff.dojo.markup.html.DojoLink;
import org.wicketstuff.dojo.markup.html.container.DojoSimpleContainer;
import org.wicketstuff.dojo.markup.html.container.layout.DojoLayoutContainer;
import org.wicketstuff.dojo.markup.html.container.layout.DojoLayoutContainer.Position;
import org.wicketstuff.pickwick.backend.panel.SequencePropertiesPanel;
import org.wicketstuff.pickwick.bean.Folder;
import org.wicketstuff.pickwick.frontend.pages.BaseSequencePage;
import org.wicketstuff.pickwick.frontend.panel.FolderTreePanel;

public class SequenceEditPage extends BaseSequencePage {
	SequencePropertiesPanel panel;

	private FolderTreePanel tree;

	public SequenceEditPage(PageParameters params) {
		super(params);

		DojoLayoutContainer layout;
		addOnClient(layout = new DojoLayoutContainer("mainArea"));
		DojoSimpleContainer s1 = new DojoSimpleContainer("form");
		layout.add(s1, Position.Client);

		DojoSimpleContainer s2 = new DojoSimpleContainer("tree");
		s2.setWidth("250px");
		layout.add(s2, Position.Left);

		File imageDirectory = null;
		if (uri != null)
			imageDirectory = imageUtils.toFile(uri);

		panel = new SequencePropertiesPanel("properties") {
			@Override
			public void onSave(AjaxRequestTarget target) {
				readSequence();
				target.addComponent(SequenceEditPage.this.getTree());
				target.addComponent(meta);
			}
		};
		panel.setImageDirectory(imageDirectory);
		panel.setOutputMarkupId(true);

		tree = new FolderTreePanel("folders") {
			@Override
			public MarkupContainer newNodeLink(MarkupContainer parent, String id, TreeNode node) {
				final Folder folder = (Folder) ((DefaultMutableTreeNode) node).getUserObject();
				DojoLink nodeLink = new DojoLink(id) {

					@Override
					public void onClick(AjaxRequestTarget target) {
						panel.setImageDirectory(folder.getFile());
						target.addComponent(panel);
					}
				};

				return nodeLink;
			}
		};
		tree.setOutputMarkupId(true);

		s2.add(tree);

		s1.add(panel);

	}

	protected Component getTree() {
		return tree;
	}
}
