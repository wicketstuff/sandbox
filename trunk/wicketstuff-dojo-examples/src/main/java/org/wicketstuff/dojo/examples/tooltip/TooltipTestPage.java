package org.wicketstuff.dojo.examples.tooltip;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.model.CompoundPropertyModel;
import org.wicketstuff.dojo.AbstractDefaultDojoBehavior;
import org.wicketstuff.dojo.examples.WicketExamplePage;
import org.wicketstuff.dojo.markup.html.tooltip.DojoTooltip;

public class TooltipTestPage extends WicketExamplePage{
	
	private Label target;
	private Label target2;
	private Label stlabel;
	private Label stlabel2;
	
	private String text;
	private Label i;
	AbstractDefaultDojoBehavior ajax;
	private Image testImage;
	
	WebMarkupContainer c;

	public TooltipTestPage(PageParameters parameters)
	{
		target = new Label("target", "point here for a simple tooltip");
		add(target);
		add(new DojoTooltip("stooltip", target));
		target2 = new Label("target2", "point here for a Dynamic tooltip");
		add(target2);
		DojoTooltip tooltip2 = new DojoTooltip("tooltip2", target2);
		add(tooltip2);
		tooltip2.add(new TooltipSimplePanel( "tooltip2Panel"));
		
		//add student example
        stlabel = new Label("stlabel", "Marco van de Haar");
        add(stlabel);
        stlabel2 = new Label("stlabel2", "Ruud Booltink");
        add(stlabel2);
        
        DojoTooltip studenttooltip = new DojoTooltip("studenttooltip", stlabel);
        DojoTooltip studenttooltip2 = new DojoTooltip("studenttooltip2", stlabel2);
        add(studenttooltip);
        add(studenttooltip2);
		
        studenttooltip.add(new TooltipStudentPanel( "student1", new CompoundPropertyModel(new TooltipStudentModel(1234, "van de Haar", "Marco", 'm'))));
        studenttooltip2.add(new TooltipStudentPanel("student2", new CompoundPropertyModel(new TooltipStudentModel(1235, "Booltink", "Ruud", 'm'))));
                
	}

}
