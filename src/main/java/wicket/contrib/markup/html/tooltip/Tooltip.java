package wicket.contrib.markup.html.tooltip;



import wicket.markup.html.WebMarkupContainer;
import wicket.markup.html.panel.Panel;
import wicket.model.IModel;
import wicket.model.Model;
import wicket.AttributeModifier;
import wicket.Component;

/**
 * 
 * 
 * based on textsoft.it's multy-line html tooltip tutorial: 
 * <a href="http://www.texsoft.it/index.php?c=software&m=sw.js.htmltooltip&l=it">texsoft.it's Tooltip tutoroial</a><br/>
 * 
 * In short this is a fully customizable Javascript-HTML-Layout Wicket Tooltip.
 * What does it do? Well you make a MVOTooltip.java (My Very Own Tooltip)<br/>
 * which extends Tooltip.java, and write the corresponding MVOTooltip.html<br/> 
 * as if it were the HTML for a panel, you can make you're very own cusomized tooltip.<br/> 
 *
 * Note: Using setter methods afeter construction probably wont do much good, <br/>
 * because instance fields are used to render AttributeModifiers in the initTooltip() method.<br/>
 *
 *for usage examples see: <br/>
 *<a href="http://www.jroller.com/page/ruudmarco?entry=tooltip_tutioral_part_one">Tutorial 1: Static Tooltip</a><br/>
 *<a href="http://www.jroller.com/comments/ruudmarco/Weblog/tooltip_tutioral_part_2_dynamic">Tutorial 2: Dynamic Tooltip</a>
 * @author Marco & Ruud
 */

public class Tooltip extends Panel
{

	private final TooltipPanel tooltipPanel;
	private final WebMarkupContainer iframe;
	
	public Tooltip(String id, TooltipPanel panel)
	{
		super(id);
		this.tooltipPanel = panel;
		add(panel);
		add(iframe = new WebMarkupContainer("iframe"));
		iframe.add(new AttributeModifier("id", true, new Model(tooltipPanel.getIFrameID())));
	}

	public WebMarkupContainer getIframe()
	{
		return iframe;
	}
	
	public TooltipPanel getTooltipPanel()
	{
		return tooltipPanel;
	}
}
