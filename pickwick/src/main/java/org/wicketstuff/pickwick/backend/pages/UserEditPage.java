package org.wicketstuff.pickwick.backend.pages;

import org.apache.wicket.PageParameters;
import org.wicketstuff.pickwick.backend.panel.UserPanel;

public class UserEditPage extends BackendBasePage {
	
	public UserEditPage(PageParameters params) {
		

		addOnClient(new UserPanel("users", null));
		
	}
}
