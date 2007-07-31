package org.wicketstuff.dojo.examples.inlineedit;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.wicketstuff.dojo.examples.WicketExamplePage;
import org.wicketstuff.dojo.markup.html.DojoLink;
import org.wicketstuff.dojo.markup.html.inlineeditbox.DojoInlineEditBox;

public class DojoInlineEditBoxSample extends WicketExamplePage {
	
	public static final String DISPLAY_TEXT = "displayText";

	private Component dojoInlineEditBox;
	private Label displayText;
	
	
	public DojoInlineEditBoxSample() {
		
		add(dojoInlineEditBox = new DojoInlineEditBox("inlineEditBox", "Hello, World!") {
			protected void onSave(AjaxRequestTarget target) {
				displayText.setModelObject(getModelObject());
				target.addComponent(displayText);
				target.addComponent(dojoInlineEditBox);
			}
		});
		//add(dojoInlineEditBox = new Label("inlineEditBox", "Toto"));
		dojoInlineEditBox.setVisible(false);
		dojoInlineEditBox.setOutputMarkupPlaceholderTag(true);

		// FIXME MUST USE DojoLink instead of AjaxLink, see DOJO-77 Widget loaded by ajax request fails to initialize
		add(new DojoLink("show") {
			@Override
			public void onClick(AjaxRequestTarget target) {
				dojoInlineEditBox.setVisible(true);
				target.addComponent(dojoInlineEditBox);
			}
		});
		displayText = new Label(DISPLAY_TEXT, "<Empty>");
		displayText.setOutputMarkupId(true);
		add(displayText);
	}
}
