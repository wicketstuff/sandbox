package org.wicketstuff.pickwick.backend.pages;

import org.wicketstuff.dojo.markup.html.container.DojoSimpleContainer;
import org.wicketstuff.dojo.markup.html.container.layout.DojoLayoutContainer;
import org.wicketstuff.dojo.markup.html.container.layout.DojoLayoutContainer.Position;
import org.wicketstuff.pickwick.backend.panel.BackendMenuItemPanel;

public class BackendLandingPage extends BackendBasePage {
	public BackendLandingPage() {
		
		DojoLayoutContainer layout;
		addOnClient(layout = new DojoLayoutContainer("mainArea"));
		DojoSimpleContainer s1 = new DojoSimpleContainer("icons");
		layout.add(s1, Position.Client);
		addIcons(s1);
		
		DojoSimpleContainer s2 = new DojoSimpleContainer("infos");
		s2.setWidth("250px");
		layout.add(s2, Position.Left);
	}

	private void addIcons(DojoSimpleContainer container) {
		container.add(new BackendMenuItemPanel("users", "users", SequenceEditPage.class, "Users Management", "Add, remove, edit users and groups"));
		container.add(new BackendMenuItemPanel("sequences", "users", SequenceEditPage.class, "Sequences Management", "Edit sequences metadata"));
		container.add(new BackendMenuItemPanel("import", "users", SequenceEditPage.class, "Sequences Import", "Import a sequence from Http"));
		container.add(new BackendMenuItemPanel("config", "users", SequenceEditPage.class, "Configuration", "Configure Pickwick"));
	}
}
