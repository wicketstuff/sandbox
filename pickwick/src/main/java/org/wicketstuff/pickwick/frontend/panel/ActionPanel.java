package org.wicketstuff.pickwick.frontend.panel;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.apache.wicket.PageParameters;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.Panel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wicketstuff.pickwick.PickwickApplication;
import org.wicketstuff.pickwick.Utils;
import org.wicketstuff.pickwick.backend.ImageUtils;
import org.wicketstuff.pickwick.backend.pages.SequenceEditPage;
import org.wicketstuff.pickwick.frontend.pages.DiaporamaPage;
import org.wicketstuff.pickwick.frontend.pages.GmapPage;

import com.google.inject.Inject;

/**
 * A panel display meta datas from a folder
 * @author Vincent Demay
 *
 */
public class ActionPanel extends Panel {

	private static final Logger log = LoggerFactory.getLogger(ActionPanel.class);

	@Inject
	protected ImageUtils imageUtils;
	
	private String uri;
	
	public ActionPanel(String id, String uri) {
		super(id);
		if (uri != null) {
			// BackendLandingPage does not pass parameters
			try {
				uri = URLDecoder.decode(uri, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				// Ignore
			}
		}
		
		PageParameters params = new PageParameters();
		params.add("uri", uri);
		BookmarkablePageLink adminLink = new BookmarkablePageLink("edit", SequenceEditPage.class, params);
		add(adminLink);
		Image editImage = new Image("editImage", new ResourceReference(ActionPanel.class, "images/edit.png"));
		adminLink.add(editImage);
		if (PickwickApplication.get().getPickwickSession().getUser()!= null && PickwickApplication.get().getPickwickSession().getUser().isAdmin()) {
			adminLink.setVisible(true);
		}else{
			adminLink.setVisible(false);
		}
		
		this.uri = uri;
		
		//actions
		PageParameters folderParams = new PageParameters();
		BookmarkablePageLink diapo;
		folderParams.add("uri", Utils.getFolderFor(uri));
		add(diapo = new BookmarkablePageLink("diapo", DiaporamaPage.class, folderParams));
		Image diapoImage = new Image("diapoImage", new ResourceReference(ActionPanel.class, "images/diaporama.png"));
		diapo.add(diapoImage);
	
		WebMarkupContainer zip;
		add(zip = new WebMarkupContainer("zip"){
			@Override
			protected void onComponentTag(ComponentTag tag) {
				super.onComponentTag(tag);
				tag.put("href", "/ZipFolder/" + Utils.getFolderFor(ActionPanel.this.uri) + ".zip");
			}
		});
		Image zipImage = new Image("zipImage", new ResourceReference(ActionPanel.class, "images/save.png"));
		zip.add(zipImage);
		
		BookmarkablePageLink map;
		add(map = new BookmarkablePageLink("map", GmapPage.class, params));
		Image mapImage = new Image("mapImage", new ResourceReference(ActionPanel.class, "images/word.png"));
		map.add(mapImage);
		
		//find if we are in a foler
		if("".equals(uri)){
			zip.setVisible(false);
			adminLink.setVisible(false);
			diapo.setVisible(false);
		}
	}

}
