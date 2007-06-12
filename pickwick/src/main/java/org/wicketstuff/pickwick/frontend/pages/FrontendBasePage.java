package org.wicketstuff.pickwick.frontend.pages;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.resources.StyleSheetReference;

public class FrontendBasePage extends WebPage {

	public FrontendBasePage() {
		super();
		add(new StyleSheetReference("pickwickCss", FrontendBasePage.class, "css/pickwick.css"));
	}

	public FrontendBasePage(PageParameters parameters) {
		super(parameters);
		add(new StyleSheetReference("pickwickCss", FrontendBasePage.class, "css/pickwick.css"));
	}

}
