package org.wicketstuff.pickwick.backend.pages;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.WebPage;
import org.wicketstuff.pickwick.backend.panel.MetadataPropertiesPanel;

public class MetadataViewPage extends WebPage{

		public MetadataViewPage(PageParameters params) {
			add(new MetadataPropertiesPanel("metadata",params.getString("uri")));
		}
}
