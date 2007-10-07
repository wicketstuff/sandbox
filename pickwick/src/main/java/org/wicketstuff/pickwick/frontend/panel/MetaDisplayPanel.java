package org.wicketstuff.pickwick.frontend.panel;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.apache.wicket.PageParameters;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.PageLink;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wicketstuff.pickwick.PickwickApplication;
import org.wicketstuff.pickwick.backend.ImageUtils;
import org.wicketstuff.pickwick.backend.pages.SequenceEditPage;
import org.wicketstuff.pickwick.bean.DisplaySequence;
import org.wicketstuff.pickwick.bean.Sequence;
import org.wicketstuff.pickwick.frontend.pages.DateModel;
import org.wicketstuff.pickwick.frontend.pages.DiaporamaPage;
import org.wicketstuff.pickwick.frontend.pages.GmapPage;
import org.wicketstuff.pickwick.frontend.pages.ImagePage;

import com.google.inject.Inject;

/**
 * A panel display meta datas from a folder
 * @author Vincent Demay
 *
 */
public class MetaDisplayPanel extends Panel {

	private static final Logger log = LoggerFactory.getLogger(MetaDisplayPanel.class);

	@Inject
	protected ImageUtils imageUtils;
	
	private String uri;
	
	public MetaDisplayPanel(String id, String uri) {
		super(id);
		if (uri != null) {
			// BackendLandingPage does not pass parameters
			try {
				uri = URLDecoder.decode(uri, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				// Ignore
			}
		}

		add(new Label("date", new DateModel(this)));
		add(new Label("title"));
		setOutputMarkupId(true);
		
		PageParameters params = new PageParameters();
		params.add("uri", uri);
		BookmarkablePageLink adminLink = new BookmarkablePageLink("edit", SequenceEditPage.class, params);
		add(adminLink);
		Image editImage = new Image("editImage", new ResourceReference(MetaDisplayPanel.class, "images/edit.png"));
		adminLink.add(editImage);
		if (PickwickApplication.get().getPickwickSession().getUser()!= null && PickwickApplication.get().getPickwickSession().getUser().isAdmin()) {
			adminLink.setVisible(true);
		}else{
			adminLink.setVisible(false);
		}
		
		this.uri = uri;
		
		//actions
		BookmarkablePageLink diapo;
		add(diapo = new BookmarkablePageLink("diapo", DiaporamaPage.class, params));
		Image diapoImage = new Image("diapoImage", new ResourceReference(MetaDisplayPanel.class, "images/diaporama.png"));
		diapo.add(diapoImage);
		
		BookmarkablePageLink map;
		add(map = new BookmarkablePageLink("map", GmapPage.class, params));
		Image mapImage = new Image("mapImage", new ResourceReference(MetaDisplayPanel.class, "images/word.png"));
		map.add(mapImage);
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
	
	@Override
	protected void onComponentTag(ComponentTag tag) {
		super.onComponentTag(tag);
		tag.put("class", "metaDisplay");
	}
	
	public void readSequence() {
		if (uri != null) {
			File imageDir = imageUtils.toFile(uri);
			Sequence sequence = ImageUtils.readSequence(imageDir);
			setModel(new CompoundPropertyModel(new DisplaySequence(sequence, imageDir)));
		}
	}

}
