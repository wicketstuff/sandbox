package wicket.contrib.scriptaculous.effects;

import wicket.Component;

public interface Effect
{

	String toJavascript();

	public class Highlight implements Effect {
		private final Component component;

		public Highlight(Component component) {
			this.component = component;
		}
		public String toJavascript()
		{
			return "new Effect.Highlight('" + component.getMarkupId() + "');";
		}
	}
}
