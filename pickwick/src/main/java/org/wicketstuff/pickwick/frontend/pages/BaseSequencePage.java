package org.wicketstuff.pickwick.frontend.pages;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.util.Date;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wicketstuff.pickwick.auth.PickwickSession;
import org.wicketstuff.pickwick.backend.ImageUtils;
import org.wicketstuff.pickwick.backend.pages.SequenceEditPage;
import org.wicketstuff.pickwick.bean.Sequence;

import com.google.inject.Inject;

/**
 * Page to display a sequence with image thumbnails
 * 
 * @author <a href="mailto:jbq@apache.org">Jean-Baptiste Quenot</a>
 */
public class BaseSequencePage extends BasePage {
	private static final Logger log = LoggerFactory.getLogger(BaseSequencePage.class);

	@Inject
	protected ImageUtils imageUtils;

	protected String uri;

	protected WebMarkupContainer meta;

	/**
	 * Need to read sequence information everytime the page is displayed to
	 * ensure to have uptodate information
	 */
	@Override
	protected void onBeforeRender() {
		super.onBeforeRender();
		readSequence();
	}
	public void readSequence() {
		if (uri != null) {
			File imageDir = imageUtils.toFile(uri);
			Sequence sequence = imageUtils.readSequence(imageDir);
			setModel(new CompoundPropertyModel(new DisplaySequence(sequence, imageDir)));
		}
	}

	public BaseSequencePage(PageParameters parameters) {
		uri = parameters.getString("uri");

		if (uri != null) {
			// BackendLandingPage does not pass parameters
			try {
				uri = URLDecoder.decode(uri, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				// Ignore
			}
		}

		addOnTop(meta = new WebMarkupContainer("meta"));
		meta.add(new Label("date", new DateModel(this)));
		meta.add(new Label("title"));
		meta.setOutputMarkupId(true);
		if (PickwickSession.get().getUser().isAdmin()) {
			meta.add(new BookmarkablePageLink("edit", SequenceEditPage.class, parameters));
		}
	}
}
