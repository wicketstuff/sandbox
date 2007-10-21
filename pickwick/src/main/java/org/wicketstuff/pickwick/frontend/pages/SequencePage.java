package org.wicketstuff.pickwick.frontend.pages;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.wicketstuff.pickwick.Utils;
import org.wicketstuff.pickwick.auth.PickwickAuthorization;
import org.wicketstuff.pickwick.auth.PickwickSession;
import org.wicketstuff.pickwick.backend.Settings;
import org.wicketstuff.pickwick.frontend.panel.DescriptionPanel;
import org.wicketstuff.pickwick.frontend.panel.FolderTreePanel;
import org.wicketstuff.pickwick.frontend.panel.ActionPanel;
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
	
	private String uri;
	
	public SequencePage(PageParameters parameters) {
		super(parameters);
		add(new SequenceGridPanel("thumbnails", new Model(uri)));
	}
	
	@Override
	protected void initPage(PageParameters parameters) {
		super.initPage(parameters);
		uri = Utils.getUri(parameters);
		//check user authorisation
		if(uri != ""){
			PickwickAuthorization.check(settings.getImageDirectoryRoot() + "/" + uri, PickwickSession.get());
		}
	}
	
	@Override
	protected Panel getSouthPanel(String id) {
		return new DescriptionPanel(id, uri);
	}
	
	@Override
	protected Panel getWestPanel(String id) {
		return new FolderTreePanel(id);
	}
	
	@Override
	protected Panel getEastPanel(String id) {
		return new ActionPanel(id, uri);
	}
}
