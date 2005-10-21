package wicket.contrib.markup.html.tooltip;

import wicket.Component;
import wicket.markup.html.basic.Label;
import wicket.markup.html.resources.JavaScriptReference;

public class SimpleTooltip extends Tooltip
{
public SimpleTooltip(String id, Component target, String label)
	{
		super(id, target);
		add(new Label("tooltiplabel", label));
	}
	

public SimpleTooltip(String id, Component target, String label, int x, int y)
{
	super(id, target, x, y);
	add(new Label("tooltiplabel", label));
}

}