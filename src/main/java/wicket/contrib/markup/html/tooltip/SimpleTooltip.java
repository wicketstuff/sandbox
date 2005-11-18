package wicket.contrib.markup.html.tooltip;

import wicket.Component;
import wicket.markup.html.basic.Label;
import wicket.markup.html.resources.JavaScriptReference;

/**
 * @author Marco van de Haar
 * simple form of Tooltip.java which has a default markup, style and holds a Label.
 *
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