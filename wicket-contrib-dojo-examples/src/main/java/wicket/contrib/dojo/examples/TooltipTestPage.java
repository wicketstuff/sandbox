package wicket.contrib.dojo.examples;

import wicket.PageParameters;
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
	private Image testImage;
	
	UpdateLabel j;
	WebMarkupContainer c;
	DescriptionPanel dpanel;

	public TooltipTestPage(PageParameters parameters)
	{
		add(target = new Label("target","point here for a simple tooltip"));
		add(new Tooltip("stooltip", new SimpleTooltip(target, "help!")));
		add(target2 = new Label("target2","point here for a Dynamic tooltip"));
		add(new Tooltip("tooltip2",new MyTooltip(new CompoundPropertyModel(new MyTooltipModel("this tooltip is a bit more dynamic with a model")) ,  target2, 100, 50)));
		
		//		add student example
        add(stlabel = new Label("stlabel", "Marco van de Haar"));
        add(stlabel2 = new Label("stlabel2", "Ruud Booltink"));
//      add the tooltip dor the student and give a StudentModel to use witht hte tooltip
        add(new Tooltip("studenttooltip", new StudentTooltip(new CompoundPropertyModel(new StudentModel(1234, "van de Haar", "Marco", 'm')), stlabel, 100, 20)));
        add(new Tooltip("studenttooltip2", new StudentTooltip(new CompoundPropertyModel(new StudentModel(1235, "Booltink", "Ruud", 'm')), stlabel2, 100, 20)));
		
                
	}

}
