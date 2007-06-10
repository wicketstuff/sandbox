package org.wicketstuff.pickwick.frontend.pages;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.WebComponent;
import org.apache.wicket.markup.html.WebPage;
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
	}
}
