package org.wicketstuff.dojo.examples.dnd;

import org.apache.wicket.PageParameters;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.behavior.AbstractBehavior;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.PackageResource;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.resources.CompressedResourceReference;
import org.wicketstuff.dojo.examples.WicketExamplePage;

public class CustomDnDShower extends WicketExamplePage {
	
	public CustomDnDShower(PageParameters parameters) {
		add(new CustomDnDShowerBehavior());
		
		CustomDojoDropContainer dropContainer = new CustomDojoDropContainer("dropContainer");
		add(dropContainer);
		
		CustomDojoDragContainer dragContainer1 = new CustomDojoDragContainer("dragContainer1", "drag1");
		CustomDojoDragContainer dragContainer2 = new CustomDojoDragContainer("dragContainer2", "drag2");
		CustomDojoDragContainer dragContainer3 = new CustomDojoDragContainer("dragContainer3", "drag3");
		add(dragContainer1);
		add(dragContainer2);
		add(dragContainer3);
		
		
		CustomDojoDragContainer dragContainer4 = new CustomDojoDragContainer("dragContainer4", "drag4");
		CustomDojoDragContainer dragContainer5 = new CustomDojoDragContainer("dragContainer5", "drag5");
		dropContainer.add(dragContainer4);
		dropContainer.add(dragContainer5);
		
		dragContainer1.add(new Image("pic1", PackageResource.get(CustomDnDShower.class, "pic1.jpg")));
		dragContainer2.add(new Image("pic2", PackageResource.get(CustomDnDShower.class, "pic2.jpg")));
		dragContainer3.add(new Image("pic3", PackageResource.get(CustomDnDShower.class, "pic3.jpg")));
		dragContainer4.add(new Image("pic4", PackageResource.get(CustomDnDShower.class, "pic4.jpg")));
		dragContainer5.add(new Image("pic5", PackageResource.get(CustomDnDShower.class, "pic5.jpg")));
	}
	
	/**
	 * Class to add our stylesheet to the page.
	 * 
	 * @author B. Molenkamp
	 * @version SVN: $Id$
	 */
	private static class CustomDnDShowerBehavior extends AbstractBehavior {
		
		public static final ResourceReference CSS = new CompressedResourceReference(CustomDnDShower.class, "dnd.css");
		
		/* (non-Javadoc)
		 * @see org.apache.wicket.behavior.AbstractBehavior#renderHead(org.apache.wicket.markup.html.IHeaderResponse)
		 */
		@Override
		public void renderHead(IHeaderResponse response) {
			super.renderHead(response);
			
			response.renderCSSReference(CSS);
		}
	}
}
