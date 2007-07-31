package org.wicketstuff.pickwick.backend.pages;

import org.apache.wicket.PageParameters;
import org.wicketstuff.pickwick.backend.panel.UserPanel;
import org.wicketstuff.pickwick.frontend.pages.BasePage;

public class UserEditPage extends BasePage {
	
	public UserEditPage(PageParameters params) {
		

		add(new UserPanel("users", null));
		
	}
}
