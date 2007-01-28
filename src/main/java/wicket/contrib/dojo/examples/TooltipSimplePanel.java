package wicket.contrib.dojo.examples;

import wicket.markup.html.basic.Label;
import wicket.markup.html.image.Image;
import wicket.markup.html.panel.Panel;
import wicket.model.Model;

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
