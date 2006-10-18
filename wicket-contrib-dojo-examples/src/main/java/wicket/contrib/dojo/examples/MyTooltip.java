package wicket.contrib.dojo.examples;

import wicket.Component;
import wicket.MarkupContainer;
import wicket.contrib.markup.html.tooltip.TooltipPanel;
import wicket.markup.html.basic.Label;
import wicket.markup.html.image.Image;
import wicket.model.IModel;
import wicket.model.Model;

public class MyTooltip extends TooltipPanel
{
	
	//i want a default constructor with just a target and an id
	public MyTooltip(MarkupContainer parent, Component target)
	{
		super(parent, target);
		new Label(this, "label", "tooltip.......!!!!");
		new Image(this, "dogimg", new Model("dog.gif"));
	}
	
	//and I want a constructor with an x, y and a model
	public MyTooltip(MarkupContainer parent, IModel model, Component target, int x, int y)
	{
		super(parent, model, target, x, y);
		
		String label1 = ((MyTooltipModel)getModelObject()).getLabel1();
		if(label1 == null)
		{
			label1 = "this is a more static label";
		}
		
		new Label(this, "label", label1);
		new Image(this, "dogimg", new Model("dog.gif"));
	}

}
