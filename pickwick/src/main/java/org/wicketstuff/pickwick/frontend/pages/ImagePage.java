package org.wicketstuff.pickwick.frontend.pages;

import java.io.File;
import java.io.IOException;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.PageParameters;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.markup.html.WebComponent;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.wicketstuff.pickwick.PickwickApplication;
import org.wicketstuff.pickwick.Utils;
import org.wicketstuff.pickwick.auth.PickwickAuthorization;
import org.wicketstuff.pickwick.auth.PickwickSession;
import org.wicketstuff.pickwick.backend.ImageUtils;
import org.wicketstuff.pickwick.backend.Settings;
import org.wicketstuff.pickwick.backend.pages.MetadataViewPage;
import org.wicketstuff.pickwick.bean.DisplaySequence;
import org.wicketstuff.pickwick.bean.Sequence;
import org.wicketstuff.pickwick.frontend.panel.ActionPanel;
import org.wicketstuff.pickwick.frontend.panel.DescriptionPanel;
import org.wicketstuff.pickwick.frontend.panel.FolderTreePanel;

import com.google.inject.Inject;

/**
 * Page to display a single image
 * 
 * @author <a href="mailto:jbq@apache.org">Jean-Baptiste Quenot</a>
 */
public class ImagePage extends BasePage {
	@Inject
	private ImageUtils imageUtils;
	
	@Inject
	private Settings settings;

	String uri;

	/**
	 * Need to read sequence information everytime the page is displayed to
	 * ensure to have uptodate information
	 */
	@Override
	protected void onBeforeRender() {
		super.onBeforeRender();
		File imageDir = imageUtils.toFile(uri).getParentFile();
		Sequence sequence = imageUtils.readSequence(imageDir);
		setDefaultModel(new CompoundPropertyModel(new DisplaySequence(sequence, imageDir)));
	}

	public ImagePage(PageParameters params) {
		super(params);
		
		PageParameters sparams = new PageParameters();
		File imageFile = imageUtils.toFile(uri);
		sparams.add("uri", imageUtils.getRelativePath(imageFile.getParentFile()));

		final WebComponent image = new WebComponent("scaled");
		image.setOutputMarkupId(true);
		image.add(new AttributeModifier("src", true, new Model(getRequest().getRelativePathPrefixToContextRoot()
				+ PickwickApplication.SCALED_IMAGE_PATH + "/" + uri)));
		add(image);
		// FIXME : create a widget with that
		final WebMarkupContainer nav = new WebMarkupContainer("nav");
		add(nav);
		
		PageParameters pageparams;
		try {
			pageparams = new PageParameters();
			pageparams.put("uri", imageUtils.getPreviousImage(uri));
			BookmarkablePageLink prev = new URIBookmarkablePageLink("prev", ImagePage.class, pageparams);
			nav.add(prev);
			Image prevImage = new Image("prevImage", new ResourceReference(ImagePage.class, "images/previous.png"));
			prev.add(prevImage);
			
			pageparams = new PageParameters();
			pageparams.put("uri", imageUtils.getNextImage(uri));
			BookmarkablePageLink next = new URIBookmarkablePageLink("next", ImagePage.class, pageparams);
			nav.add(next);
			Image nextImage = new Image("nextImage", new ResourceReference(ImagePage.class, "images/next.png"));
			next.add(nextImage);
			
			pageparams = new PageParameters();
			pageparams.put("uri", imageUtils.getFirstImage(uri));
			BookmarkablePageLink first = new URIBookmarkablePageLink("first", ImagePage.class, pageparams);
			nav.add(first);
			Image firstImage = new Image("firstImage", new ResourceReference(ImagePage.class, "images/first.png"));
			first.add(firstImage);
			
			pageparams = new PageParameters();
			pageparams.put("uri", imageUtils.getLastImage(uri));
			BookmarkablePageLink last = new URIBookmarkablePageLink("last", ImagePage.class, pageparams);
			nav.add(last);
			Image lastImage = new Image("lastImage", new ResourceReference(ImagePage.class, "images/last.png"));
			last.add(lastImage);

			// FIXME : remove me, just for tests
			BookmarkablePageLink meta;
			nav.add(meta = new BookmarkablePageLink("meta", MetadataViewPage.class, params));
			Image metaImage = new Image("metaImage", new ResourceReference(ImagePage.class, "images/information.png"));
			meta.add(metaImage);
			
			String folder = Utils.getFolderFor(uri);
			PageParameters folderParams = new PageParameters();
			folderParams.add("uri", folder);
			BookmarkablePageLink diapo;
			nav.add(diapo = new BookmarkablePageLink("diapo", DiaporamaPage.class, folderParams));
			Image diapoImage = new Image("diapoImage", new ResourceReference(ImagePage.class, "images/diaporama.png"));
			diapo.add(diapoImage);
			
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
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
	protected Panel getEastPanel(String id) {
		return new ActionPanel(id, uri);
	}
	
	@Override
	protected Panel getWestPanel(String id) {
		return new FolderTreePanel(id);
	}
	
	@Override
	protected Panel getSouthPanel(String id) {
		return new DescriptionPanel(id, uri);
	}

	public class URIBookmarkablePageLink extends BookmarkablePageLink {
		public URIBookmarkablePageLink(String id, Class pageClass, PageParameters parameters) {
			super(id, pageClass, parameters);
		}

		@Override
		public boolean isEnabled() {
			
			PageParameters p = ImagePage.this.getPageParameters();
			
			return (p!=null&&p.getString("uri") != null);
		}
	}
}
