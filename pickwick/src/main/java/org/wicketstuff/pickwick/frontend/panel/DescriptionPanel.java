package org.wicketstuff.pickwick.frontend.panel;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.wicketstuff.pickwick.backend.Settings;
import org.wicketstuff.pickwick.bean.Folder;
import org.wicketstuff.pickwick.bean.Sequence;
import org.wicketstuff.pickwick.frontend.pages.SequencePage;

import com.google.inject.Inject;

/**
 * Panel displaying the metadata for an image
 * @author Vincent Demay
 *
 */
public class DescriptionPanel extends Panel{

	@Inject
	Settings settings;

	public DescriptionPanel(String id, Sequence sequence, Folder folder) {
		super(id);
		String title = sequence.getTitle();
		if (title == null){
			title = folder.getFile().getName();
		}
		
		add(new Label("title", new Model(title)));
		add(new Label("description", new Model(sequence.getDescription())));
		PageParameters params = new PageParameters();
		params.add("uri", folder.getFile().getAbsolutePath().substring(settings.getImageDirectoryRoot().getAbsolutePath().length() + 1));
		add(new BookmarkablePageLink("link", SequencePage.class, params));
		
	
	}


}
