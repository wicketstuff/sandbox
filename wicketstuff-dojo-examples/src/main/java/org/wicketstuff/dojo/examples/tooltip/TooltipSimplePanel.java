package org.wicketstuff.dojo.examples.tooltip;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

public class TooltipSimplePanel extends Panel
{
	
	//i want a default constructor with just a target and an id
	public TooltipSimplePanel(String id)
	{
		super(id);
		add(new Label("label", "tooltip.......!!!!"));
		add(new Image("dogimg", new Model("dog.gif")));
	}

}
