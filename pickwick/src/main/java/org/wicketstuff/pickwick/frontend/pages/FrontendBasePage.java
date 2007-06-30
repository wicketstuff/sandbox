package org.wicketstuff.pickwick.frontend.pages;

import org.apache.wicket.Component;
import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.PageLink;
import org.apache.wicket.markup.html.resources.StyleSheetReference;
import org.wicketstuff.dojo.markup.html.container.DojoSimpleContainer;
import org.wicketstuff.dojo.markup.html.container.layout.DojoLayoutContainer;
import org.wicketstuff.dojo.markup.html.container.layout.DojoLayoutContainer.Position;
import org.wicketstuff.pickwick.backend.pages.BackendLandingPage;

public class FrontendBasePage extends WebPage {
	
	private DojoSimpleContainer client;

	public FrontendBasePage() {
		super();
		add(new StyleSheetReference("pickwickCss", FrontendBasePage.class, "css/pickwick.css"));
		createLayout();
	}

	public FrontendBasePage(PageParameters parameters) {
		super(parameters);
		add(new StyleSheetReference("pickwickCss", FrontendBasePage.class, "css/pickwick.css"));
		createLayout();
	}
	
	public void createLayout(){
		DojoLayoutContainer layout = new DojoLayoutContainer("page");
		add(layout);
		DojoSimpleContainer top = new DojoSimpleContainer("top");
		top.setHeight("100px");
		layout.add(top, Position.Top);
		client = new DojoSimpleContainer("client");
		layout.add(client, Position.Client);
		
		top.add(new PageLink("back", BackendLandingPage.class));
	}
	
	public void addOnClient(Component c){
		client.add(c);
	}

}
