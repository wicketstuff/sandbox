package wicket.contrib.scriptaculous.effects;

import wicket.Component;

public class Effect
{
	public static String highlight(Component component)
	{
		return "new Effect.Highlight('" + component.getMarkupId() + "');";
	}
}
