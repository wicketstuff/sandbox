package wicket.contrib.dojo.examples;

import wicket.ajax.AjaxRequestTarget;
import wicket.contrib.dojo.markup.html.inlineeditbox.DojoInlineEditBox;
import wicket.markup.html.WebPage;
import wicket.markup.html.basic.Label;

public class DojoInlineEditBoxSample extends WebPage {
	
	public static final String DISPLAY_TEXT = "displayText";

	private DojoInlineEditBox dojoInlineEditBox;
	private Label displayText;
	
	
	public DojoInlineEditBoxSample() {
		
		new DojoInlineEditBox(this,"inlineEditBox", "inlineEditBox") {
			@Override
			protected void onSave(AjaxRequestTarget target) {
				displayText.setModelObject(getModelObject());
				target.addComponent(displayText);
			}
		};
		
		displayText = new Label(this, DISPLAY_TEXT, "Label");
		displayText.setOutputMarkupId(true);
	}
}
