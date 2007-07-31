package org.wicketstuff.pickwick.backend.pages;

import org.wicketstuff.pickwick.backend.panel.BackendMenuItemPanel;
import org.wicketstuff.pickwick.frontend.pages.BasePage;

public class BackendLandingPage extends BasePage {
	public BackendLandingPage() {
		add(new BackendMenuItemPanel("users", "users", UserEditPage.class, "Users Management", "Add, remove, edit users and groups"));
		add(new BackendMenuItemPanel("sequences", "sequence", SequenceEditPage.class, "Sequences Management", "Edit sequences metadata"));
		add(new BackendMenuItemPanel("import", "users", SequenceEditPage.class, "Sequences Import", "Import a sequence from Http"));
		add(new BackendMenuItemPanel("config", "config", SequenceEditPage.class, "Configuration", "Configure Pickwick"));

	}
}
