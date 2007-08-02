package org.wicketstuff.pickwick.frontend.pages;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.PageLink;
import org.apache.wicket.markup.html.resources.StyleSheetReference;
import org.apache.wicket.model.Model;
import org.wicketstuff.pickwick.PickwickApplication;
import org.wicketstuff.pickwick.auth.PickwickLoginPage;
import org.wicketstuff.pickwick.auth.PickwickLogoutPage;
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
		String name = PickwickSession.get().getUserName();
		
		Label userName = new Label("userName", new Model(name));
		PageLink auth = new PageLink("auth", PickwickLoginPage.class);
		PageLink logout = new PageLink("logout", PickwickLogoutPage.class);
		add(userName);
		add(auth);
		add(logout);
		if (PickwickApplication.get().getPickwickSession().getDefaultUser().getName().equals(name)){
			//anonymous
			userName.setVisible(false);
			logout.setVisible(false);
		}else{
			auth.setVisible(false);
		}
	}

}
