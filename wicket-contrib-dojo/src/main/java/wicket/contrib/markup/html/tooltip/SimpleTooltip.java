package wicket.contrib.markup.html.tooltip;

import wicket.Component;
import wicket.MarkupContainer;
import wicket.contrib.dojo.markup.html.tooltip.DojoTooltip;
import wicket.markup.html.basic.Label;

/**
 * @author Marco van de Haar simple form of Tooltip.java which has a default
 *         markup, style and holds a Label.
 * @deprecated see {@link DojoTooltip}
 */
public class SimpleTooltip extends TooltipPanel
{

	/**
	 * @param parent
	 * @param id
	 *            component ID
	 * @param target
	 *            target component
	 * @param label
	 *            text String for the label
	 * 
	 * constructor with default positioning
	 */
	public SimpleTooltip(MarkupContainer parent, Component target, String label)
	{
		super(parent, target);
		new Label(this, "tooltiplabel", label);
	}


	/**
	 * @param parent
	 * @param id
	 *            component ID
	 * @param target
	 *            target component
	 * @param label
	 *            text String for the label
	 * @param x
	 *            x-offset
	 * @param y
	 *            y-offset
	 */
	public SimpleTooltip(MarkupContainer parent, Component target, String label, int x, int y)
	{
		super(parent, target, x, y);
		new Label(this, "tooltiplabel", label);
	}

}