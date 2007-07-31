package org.wicketstuff.pickwick.frontend.pages;

import org.apache.wicket.PageParameters;
import org.apache.wicket.model.Model;
import org.wicketstuff.pickwick.Utils;
import org.wicketstuff.pickwick.frontend.panel.FolderTreePanel;
import org.wicketstuff.pickwick.frontend.panel.MetaDisplayPanel;
import org.wicketstuff.pickwick.frontend.panel.SequenceGridPanel;

/**
 * Page to display a sequence with image thumbnails
 * 
 * @author <a href="mailto:jbq@apache.org">Jean-Baptiste Quenot</a>
 */
public class SequencePage extends BasePage {
	public SequencePage(PageParameters parameters) {
		super();
		String uri = Utils.getUri(parameters);
		add(new MetaDisplayPanel("meta", uri));
		add(new SequenceGridPanel("thumbnails", new Model(uri)));
		add(new FolderTreePanel("treePanel"));
	}
}
