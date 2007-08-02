package org.wicketstuff.pickwick.frontend.pages;

import java.io.File;
import java.io.IOException;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.PageParameters;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.WebComponent;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.wicketstuff.dojo.AbstractRequireDojoBehavior;
import org.wicketstuff.dojo.dojofx.DojoFXHandler;
import org.wicketstuff.dojo.dojofx.FXOnMouseOverFader;
import org.wicketstuff.dojo.markup.html.container.DojoSimpleContainer;
import org.wicketstuff.dojo.markup.html.container.layout.DojoLayoutContainer;
import org.wicketstuff.dojo.markup.html.container.layout.DojoLayoutContainer.Position;
import org.wicketstuff.pickwick.PickwickApplication;
import org.wicketstuff.pickwick.Utils;
import org.wicketstuff.pickwick.auth.PickwickSession;
import org.wicketstuff.pickwick.backend.ImageUtils;
import org.wicketstuff.pickwick.backend.pages.MetadataViewPage;
import org.wicketstuff.pickwick.backend.pages.SequenceEditPage;
import org.wicketstuff.pickwick.bean.DisplaySequence;
import org.wicketstuff.pickwick.bean.Sequence;
import org.wicketstuff.pickwick.frontend.panel.MetaDisplayPanel;

import com.google.inject.Inject;

/**
 * Page to display a single image
 * 
 * @author <a href="mailto:jbq@apache.org">Jean-Baptiste Quenot</a>
 */
public class ImagePage extends BasePage {
	@Inject
	private ImageUtils imageUtils;

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
		setModel(new CompoundPropertyModel(new DisplaySequence(sequence, imageDir)));
	}

	public ImagePage(PageParameters params) {
		uri = Utils.getUri(params);

		add(new MetaDisplayPanel("meta", uri));

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

		//nav.add(new FXOnMouseOverFader(500,image,false,1.0,0.5));
		nav.setOutputMarkupId(true);
		add(new AbstractRequireDojoBehavior() {
			@Override
			public void setRequire(RequireDojoLibs libs) {
				libs.add("dojo.html");
				libs.add("dojo.lfx.*");
			}

			@Override
			public void renderHead(IHeaderResponse response) {
				super.renderHead(response);
				response.renderJavascriptReference(new ResourceReference(ImagePage.class, "ImagePage.js"));
				// connect to the resize event the image resize
				/*response.renderOnLoadJavascript("" + "resizeImage('" + image.getMarkupId() + "', '" + top.getMarkupId()
						+ "'); " + "dojo.event.connect(dojo.widget.byId('" + mainLayout.getMarkupId()
						+ "'), 'onResized', function() {" + "resizeImage('" + image.getMarkupId() + "', '"
						+ top.getMarkupId() + "');" + "});");*/
				// by default, the image is not visible when everythink is
				// loaded, make it visible
				response.renderOnLoadJavascript("dojo.byId('" + image.getMarkupId() + "').style.visibility='visible'");
				// response.renderOnLoadJavascript("dojo.byId('"+nav.getMarkupId()+"').style.visibility='visible'");
				
				response.renderJavascript("dojo.addOnLoad( function(){" +
						"	connectProgresTransparency('" + image.getMarkupId() + "','" + nav.getMarkupId() + "')" +
						"})", "fader");
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

			// FIXME : remove me, just for tests
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
