package wicket.contrib.dojo.markup.html.tooltip;

import wicket.Component;
import wicket.behavior.AttributeAppender;
import wicket.contrib.dojo.DojoIdConstants;
import wicket.contrib.dojo.toggle.DojoToggle;
import wicket.contrib.dojo.widgets.HideWebMarkupContainer;
import wicket.markup.ComponentTag;
import wicket.model.Model;

/**
 * @author vdemay
 *
 */
public class DojoTooltip extends HideWebMarkupContainer
{
	
	private Component component;
	
	/**
	 * @param parent
	 * @param id
	 * @param component
	 */
	public DojoTooltip(String id, Component component)
	{
		super(id);
		this.component = component;
		add(new DojoTooltipHandler());
	}
	
	protected void onComponentTag(ComponentTag tag)
	{
		super.onComponentTag(tag);
		tag.put(DojoIdConstants.DOJO_TYPE, DojoIdConstants.DOJO_TYPE_TOOLTIP);
		tag.put("connectId", component.getMarkupId());
	}
	
	/**
	 * @return tooltiped component
	 */
	public Component getTooltipedComponent(){
		return component;
	}
	
	/**
	 * Set the dialog effect
	 * @param toggle
	 */
	public void setToggle(DojoToggle toggle){
		this.add(new AttributeAppender("toggle", new Model(toggle.getToggle()),""));
		this.add(new AttributeAppender("toggleDuration", new Model(toggle.getDuration() + ""),""));
	}

}
