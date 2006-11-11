package wicket.contrib.dojo.examples;

import wicket.PageParameters;
import wicket.contrib.dojo.AbstractDefaultDojoBehavior;
import wicket.contrib.dojo.examples.rssreader.DescriptionPanel;
import wicket.contrib.dojo.examples.rssreader.UpdateLabel;
import wicket.contrib.markup.html.tooltip.SimpleTooltip;
import wicket.contrib.markup.html.tooltip.Tooltip;
import wicket.markup.html.WebMarkupContainer;
import wicket.markup.html.WebPage;
import wicket.markup.html.basic.Label;
import wicket.markup.html.image.Image;
import wicket.model.CompoundPropertyModel;

public class TooltipTestPage extends WebPage{
	
	private Label target;
	private Label target2;
	private Label stlabel;
	private Label stlabel2;
	
	private String text;
	private Label i;
	AbstractDefaultDojoBehavior ajax;
	private Image testImage;
	
	UpdateLabel j;
	WebMarkupContainer c;
	DescriptionPanel dpanel;

	public TooltipTestPage(PageParameters parameters)
	{
		target = new Label(this, "target","point here for a simple tooltip");
		new Tooltip(this, "stooltip", new SimpleTooltip(this, target, "help!"));
		target2 = new Label(this, "target2","point here for a Dynamic tooltip");
		new Tooltip(this, "tooltip2",new MyTooltip(this, new CompoundPropertyModel(new MyTooltipModel("this tooltip is a bit more dynamic with a model")) ,  target2, 100, 50));
		
		//		add student example
        stlabel = new Label(this, "stlabel", "Marco van de Haar");
        stlabel2 = new Label(this, "stlabel2", "Ruud Booltink");
//      add the tooltip dor the student and give a StudentModel to use witht hte tooltip
        new Tooltip(this, "studenttooltip", new StudentTooltip(this, new CompoundPropertyModel(new StudentModel(1234, "van de Haar", "Marco", 'm')), stlabel, 100, 20));
        new Tooltip(this, "studenttooltip2", new StudentTooltip(this, new CompoundPropertyModel(new StudentModel(1235, "Booltink", "Ruud", 'm')), stlabel2, 100, 20));
		
                
	}

}
