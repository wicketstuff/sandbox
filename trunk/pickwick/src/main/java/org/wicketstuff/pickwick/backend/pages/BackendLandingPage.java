package org.wicketstuff.pickwick.backend.pages;

import org.wicketstuff.pickwick.backend.panel.BackendMenuItemPanel;

public class BackendLandingPage extends BaseAdminPage {
	public BackendLandingPage() {
		super(null);
		add(new BackendMenuItemPanel("users", "users", UserEditPage.class, "Users Management", "Add, remove, edit users"));
		add(new BackendMenuItemPanel("roles", "users", RoleEditPage.class, "Roles Management", "Add, remove, edit roles"));
		add(new BackendMenuItemPanel("sequences", "sequence", SequenceEditPage.class, "Sequences Management", "Edit sequences metadata"));
		add(new BackendMenuItemPanel("import", "users", ImportPage.class, "Sequences Import", "Import a sequence from Http"));
		add(new BackendMenuItemPanel("config", "config", SequenceEditPage.class, "Configuration", "Configure Pickwick"));

	}
}
