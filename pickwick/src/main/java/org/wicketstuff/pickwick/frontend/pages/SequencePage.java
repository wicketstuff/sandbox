package org.wicketstuff.pickwick.frontend.pages;

import org.apache.wicket.PageParameters;
import org.apache.wicket.model.Model;
import org.wicketstuff.pickwick.Utils;
import org.wicketstuff.pickwick.auth.PickwickAuthorization;
import org.wicketstuff.pickwick.auth.PickwickSession;
import org.wicketstuff.pickwick.backend.Settings;
import org.wicketstuff.pickwick.frontend.panel.FolderTreePanel;
import org.wicketstuff.pickwick.frontend.panel.MetaDisplayPanel;
import org.wicketstuff.pickwick.frontend.panel.SequenceGridPanel;

import com.google.inject.Inject;

/**
 * Page to display a sequence with image thumbnails
 * 
 * @author <a href="mailto:jbq@apache.org">Jean-Baptiste Quenot</a>
 */
public class SequencePage extends BasePage {
	
	@Inject
	Settings settings;
	
	public SequencePage(PageParameters parameters) {
		super();
		String uri = Utils.getUri(parameters);
		//check user authorisation
		if(uri != ""){
			PickwickAuthorization.check(settings.getImageDirectoryRoot() + "/" + uri, PickwickSession.get());
		}
		add(new MetaDisplayPanel("meta", uri));
		add(new SequenceGridPanel("thumbnails", new Model(uri)));
		add(new FolderTreePanel("treePanel"));
	}
}
