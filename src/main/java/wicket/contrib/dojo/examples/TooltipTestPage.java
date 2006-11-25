package wicket.contrib.dojo.examples;

import wicket.PageParameters;
import wicket.contrib.dojo.AbstractDefaultDojoBehavior;
import wicket.contrib.dojo.examples.rssreader.DescriptionPanel;
import wicket.contrib.dojo.examples.rssreader.UpdateLabel;
import wicket.contrib.dojo.markup.html.tooltip.DojoTooltip;
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
		target = new Label(this, "target", "point here for a simple tooltip");
		new DojoTooltip(this, "stooltip", target);
		target2 = new Label(this, "target2", "point here for a Dynamic tooltip");
		DojoTooltip tooltip2 = new DojoTooltip(this, "tooltip2", target2);
		new TooltipSimplePanel(tooltip2, "tooltip2Panel");
		
		//add student example
        stlabel = new Label(this, "stlabel", "Marco van de Haar");
        stlabel2 = new Label(this, "stlabel2", "Ruud Booltink");

        DojoTooltip studenttooltip = new DojoTooltip(this, "studenttooltip", stlabel);
        DojoTooltip studenttooltip2 = new DojoTooltip(this, "studenttooltip2", stlabel2);
		
        new TooltipStudentPanel(studenttooltip, "student1", new CompoundPropertyModel(new TooltipStudentModel(1234, "van de Haar", "Marco", 'm')));
        new TooltipStudentPanel(studenttooltip2, "student2", new CompoundPropertyModel(new TooltipStudentModel(1235, "Booltink", "Ruud", 'm')));
                
	}

}
