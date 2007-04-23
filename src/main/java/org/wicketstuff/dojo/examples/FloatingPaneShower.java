package org.wicketstuff.dojo.examples;

import org.apache.wicket.PageParameters;
import org.wicketstuff.dojo.markup.html.floatingpane.DojoFloatingPane;
import org.apache.wicket.markup.html.WebPage;

public class FloatingPaneShower extends WebPage {
	
	public FloatingPaneShower(PageParameters parameters){
		DojoFloatingPane pane1 = new DojoFloatingPane( "pane1");
		add(pane1);
		DojoFloatingPane pane2 = new DojoFloatingPane("pane2");
		pane1.add(pane2);
		DojoFloatingPane pane3 = new DojoFloatingPane("pane3");
		add(pane3);
		
		
		pane3.setHasShadow(true);
		pane3.setHeight("300px");
		pane3.setWidth("300px");
		
		pane1.setDisplayCloseAction(false);
		pane1.setHeight("300px");
		pane1.setWidth("300px");
		pane2.setTitle("a title here");
	}
}
