package org.wicketstuff.pickwick.frontend.pages;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.PageParameters;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.WebComponent;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.Model;
import org.wicketstuff.dojo.AbstractRequireDojoBehavior;
import org.wicketstuff.dojo.markup.html.container.DojoSimpleContainer;
import org.wicketstuff.dojo.markup.html.container.layout.DojoLayoutContainer;
import org.wicketstuff.dojo.markup.html.container.layout.DojoLayoutContainer.Position;
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
		try {
			uri = URLDecoder.decode(uri, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			//Ignore
		}
		final WebComponent image = new WebComponent("scaled");
		image.setOutputMarkupId(true);
		image.add(new AttributeModifier("src", true, new Model(getRequest().getRelativePathPrefixToContextRoot()
				+ PickWickApplication.SCALED_IMAGE_PATH + "/" + uri)));
		DojoLayoutContainer imageLayout = new DojoLayoutContainer("image");
		addOnClient(imageLayout);
		final DojoSimpleContainer top, bottom;
		top = new DojoSimpleContainer("top");
		bottom = new DojoSimpleContainer("bottom");
		bottom.setHeight("2em");
		imageLayout.add(top, Position.Client);
		imageLayout.add(bottom, Position.Bottom);
		top.add(image);
		add(new AbstractRequireDojoBehavior() {
			@Override
			public void setRequire(RequireDojoLibs libs) {
				libs.add("dojo.html");
				//libs.add("dojo.log");
			}
			@Override
			public void renderHead(IHeaderResponse response) {
				super.renderHead(response);
				response.renderJavascriptReference(new ResourceReference(ImagePage.class, "ImagePage.js"));
				// FIXME onResize is ignored
				response.renderJavascript("dojo.addOnLoad(function() {resizeImage('"+image.getMarkupId()+"', '" + top.getMarkupId() + "'); dojo.event.connect(dojo.byId('"+ mainLayout.getMarkupId() +"'), 'onResize', function() {resizeImage('"+image.getMarkupId()+"', '" + top.getMarkupId() + "');});});", ImagePage.class.getName());
			}
			@Override
			protected void respond(AjaxRequestTarget target) {
			}
		});
		PageParameters pageparams;
		try {
			pageparams = new PageParameters();
			pageparams.put("uri", imageUtils.getPreviousImage(uri));
			BookmarkablePageLink prev = new URIBookmarkablePageLink("prev", ImagePage.class, pageparams);
			bottom.add(prev);
			pageparams = new PageParameters();
			pageparams.put("uri", imageUtils.getNextImage(uri));
			BookmarkablePageLink next = new URIBookmarkablePageLink("next", ImagePage.class, pageparams);
			bottom.add(next);
			pageparams = new PageParameters();
			pageparams.put("uri", imageUtils.getFirstImage(uri));
			BookmarkablePageLink first = new URIBookmarkablePageLink("first", ImagePage.class, pageparams);
			bottom.add(first);
			pageparams = new PageParameters();
			pageparams.put("uri", imageUtils.getLastImage(uri));
			BookmarkablePageLink last = new URIBookmarkablePageLink("last", ImagePage.class, pageparams);
			bottom.add(last);
			
			//FIXME : remove me, just for tests
			bottom.add(new BookmarkablePageLink("meta", MetadataViewPage.class, params));
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
