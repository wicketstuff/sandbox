package wicket.contrib.dojo.examples;

import wicket.PageParameters;
import wicket.contrib.dojo.markup.html.floatingpane.DojoFloatingPane;
import wicket.markup.html.WebPage;

public class FloatingPaneShower extends WebPage {
	
	public FloatingPaneShower(PageParameters parameters){
		DojoFloatingPane pane1 = new DojoFloatingPane(this, "pane1");
		DojoFloatingPane pane2 = new DojoFloatingPane(pane1, "pane2");
		DojoFloatingPane pane3 = new DojoFloatingPane(this, "pane3");
		
		pane2.setTitle("a title here");
		pane3.setHasShadow(true);
		pane1.setDisplayCloseAction(false);
	}
}
