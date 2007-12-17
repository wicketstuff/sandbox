package org.wicketstuff.pickwick.auth;

import org.apache.wicket.authentication.pages.SignInPage;
import org.apache.wicket.markup.html.resources.StyleSheetReference;
import org.wicketstuff.pickwick.frontend.pages.BasePage;

public class PickwickLoginPage extends SignInPage {
	
	public PickwickLoginPage() {
		add(new StyleSheetReference("pickwickCss", BasePage.class, "css/pickwick.css"));
	}
}

