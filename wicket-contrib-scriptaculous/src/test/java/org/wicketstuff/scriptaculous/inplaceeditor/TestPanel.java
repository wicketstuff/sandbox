package org.wicketstuff.scriptaculous.inplaceeditor;

import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

public class TestPanel extends Panel {

	public TestPanel(String id) {
		super(id);
		
		add(new AjaxEditInPlaceLabel("label", new Model("me & you")));
	}
}
