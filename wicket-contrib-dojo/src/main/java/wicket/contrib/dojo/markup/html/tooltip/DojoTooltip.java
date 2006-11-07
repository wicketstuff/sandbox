package wicket.contrib.dojo.markup.html.tooltip;

import wicket.Component;
import wicket.MarkupContainer;
import wicket.behavior.AttributeAppender;
import wicket.contrib.dojo.toggle.DojoToggle;
import wicket.markup.ComponentTag;
import wicket.markup.html.WebMarkupContainer;
import wicket.model.Model;

public class DojoTooltip extends WebMarkupContainer
{
	private Component component;
	
	public DojoTooltip(MarkupContainer parent, String id, Component component)
	{
		super(parent, id);
		this.component = component;
		add(new DojoTooltipHandler());
	}
	
	
	@Override
	protected void onComponentTag(ComponentTag tag)
	{
		tag.put("dojoType", "tooltip");
		tag.put("connectId", component.getMarkupId());
		tag.put("style", "display:none");
	}
	
	public Component getTooltipedComponent(){
		return component;
	}
	
	/**
	 * Set the dialog effect
	 * @param toggle
	 */
	public void setToggle(DojoToggle toggle){
		this.add(new AttributeAppender("toggle", new Model<String>(toggle.getToggle()),""));
		this.add(new AttributeAppender("toggleDuration", new Model<String>(toggle.getDuration() + ""),""));
	}

}
