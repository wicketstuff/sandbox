package wicket.contrib.dojo.examples;

import wicket.PageParameters;
import wicket.ajax.AjaxRequestTarget;
import wicket.contrib.dojo.markup.html.DojoLink;
import wicket.contrib.dojo.markup.html.floatingpane.DojoModalFloatingPane;
import wicket.markup.html.WebPage;

public class ModalPaneSample extends WebPage {
	
	public ModalPaneSample(PageParameters parameters){
		final DojoModalFloatingPane dialog = new DojoModalFloatingPane("dialogFloating");
		dialog.setTitle("Sample...");
		dialog.setWidth("300px");
		dialog.setHeight("300px");
		dialog.setMinHeight("200px");
		dialog.setMinWidth("200px");
		add(dialog);
		
		add(new DojoLink("open"){
			public void onClick(AjaxRequestTarget target) {
				dialog.show(target);			
			}
			
		});
		
	}
}
