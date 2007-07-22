package org.wicketstuff.pickwick.frontend.pages;

import org.apache.wicket.Component;
import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.PageLink;
import org.apache.wicket.markup.html.resources.StyleSheetReference;
import org.apache.wicket.model.Model;
import org.wicketstuff.dojo.markup.html.container.DojoSimpleContainer;
import org.wicketstuff.dojo.markup.html.container.layout.DojoLayoutContainer;
import org.wicketstuff.dojo.markup.html.container.layout.DojoLayoutContainer.Position;
import org.wicketstuff.pickwick.auth.PickwickSession;
import org.wicketstuff.pickwick.backend.pages.BackendLandingPage;

public class BasePage extends WebPage {
	
	protected DojoSimpleContainer client;
	protected DojoLayoutContainer mainLayout;
	DojoSimpleContainer top;

	public BasePage() {
		super();
		add(new StyleSheetReference("pickwickCss", BasePage.class, "css/pickwick.css"));
		createLayout();
	}
	
	public void createLayout(){
		mainLayout = new DojoLayoutContainer("page");
		add(mainLayout);
		top = new DojoSimpleContainer("top");
		top.setHeight("100px");
		mainLayout.add(top, Position.Top);
		client = new DojoSimpleContainer("client");
		mainLayout.add(client, Position.Client);
		
		top.add(new BookmarkablePageLink("back", BackendLandingPage.class));
		top.add(new Label("userName", new Model(PickwickSession.get().getUser().getName())));
	}
	
	public void addOnClient(Component c){
		client.add(c);
	}
	public void addOnTop(Component c) {
		top.add(c);
	}
}
