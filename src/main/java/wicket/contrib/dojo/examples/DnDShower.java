package wicket.contrib.dojo.examples;

import wicket.PageParameters;
import wicket.ajax.AjaxRequestTarget;
import wicket.contrib.dojo.dojodnd.DojoDragContainer;
import wicket.contrib.dojo.dojodnd.DojoDropContainer;
import wicket.markup.html.WebPage;
import wicket.markup.html.image.Image;

public class DnDShower extends WebPage {
	
	public DnDShower(PageParameters parameters){
		DojoDropContainer dropContainer = new DojoDropContainer(this,"dropContainer"){
		
			@Override
			public void onDrop(AjaxRequestTarget target, DojoDragContainer container, int position) {
				System.out.println("position = " + position);
				System.out.println("DojoDragContainer" + container.getId());
				
			}
		
		};
		
		DojoDragContainer dragContainer1 = new DojoDragContainer(this,"dragContainer1");
		DojoDragContainer dragContainer2 = new DojoDragContainer(this,"dragContainer2");
		DojoDragContainer dragContainer3 = new DojoDragContainer(this,"dragContainer3");
		
		DojoDragContainer dragContainer4 = new DojoDragContainer(dropContainer,"dragContainer4");
		DojoDragContainer dragContainer5 = new DojoDragContainer(dropContainer,"dragContainer5");
		
		new Image(dragContainer1,"pic1");
		new Image(dragContainer2,"pic2");
		new Image(dragContainer3,"pic3");
		new Image(dragContainer4,"pic4");
		new Image(dragContainer5,"pic5");
	}
}
