package org.wicketstuff.dojo.examples;

import org.apache.wicket.PageParameters;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.wicketstuff.dojo.dojodnd.DojoDragContainer;
import org.wicketstuff.dojo.dojodnd.DojoDropContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.image.Image;

public class DnDShower extends WebPage {
	
	public DnDShower(PageParameters parameters){
		DojoDropContainer dropContainer = new DojoDropContainer("dropContainer"){
		
			public void onDrop(AjaxRequestTarget target, DojoDragContainer container, int position) {
				System.out.println("position = " + position);
				System.out.println("DojoDragContainer" + container.getId());
				
			}
		
		};
		add(dropContainer);
		
		DojoDragContainer dragContainer1 = new DojoDragContainer("dragContainer1");
		DojoDragContainer dragContainer2 = new DojoDragContainer("dragContainer2");
		DojoDragContainer dragContainer3 = new DojoDragContainer("dragContainer3");
		add(dragContainer1);
		add(dragContainer2);
		add(dragContainer3);
		
		
		DojoDragContainer dragContainer4 = new DojoDragContainer("dragContainer4");
		DojoDragContainer dragContainer5 = new DojoDragContainer("dragContainer5");
		dropContainer.add(dragContainer4);
		dropContainer.add(dragContainer5);
		
		dragContainer1.add(new Image("pic1"));
		dragContainer2.add(new Image("pic2"));
		dragContainer3.add(new Image("pic3"));
		dragContainer4.add(new Image("pic4"));
		dragContainer5.add(new Image("pic5"));
	}
}
