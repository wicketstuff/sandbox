package wicket.contrib.dojo.markup.html.floatingpane;

import wicket.behavior.AttributeAppender;
import wicket.contrib.dojo.DojoIdConstants;
import wicket.contrib.dojo.toggle.DojoToggle;
import wicket.markup.ComponentTag;
import wicket.model.Model;

public class DojoModalFloatingPane extends DojoAbstractFloatingPane
{
	private String bgColor="white";
	private String bgOpacity="0.5";

	public DojoModalFloatingPane(String id)
	{
		super(id);
		add(new DojoModalFloatingPaneHandler());
		setOutputMarkupId(true);
	}

	protected void onComponentTag(ComponentTag tag)
	{
		super.onComponentTag(tag);
		tag.put(DojoIdConstants.DOJO_TYPE, DojoIdConstants.DOJO_TYPE_MODALFLOATINGPANE);
		tag.put("bgColor", bgColor);
		tag.put("bgOpacity", bgOpacity);
	}
	
	/**
	 * Background color for floating pane
	 * @return Background color for floating pane
	 */
	public String getBgColor()
	{
		return bgColor;
	}

	/**
	 * Background color for floating pane
	 * @param bgColor Background color for floating pane
	 */
	public void setBgColor(String bgColor)
	{
		this.bgColor = bgColor;
	}

	/**
	 * Background opacity for floating pane
	 * @return Background opacity for floating pane
	 */
	public String getBgOpacity()
	{
		return bgOpacity;
	}

	/**
	 * Background opacity for floating pane
	 * @param bgOpacity Background opacity for floating pane
	 */
	public void setBgOpacity(String bgOpacity)
	{
		this.bgOpacity = bgOpacity;
	}
	
	/**
	 * Set the dialog effect : see {@link DojoToggle}
	 * @param toggle
	 */
	public void setToggle(DojoToggle toggle){
		this.add(new AttributeAppender("toggle", new Model(toggle.getToggle()),""));
		this.add(new AttributeAppender("toggleDuration", new Model(toggle.getDuration() + ""),""));
	}
	
	
}
