package org.wicketstuff.dojo.examples.modalpane;

import org.apache.wicket.PageParameters;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.wicketstuff.dojo.examples.WicketExamplePage;
import org.wicketstuff.dojo.markup.html.DojoLink;
import org.wicketstuff.dojo.markup.html.floatingpane.DojoModalFloatingPane;

public class ModalPaneSample extends WicketExamplePage {
	
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
