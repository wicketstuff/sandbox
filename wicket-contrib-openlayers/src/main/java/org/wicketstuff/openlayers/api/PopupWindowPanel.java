package org.wicketstuff.openlayers.api;

import org.apache.wicket.markup.html.panel.Panel;

public class PopupWindowPanel extends Panel {

	private final static String markupId="content";
	
	public PopupWindowPanel() {
		super(markupId);
		setOutputMarkupId(true);
	}
/**
 * NOOP!
 * NEED STATIC ID!
 */
	@Override
	public void setMarkupId(String markupId) {
		// TODO Auto-generated method stub
//		super.setMarkupId(markupId);
	}
	

}
