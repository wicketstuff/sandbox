package org.wicketstuff.pickwick.backend.panel;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.PageLink;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

/**
 * A panel displaying a backend action
 * @author Vincent Demay
 *
 */
public class BackendMenuItemPanel extends Panel{

	private String imagePath;
	private ResourceReference over;
	private ResourceReference out;
	
	public BackendMenuItemPanel(String id, String imgPath, Class webPageClass, String title, String description) {
		super(id);
		this.imagePath = imgPath;
		
		over = new ResourceReference(BackendMenuItemPanel.class, "images/" + imagePath + "_hover.gif");
		out = new ResourceReference(BackendMenuItemPanel.class, "images/" + imagePath + ".gif");
	
		PageLink link = new PageLink("link", webPageClass);
		add(link);
		link.add(new WebMarkupContainer("image"){
			@Override
			protected void onBeforeRender() {
				super.onBeforeRender();
				add(new AttributeModifier("src", true, new Model(urlFor(out).toString())));
			}
		});
		
		link.add(new Label("title", new Model(title)));
		link.add(new Label("description", new Model(description)));
		
	}
	
	@Override
	protected void onBeforeRender() {
		super.onBeforeRender();
		add(new AttributeModifier("onMouseOver", true, new Model("this.getElementsByTagName('img')[0].src='" + urlFor(over).toString() + "'")));
		add(new AttributeModifier("onMouseOut", true, new Model("this.getElementsByTagName('img')[0].src='" + urlFor(out).toString() + "'")));
	}
	
	

}
