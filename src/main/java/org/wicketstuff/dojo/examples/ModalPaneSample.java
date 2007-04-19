package org.wicketstuff.dojo.examples;

import org.apache.wicket.PageParameters;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.wicketstuff.dojo.markup.html.DojoLink;
import org.wicketstuff.dojo.markup.html.floatingpane.DojoModalFloatingPane;
import org.apache.wicket.markup.html.WebPage;

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
