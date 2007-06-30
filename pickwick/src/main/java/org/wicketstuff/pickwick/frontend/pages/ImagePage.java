package org.wicketstuff.pickwick.frontend.pages;

import java.io.IOException;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.WebComponent;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.Model;
import org.wicketstuff.pickwick.PickWickApplication;
import org.wicketstuff.pickwick.backend.ImageUtils;
import org.wicketstuff.pickwick.backend.pages.MetadataViewPage;

import com.google.inject.Inject;

/**
 * Page to display a single image
 * 
 * @author <a href="mailto:jbq@apache.org">Jean-Baptiste Quenot</a>
 */
public class ImagePage extends FrontendBasePage {
	@Inject
	private ImageUtils imageUtils;
	
	public ImagePage(PageParameters params) {
		String uri = params.getString("uri");
		if (uri == null)
			throw new RuntimeException("No uri provided!");
		WebComponent image = new WebComponent("scaled");
		image.add(new AttributeModifier("src", true, new Model(getRequest().getRelativePathPrefixToContextRoot()
				+ PickWickApplication.SCALED_IMAGE_PATH + "/" + uri)));
		addOnClient(image);
		PageParameters pageparams;
		try {
			pageparams = new PageParameters();
			pageparams.put("uri", imageUtils.getPreviousImage(uri));
			BookmarkablePageLink prev = new URIBookmarkablePageLink("prev", ImagePage.class, pageparams);
			addOnClient(prev);
			pageparams = new PageParameters();
			pageparams.put("uri", imageUtils.getNextImage(uri));
			BookmarkablePageLink next = new URIBookmarkablePageLink("next", ImagePage.class, pageparams);
			addOnClient(next);
			pageparams = new PageParameters();
			pageparams.put("uri", imageUtils.getFirstImage(uri));
			BookmarkablePageLink first = new URIBookmarkablePageLink("first", ImagePage.class, pageparams);
			addOnClient(first);
			pageparams = new PageParameters();
			pageparams.put("uri", imageUtils.getLastImage(uri));
			BookmarkablePageLink last = new URIBookmarkablePageLink("last", ImagePage.class, pageparams);
			addOnClient(last);
			
			//FIXME : remove me, just for tests
			addOnClient(new BookmarkablePageLink("meta", MetadataViewPage.class, params));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public class URIBookmarkablePageLink extends BookmarkablePageLink {
		public URIBookmarkablePageLink(String id, Class pageClass, PageParameters parameters) {
			super(id, pageClass, parameters);
		}

		@Override
		public boolean isEnabled() {
			return parameters.getString("uri") != null;
		}
	}
}
