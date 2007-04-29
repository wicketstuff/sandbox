package org.wicketstuff.scriptaculous.effect;

import java.util.HashMap;
import java.util.Map;

import org.apache.wicket.Component;
import org.wicketstuff.scriptaculous.JavascriptBuilder;


/**
 * API for working with scriptaculous effects. 
 * 
 * @see http://wiki.script.aculo.us/scriptaculous/show/VisualEffects
 * @author <a href="mailto:wireframe6464@users.sourceforge.net">Ryan Sonnek</a>
 */
public interface Effect
{

	String toJavascript();

	/**
	 * Helper Base Effect class for simple effest that require a component and options.
	 */
	public abstract class AbstractEffect implements Effect
	{
		private final Component component;
		private final Map options;

		public AbstractEffect(Component component)
		{
			this.component = component;
			this.options = new HashMap();
		}

		public AbstractEffect(Component component, Map options)
		{
			this.component = component;
			this.options = options;
		}

		public String toString()
		{
			return toJavascript();
		}

		public String toJavascript()
		{
			JavascriptBuilder builder = new JavascriptBuilder();
			builder
					.addLine("new Effect." + getEffectName() + "('" + component.getMarkupId()
							+ "', ");
			builder.addOptions(options);
			builder.addLine(");");

			return builder.toJavascript();
		}

		protected void addOption(String key, Object value)
		{
			options.put(key, value);
		}

		protected abstract String getEffectName();
	}

	/**
	 * Effect for highlighting a component using the famous "yellow fade".
	 * 
	 * @see http://wiki.script.aculo.us/scriptaculous/show/Effect.Highlight
	 */
	public class Highlight extends AbstractEffect
	{

		public Highlight(Component component)
		{
			super(component);
		}

		public Highlight(Component component, Map options)
		{
			super(component, options);
		}

		protected String getEffectName()
		{
			return "Highlight";
		}

		public void setStartColor(String rgb)
		{
			addOption("startcolor", rgb);
		}

		public void setEndColor(String rgb)
		{
			addOption("endcolor", rgb);
		}

		public void setRestoreColor(String rgb)
		{
			addOption("restorecolor", rgb);
		}
	}
}
