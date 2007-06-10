package org.wicketstuff.pickwick.frontend.pages;

import java.io.IOException;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.WebComponent;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.Model;
import org.wicketstuff.pickwick.PickWickApplication;

/**
 * Page to display a single image
 * 
 * @author <a href="mailto:jbq@apache.org">Jean-Baptiste Quenot</a>
 */
public class ImagePage extends WebPage {
	public ImagePage(PageParameters params) {
		String uri = params.getString("uri");
		WebComponent image = new WebComponent("scaled");
		image.add(new AttributeModifier("src", true, new Model(getRequest().getRelativePathPrefixToContextRoot()
				+ PickWickApplication.SCALED_IMAGE_PATH + "/" + uri)));
		add(image);
		PageParameters pageparams;
		try {
			pageparams = new PageParameters();
			pageparams.put("uri", PickWickApplication.get().getImageUtils().getPreviousImage(uri));
			BookmarkablePageLink prev = new URIBookmarkablePageLink("prev", ImagePage.class, pageparams);
			add(prev);
			pageparams = new PageParameters();
			pageparams.put("uri", PickWickApplication.get().getImageUtils().getNextImage(uri));
			BookmarkablePageLink next = new URIBookmarkablePageLink("next", ImagePage.class, pageparams);
			add(next);
			pageparams = new PageParameters();
			pageparams.put("uri", PickWickApplication.get().getImageUtils().getFirstImage(uri));
			BookmarkablePageLink first = new URIBookmarkablePageLink("first", ImagePage.class, pageparams);
			add(first);
			pageparams = new PageParameters();
			pageparams.put("uri", PickWickApplication.get().getImageUtils().getLastImage(uri));
			BookmarkablePageLink last = new URIBookmarkablePageLink("last", ImagePage.class, pageparams);
			add(last);
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
