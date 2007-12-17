package org.wicketstuff.pickwick.frontend.pages;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.panel.Panel;
import org.wicketstuff.pickwick.backend.Settings;
import org.wicketstuff.pickwick.frontend.panel.FolderTreePanel;
import org.wicketstuff.pickwick.frontend.panel.GmapLocalizedPanel;

import com.google.inject.Inject;

/**
 * Page to display a sequence with image thumbnails
 * 
 * @author <a href="mailto:jbq@apache.org">Jean-Baptiste Quenot</a>
 */
public class GmapPage extends BasePage {
	
	@Inject
	Settings settings;
	
	public GmapPage(PageParameters parameters) {
		super(null);
		add(new GmapLocalizedPanel("map"));
	}
	
	@Override
	protected Panel getWestPanel(String id) {
		return new FolderTreePanel(id);
	}
}
