package org.wicketstuff.accordion;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;

public class content extends Panel {

	public content(String id, String text) {
		super(id);
		add(new Label("label", text));
	}

}
