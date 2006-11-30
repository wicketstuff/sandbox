package wicket.contrib.dojo.examples;

import wicket.PageParameters;
import wicket.ajax.AjaxRequestTarget;
import wicket.ajax.markup.html.AjaxLink;
import wicket.contrib.dojo.markup.html.floatingpane.DojoModalFloatingPane;
import wicket.markup.html.WebPage;

public class ModalPaneSample extends WebPage {
	
	public ModalPaneSample(PageParameters parameters){
		final DojoModalFloatingPane dialog = new DojoModalFloatingPane(this,"dialogFloating");
		dialog.setTitle("Sample...");
		dialog.setWidth("300px");
		dialog.setHeight("300px");
		new AjaxLink(this, "open"){

			@Override
			public void onClick(AjaxRequestTarget target) {
				dialog.show(target);			
			}
			
		};
	}
}
