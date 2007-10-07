package org.wicketstuff.pickwick.frontend.pages;

import org.apache.wicket.PageParameters;
import org.wicketstuff.pickwick.backend.Settings;
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
		super();
		add(new GmapLocalizedPanel("map"));
	}
}
