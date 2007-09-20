package org.wicketstuff.pickwick.backend.pages;

import org.apache.wicket.PageParameters;
import org.wicketstuff.pickwick.backend.panel.RolesPanel;
import org.wicketstuff.pickwick.frontend.pages.BasePage;

public class RoleEditPage extends BasePage {
	
	public RoleEditPage(PageParameters params) {
		add(new RolesPanel("roleEdit"));
	}
}
