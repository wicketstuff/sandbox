package org.wicketstuff.dojo.examples.contextual;

import org.apache.wicket.ResourceReference;
import org.wicketstuff.dojo.examples.WicketExamplePage;
import org.wicketstuff.dojo.markup.html.container.DojoSimpleContainer;
import org.wicketstuff.dojo.markup.html.contextualMenu.DojoContextualMenuBehavior;
import org.wicketstuff.dojo.markup.html.contextualMenu.DojoMenu;
import org.wicketstuff.dojo.markup.html.contextualMenu.DojoMenuItem;

public class MenuSample extends WicketExamplePage {

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
