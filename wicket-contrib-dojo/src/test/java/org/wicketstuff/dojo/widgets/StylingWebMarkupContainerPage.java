package org.wicketstuff.dojo.widgets;

import org.wicketstuff.dojo.markup.html.floatingpane.DojoFloatingPane;
import org.wicketstuff.dojo.skin.manager.SkinManager;
import org.wicketstuff.dojo.skin.windows.WindowsDojoSkin;
import org.apache.wicket.markup.html.WebPage;

public class StylingWebMarkupContainerPage extends WebPage {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public StylingWebMarkupContainerPage() {
		super();
		//simple styling
		StylingWebMarkupContainer styling1 = new StylingWebMarkupContainer("styling1");
		styling1.setWidth("250px");
		styling1.setHeight("100px");
		add(styling1);
		
		//styling using Java and from Html
		StylingWebMarkupContainer styling2 = new StylingWebMarkupContainer("styling2");
		styling2.setWidth("250px");
		styling2.setHeight("100px");
		add(styling2);
		
		//styling overwriting Java styling attr from Html ones
		StylingWebMarkupContainer styling3 = new StylingWebMarkupContainer("styling3");
		styling3.setWidth("250px");
		styling3.setHeight("100px");
		add(styling3);
	}

}
