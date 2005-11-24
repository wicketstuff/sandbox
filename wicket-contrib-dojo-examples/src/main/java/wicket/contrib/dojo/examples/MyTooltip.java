package wicket.contrib.dojo.examples;

import wicket.Component;
import wicket.contrib.markup.html.tooltip.TooltipPanel;
import wicket.markup.html.basic.Label;
import wicket.markup.html.image.Image;
import wicket.model.IModel;
import wicket.model.Model;

public class MyTooltip extends TooltipPanel
{
	
	//i want a default constructor with just a target and an id
	public MyTooltip(Component target)
	{
		super(target);
		add(new Label("label", "tooltip.......!!!!"));
		add(new Image("dogimg", new Model("dog.gif")));
	}
	
	//and I want a constructor with an x, y and a model
	public MyTooltip(IModel model, Component target, int x, int y)
	{
		super(model, target, x, y);
		
		String label1 = ((MyTooltipModel)getModelObject()).getLabel1();
		if(label1 == null)
		{
			label1 = "this is a more static label";
		}
		
		add(new Label("label", label1));
		add(new Image("dogimg", new Model("dog.gif")));
	}

}
