package wicket.contrib.dojo.markup.html.tooltip;

import static wicket.contrib.dojo.DojoIdConstants.DOJO_TYPE;
import static wicket.contrib.dojo.DojoIdConstants.DOJO_TYPE_TOOLTIP;
import wicket.Component;
import wicket.MarkupContainer;
import wicket.behavior.AttributeAppender;
import wicket.contrib.dojo.toggle.DojoToggle;
import wicket.contrib.dojo.widgets.HideWebMarkupContainer;
import wicket.contrib.dojo.widgets.StyleAttribute;
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
	public DojoTooltip(MarkupContainer parent, String id, Component component)
	{
		super(parent, id);
		this.component = component;
		add(new DojoTooltipHandler());
	}
	
	@Override
	protected void onComponentTag(ComponentTag tag)
	{
		super.onComponentTag(tag);
		tag.put(DOJO_TYPE, DOJO_TYPE_TOOLTIP);
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
		this.add(new AttributeAppender("toggle", new Model<String>(toggle.getToggle()),""));
		this.add(new AttributeAppender("toggleDuration", new Model<String>(toggle.getDuration() + ""),""));
	}

}
