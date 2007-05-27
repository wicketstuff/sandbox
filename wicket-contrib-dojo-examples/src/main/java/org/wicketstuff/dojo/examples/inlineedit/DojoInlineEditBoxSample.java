package org.wicketstuff.dojo.examples.inlineedit;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.wicketstuff.dojo.examples.WicketExamplePage;
import org.wicketstuff.dojo.markup.html.inlineeditbox.DojoInlineEditBox;

public class DojoInlineEditBoxSample extends WicketExamplePage {
	
	public static final String DISPLAY_TEXT = "displayText";

	private DojoInlineEditBox dojoInlineEditBox;
	private Label displayText;
	
	
	public DojoInlineEditBoxSample() {
		
		add(new DojoInlineEditBox("inlineEditBox", "inlineEditBox") {
			protected void onSave(AjaxRequestTarget target) {
				displayText.setModelObject(getModelObject());
				target.addComponent(displayText);
			}
		});
		
		displayText = new Label(DISPLAY_TEXT, "Label");
		displayText.setOutputMarkupId(true);
		add(displayText);
	}
}
