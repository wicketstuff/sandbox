package org.wicketstuff.pickwick.backend.pages;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.PageParameters;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.panel.EmptyPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.wicketstuff.dojo.markup.html.DojoLink;
import org.wicketstuff.dojo.markup.html.container.DojoSimpleContainer;
import org.wicketstuff.dojo.markup.html.container.layout.DojoLayoutContainer;
import org.wicketstuff.dojo.markup.html.container.layout.DojoLayoutContainer.Position;
import org.wicketstuff.pickwick.backend.ImageUtils;
import org.wicketstuff.pickwick.backend.panel.SequencePropertiesPanel;
import org.wicketstuff.pickwick.bean.Folder;
import org.wicketstuff.pickwick.frontend.panel.FolderTreePanel;

import com.google.inject.Inject;

public class SequenceEditPage extends BackendBasePage {
	
	@Inject
	private transient ImageUtils imageUtils = new ImageUtils();
	
	private Panel panel = null;
	private FolderTreePanel tree = null;
	
	public SequenceEditPage(PageParameters params) {
		
		DojoLayoutContainer layout;
		addOnClient(layout = new DojoLayoutContainer("mainArea"));
		DojoSimpleContainer s1 = new DojoSimpleContainer("form");
		layout.add(s1, Position.Client);
		
		DojoSimpleContainer s2 = new DojoSimpleContainer("tree");
		s2.setWidth("250px");
		layout.add(s2, Position.Left);
		
		panel = new EmptyPanel("properties");
		panel.setOutputMarkupId(true);
		
		tree = new FolderTreePanel("folders"){
			@Override
			public MarkupContainer newNodeLink(MarkupContainer parent, String id, TreeNode node) {
				final Folder folder = (Folder)((DefaultMutableTreeNode)node).getUserObject();
				DojoLink nodeLink = new DojoLink(id){

					@Override
					public void onClick(AjaxRequestTarget target) {
						SequencePropertiesPanel newPanel =  new SequencePropertiesPanel("properties", folder.getFile()){
							@Override
							public void onSave(AjaxRequestTarget target) {
								target.addComponent(SequenceEditPage.this.getTree());
							}
						};
						newPanel.setOutputMarkupId(true);
						panel.replaceWith(newPanel);
						panel = newPanel;
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
