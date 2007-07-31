package org.wicketstuff.pickwick.frontend.pages;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.resources.StyleSheetReference;
import org.apache.wicket.model.Model;
import org.wicketstuff.pickwick.auth.PickwickSession;
import org.wicketstuff.pickwick.backend.pages.BackendLandingPage;

public class BasePage extends WebPage {
	
	public BasePage() {
		super();
		add(new StyleSheetReference("pickwickCss", BasePage.class, "css/pickwick.css"));
		addHeader();
	}
	
	public void addHeader(){

		add(new BookmarkablePageLink("back", BackendLandingPage.class));
		add(new Label("userName", new Model(PickwickSession.get().getUser().getName())));
	}

}
