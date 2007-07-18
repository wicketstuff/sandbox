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
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.Model;
import org.wicketstuff.dojo.AbstractRequireDojoBehavior;
import org.wicketstuff.dojo.dojofx.FXOnMouseOverFader;
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
		imageLayout.add(top, Position.Client);
		top.add(image);
		
		//FIXME : create a widget with that
		final WebMarkupContainer nav = new WebMarkupContainer("nav");
		top.add(nav);
		nav.setOutputMarkupId(true);
		nav.add(new FXOnMouseOverFader(300,nav,false,0.2, 1.0));
		add(new AbstractRequireDojoBehavior() {
			@Override
			public void setRequire(RequireDojoLibs libs) {
				libs.add("dojo.html");
			}
			@Override
			public void renderHead(IHeaderResponse response) {
				super.renderHead(response);
				response.renderJavascriptReference(new ResourceReference(ImagePage.class, "ImagePage.js"));
				//connect to the resize event the image resize
				response.renderOnLoadJavascript("" +
						"resizeImage('"+image.getMarkupId()+"', '" + top.getMarkupId() + "'); " +
						"dojo.event.connect(dojo.widget.byId('"+ mainLayout.getMarkupId() +"'), 'onResized', function() {" +
								"resizeImage('"+image.getMarkupId()+"', '" + top.getMarkupId() + "');" +
						"});");
				//by default, the image is not visible when everythink is loaded, make it visible
				response.renderOnLoadJavascript("dojo.byId('"+image.getMarkupId()+"').style.visibility='visible'");
				//response.renderOnLoadJavascript("dojo.byId('"+nav.getMarkupId()+"').style.visibility='visible'");
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
			nav.add(prev);
			pageparams = new PageParameters();
			pageparams.put("uri", imageUtils.getNextImage(uri));
			BookmarkablePageLink next = new URIBookmarkablePageLink("next", ImagePage.class, pageparams);
			nav.add(next);
			pageparams = new PageParameters();
			pageparams.put("uri", imageUtils.getFirstImage(uri));
			BookmarkablePageLink first = new URIBookmarkablePageLink("first", ImagePage.class, pageparams);
			nav.add(first);
			pageparams = new PageParameters();
			pageparams.put("uri", imageUtils.getLastImage(uri));
			BookmarkablePageLink last = new URIBookmarkablePageLink("last", ImagePage.class, pageparams);
			nav.add(last);
			
			//FIXME : remove me, just for tests
			nav.add(new BookmarkablePageLink("meta", MetadataViewPage.class, params));
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
