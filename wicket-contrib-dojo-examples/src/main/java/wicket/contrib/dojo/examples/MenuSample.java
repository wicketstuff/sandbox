package wicket.contrib.dojo.examples;

import wicket.ResourceReference;
import wicket.contrib.dojo.markup.html.container.DojoSimpleContainer;
import wicket.contrib.dojo.markup.html.contextualMenu.DojoContextualMenuBehavior;
import wicket.contrib.dojo.markup.html.contextualMenu.DojoMenu;
import wicket.contrib.dojo.markup.html.contextualMenu.DojoMenuItem;
import wicket.markup.html.WebPage;

public class MenuSample extends WebPage {

	public MenuSample() {
		super();
		DojoSimpleContainer container = new DojoSimpleContainer("container");
		container.setHeight("500px");
		
		DojoMenu menu = new DojoMenu("menu");
		menu.addChild(new DojoMenuItem("about", "About"));
		menu.addChild(new DojoMenuItem("edit", "Edit")
			.addChild(new DojoMenuItem("copy", "Copy", new ResourceReference(MenuSample.class, "copy.jpg")))
			.addChild(new DojoMenuItem("move", "Move", new ResourceReference(MenuSample.class, "move.jpg"))));
		container.add(new DojoContextualMenuBehavior(menu));
		
		add(container);
	}

}
