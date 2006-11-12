package wicket.contrib.dojo.examples;

import wicket.MarkupContainer;
import wicket.markup.html.basic.Label;
import wicket.markup.html.image.Image;
import wicket.markup.html.panel.Panel;
import wicket.model.Model;

public class TooltipSimplePanel extends Panel
{
	
	//i want a default constructor with just a target and an id
	public TooltipSimplePanel(MarkupContainer parent, String id)
	{
		super(parent, id);
		new Label(this, "label", "tooltip.......!!!!");
		new Image(this, "dogimg", new Model("dog.gif"));
	}

}
