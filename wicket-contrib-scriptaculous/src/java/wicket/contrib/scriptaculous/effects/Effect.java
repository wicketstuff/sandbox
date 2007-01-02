package wicket.contrib.scriptaculous.effects;

import java.util.HashMap;
import java.util.Map;

import wicket.Component;
import wicket.contrib.scriptaculous.JavascriptBuilder;

public interface Effect
{

	String toJavascript();

	public abstract class AbstractEffect implements Effect {
		private final Component component;
		private final Map options;

		public AbstractEffect(Component component) {
			this.component = component;
			this.options = new HashMap();
		}

		public AbstractEffect(Component, Map options) {
			this.component = component;
			this.options = options;
		}

		public String toString() {
			return toJavascript();
		}

		public String toJavascript() {
			JavascriptBuilder builder = new JavascriptBuilder();
			builder.addLine("new Effect." + getEffectName() + "('" + component.getMarkupId() + "' ");
			builder.addOptions(options);
			builder.addLine(");");

			return builder.toJavascript();
		}

		protected abstract String getEffectName();

	}

	public class Highlight extends AbstractEffect {

		public Highlight(Component component) {
			super(component);
		}
		public Highlight(Component component, Map options) {
			super(component, options);
		}

		@Override
		protected String getEffectName()
		{
			return "Highlight";
		}
	}
}
