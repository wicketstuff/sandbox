package org.wicketstuff.pickwick.frontend.panel;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.wicketstuff.pickwick.backend.ImageUtils;
import org.wicketstuff.pickwick.backend.Settings;
import org.wicketstuff.pickwick.bean.DisplaySequence;
import org.wicketstuff.pickwick.bean.Sequence;
import org.wicketstuff.pickwick.frontend.pages.DateModel;
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
	@Inject
	protected ImageUtils imageUtils;
	
	private String uri;

	public DescriptionPanel(String id, String uri) {
		super(id);
		
		if (uri != null) {
			// BackendLandingPage does not pass parameters
			try {
				uri = URLDecoder.decode(uri, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				// Ignore
			}
		}
		this.uri = uri;

		add(new Label("date", new DateModel(this)));
		add(new Label("title"));
		setOutputMarkupId(true);
		add(new Label("description"));
		PageParameters params = new PageParameters();
		params.add("uri", uri);
		add(new BookmarkablePageLink("link", SequencePage.class, params));
	}
	

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
			Sequence sequence = ImageUtils.readSequence(imageDir);
      setDefaultModel( new CompoundPropertyModel( new DisplaySequence( sequence, imageDir ) ) );
//			setModel(new CompoundPropertyModel(new DisplaySequence(sequence, imageDir)));
		}
	}


}
