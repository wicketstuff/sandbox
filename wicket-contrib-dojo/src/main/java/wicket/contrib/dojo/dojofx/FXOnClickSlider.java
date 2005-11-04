package wicket.contrib.dojo.dojofx;

import wicket.AttributeModifier;
import wicket.Component;
import wicket.contrib.dojo.dojofx.DojoFXHandler.AppendAttributeModifier;
import wicket.markup.html.HtmlHeaderContainer;
import wicket.model.Model;

public class FXOnClickSlider extends DojoFXHandler
{
	
	private String HTMLID;
	private String componentId;
	private final int x;
	private final int y;
	private final String type;
	private int fromx;
	private int fromy;

	
	
	public FXOnClickSlider(int duration, Component trigger, int x, int y, boolean relative)
	{
		super("onclick", duration, trigger);
		this.x = x;
		this.y = y;
		if(relative)
		{
			type = "relative";
		} else
		{
			type = "absolute";
		}

	}
	
	public FXOnClickSlider(int duration, Component trigger, int x, int y, int fromx, int fromy)
	{
		super("onclick", duration, trigger);
		this.x = x;
		this.y = y;
		this.fromx = fromx;
		this.fromy = fromy;
		this.type = "fromto";
		
	}

		
	protected void onBind()
	{
		Component c = getComponent();
		this.component = (Component)c;
		this.componentId = c.getId();
	
		//create a unique HTML for the explode component
		HTMLID = this.component.getId() + "_" + component.getPath();
		//Add ID to component, and bind effect to trigger
		this.component.add(new AttributeModifier("id", true, new Model(HTMLID)));
		
		this.component.add(new AppendAttributeModifier("style",true, new Model("position: absolute")));
		
		if (type=="relative")
		{
			this.getTrigger().add(new AppendAttributeModifier(getEventName(),true, new Model("dojo.fx.html.slideBy(document.getElementById('" + HTMLID + "'), ["+ x +", " + y +"]," + getDuration() +")")));
		}
		else if (type=="fromto")
		{
			this.getTrigger().add(new AppendAttributeModifier(getEventName(),true, new Model("dojo.fx.html.slide(document.getElementById('" + HTMLID + "'), ["+ fromx +", " + fromy +"], ["+ x +", " + y +"]," + getDuration() +")")));
		}
		//assume that type == absolute
		else 
		{
			this.getTrigger().add(new AppendAttributeModifier(getEventName(),true, new Model("dojo.fx.html.slideTo(document.getElementById('" + HTMLID + "'), ["+ x +", " + y +"]," + getDuration() +")")));
		}
		
	}

	protected void renderHeadContribution(HtmlHeaderContainer container)
	{
		//no header contributions necessary here.
		
	}
}