package org.wicketstuff.pickwick.backend.pages;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

import org.apache.wicket.MarkupContainer;
import org.apache.wicket.PageParameters;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.panel.EmptyPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.wicketstuff.dojo.markup.html.DojoLink;
import org.wicketstuff.pickwick.backend.ImageUtils;
import org.wicketstuff.pickwick.backend.panel.SequencePropertiesPanel;
import org.wicketstuff.pickwick.bean.Folder;
import org.wicketstuff.pickwick.frontend.panel.FolderTreePanel;

import com.google.inject.Inject;

public class SequenceEditPage extends WebPage {
	
	@Inject
	private transient ImageUtils imageUtils = new ImageUtils();
	
	private Panel panel = null;
	
	public SequenceEditPage(PageParameters params) {
		
		panel = new EmptyPanel("properties");
		panel.setOutputMarkupId(true);
		
		add(new FolderTreePanel("folders"){
			@Override
			public MarkupContainer newNodeLink(MarkupContainer parent, String id, TreeNode node) {
				final Folder folder = (Folder)((DefaultMutableTreeNode)node).getUserObject();
				DojoLink nodeLink = new DojoLink(id){

					@Override
					public void onClick(AjaxRequestTarget target) {
						SequencePropertiesPanel newPanel =  new SequencePropertiesPanel("properties", folder.getFile() + "/_sequence.xml");
						newPanel.setOutputMarkupId(true);
						panel.replaceWith(newPanel);
						panel = newPanel;
						target.addComponent(panel);
						
					}
					
				};

				return nodeLink;
			}
		});
		
		add(panel);
		
	}
}
