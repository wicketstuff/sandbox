package org.wicketstuff.dojo.examples.dnd;

import org.apache.wicket.PageParameters;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.model.Model;
import org.wicketstuff.dojo.dojodnd.DojoDragContainer;
import org.wicketstuff.dojo.dojodnd.DojoDragCopyContainer;
import org.wicketstuff.dojo.dojodnd.DojoDropContainer;
import org.wicketstuff.dojo.examples.WicketExamplePage;

public class DnDShower extends WicketExamplePage {
	
	public DnDShower(PageParameters parameters){
		DojoDropContainer dropContainer = new DojoDropContainer("dropContainer"){
		
			public void onDrop(AjaxRequestTarget target, DojoDragContainer container, int position) {
				System.out.println("position = " + position);
				System.out.println("DojoDragContainer = " + container.getId());
				System.out.println("DojoDragContainerModel = " + container.getModelObjectAsString());
				
			}
		
		};
		add(dropContainer);
		
		DojoDragContainer dragContainer1 = new DojoDragContainer("dragContainer1");
		DojoDragContainer dragContainer2 = new DojoDragContainer("dragContainer2");
		DojoDragContainer dragContainer3 = new DojoDragContainer("dragContainer3");
		add(dragContainer1);
		dragContainer1.setModel(new Model("foo"));
		add(dragContainer2);
		add(dragContainer3);
		
		// some drag-copy sources.
		DojoDragCopyContainer dragCopyContainer1 = new DojoDragCopyContainer("dragCopyContainer1", true);
		DojoDragCopyContainer dragCopyContainer2 = new DojoDragCopyContainer("dragCopyContainer2", true);
		DojoDragCopyContainer dragCopyContainer3 = new DojoDragCopyContainer("dragCopyContainer3", false);
		dragCopyContainer1.add(new Image("pic1"));
		dragCopyContainer2.add(new Image("pic2"));
		dragCopyContainer3.add(new Image("pic3"));
		add(dragCopyContainer1);
		add(dragCopyContainer2);
		add(dragCopyContainer3);
		
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
