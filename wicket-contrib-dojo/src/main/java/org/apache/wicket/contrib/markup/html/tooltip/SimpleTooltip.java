package org.apache.wicket.contrib.markup.html.tooltip;

import org.apache.wicket.Component;
import org.apache.wicket.contrib.dojo.markup.html.tooltip.DojoTooltip;
import org.apache.wicket.markup.html.basic.Label;

/**
 * @author Marco van de Haar
 * simple form of Tooltip.java which has a default markup, style and holds a Label.
 * @deprecated will be remove in 2.0 use {@link DojoTooltip}
 */
public class SimpleTooltip extends TooltipPanel
{

	/**
	 * @param id component ID
	 * @param target target component
	 * @param label text String for the label
	 * 
	 * constructor with default positioning
	 */
	public SimpleTooltip(Component target, String label)
		{
			super(target);
			add(new Label("tooltiplabel", label));
		}
	

	/**
	 * @param id component ID
	 * @param target target component
	 * @param label text String for the label
	 * @param x x-offset
	 * @param y y-offset
	 */
	public SimpleTooltip(Component target, String label, int x, int y)
	{
		super(target, x, y);
		add(new Label("tooltiplabel", label));
	}

}
