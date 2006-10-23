package wicket.contrib.dojo.examples;

import wicket.PageParameters;
import wicket.contrib.dojo.dojodnd.DojoDragContainer;
import wicket.contrib.dojo.dojodnd.DojoDropContainer;
import wicket.markup.html.WebPage;

public class DnDShower extends WebPage {
	
	public DnDShower(PageParameters parameters){
		DojoDropContainer dropContainer = new DojoDropContainer(this,"dropContainer"){
		
			@Override
			public void onDrop(DojoDragContainer container, int position) {
				System.out.println("position = " + position);
				System.out.println("DojoDragContainer" + container.getId());
				
			}
		
		};
		
		DojoDragContainer dropContainer1 = new DojoDragContainer(this,"dragContainer1");
		DojoDragContainer dropContainer2 = new DojoDragContainer(this,"dragContainer2");
		DojoDragContainer dropContainer3 = new DojoDragContainer(this,"dragContainer3");
		
		DojoDragContainer dropContainer4 = new DojoDragContainer(dropContainer,"dragContainer4");
		DojoDragContainer dropContainer5 = new DojoDragContainer(dropContainer,"dragContainer5");
		DojoDragContainer dropContainer6 = new DojoDragContainer(dropContainer,"dragContainer6");
		
	}
}
