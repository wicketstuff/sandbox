package org.wicketstuff.pickwick.frontend.pages;

import org.apache.wicket.PageParameters;
import org.wicketstuff.pickwick.frontend.panel.FolderTreePanel;
import org.wicketstuff.pickwick.frontend.panel.SequenceGridPanel;

/**
 * Page to display a sequence with image thumbnails
 * 
 * @author <a href="mailto:jbq@apache.org">Jean-Baptiste Quenot</a>
 */
public class SequencePage extends FrontendBasePage {
	public SequencePage(PageParameters parameters) {
		String uri = parameters.getString("uri");
		if (uri == null) {
			uri = "";
		}
		add(new SequenceGridPanel("thumbnails", uri));
		add(new FolderTreePanel("treePanel"));
	}
}
