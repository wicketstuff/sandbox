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
	
	/**
	 * Makes the element drop and fade out at the same time.
	 * @see http://wiki.script.aculo.us/scriptaculous/show/Effect.DropOut
	 */
	public class DropOut extends AbstractEffect {

		public DropOut(Component component) {
			super(component);
		}

		protected String getEffectName() {
			return "DropOut";
		}
	}

	/**
	 * shake an element from left to right
	 * 
	 * @see http://wiki.script.aculo.us/scriptaculous/show/Effect.Shake
	 */
	public class Shake extends AbstractEffect {

		public Shake(Component component) {
			super(component);
		}

		protected String getEffectName() {
			return "Shake";
		}
	}
	
	/**
	 * @see http://wiki.script.aculo.us/scriptaculous/show/Effect.SwitchOff
	 */
	public class SwitchOff extends AbstractEffect {

		public SwitchOff(Component component) {
			super(component);
		}

		protected String getEffectName() {
			return "SwitchOff";
		}
	}
	
	/**
	 * Reduce the element to its top-left corner.
	 * @see http://wiki.script.aculo.us/scriptaculous/show/Effect.Squish
	 */
	public class Squish extends AbstractEffect {

		public Squish(Component component) {
			super(component);
		}

		protected String getEffectName() {
			return "Squish";
		}
	}

	/**
	 * @see http://wiki.script.aculo.us/scriptaculous/show/Effect.Shrink
	 */
	public class Shrink extends AbstractEffect {

		public Shrink(Component component) {
			super(component);
		}

		protected String getEffectName() {
			return "Shrink";
		}
	}

	/**
	 * Grow an element into view.
	 * 
	 * @TODO: support direction option
	 * @see http://wiki.script.aculo.us/scriptaculous/show/Effect.Grow
	 */
	public class Grow extends AbstractEffect {

		public Grow(Component component) {
			super(component);
		}

		protected String getEffectName() {
			return "Grow";
		}
	}

	/**
	 * Reduce the element to its top then to left to make it disappear.
	 * @see http://wiki.script.aculo.us/scriptaculous/show/Effect.Fold
	 */
	public class Fold extends AbstractEffect {

		public Fold(Component component) {
			super(component);
		}

		protected String getEffectName() {
			return "Fold";
		}
	}

	/**
	 * Pulsates the element.
	 * 
	 * @see http://wiki.script.aculo.us/scriptaculous/show/Effect.Pulsate
	 */
	public class Pulsate extends AbstractEffect {

		public Pulsate(Component component) {
			super(component);
		}

		protected String getEffectName() {
			return "Pulsate";
		}
		/**
		 * set the number of seconds after which to stop the effect.
		 * @param seconds
		 */
		public void setDuration(int seconds) {
			addOption("duration", new Integer(seconds));
		}
		/**
		 * set the minimal opacity during the pulsate.
		 * @param value
		 */
		public void setFrom(float value) {
			addOption("from", new Float(value));
		}
		/**
		 * set the number of pulses within the duration time.
		 * default value is 5
		 * @param pulses
		 */
		public void setPulses(int pulses) {
			addOption("pulses", new Integer(pulses));
		}
	}

	/**
	 * Core effect to change to opacity of an element. 
	 * 
	 * @see Appear
	 * @see Fade
	 * @see http://wiki.script.aculo.us/scriptaculous/show/Effect.Opacity
	 */
	public class Opacity extends AbstractEffect
	{
		public Opacity(Component component) {
			super(component);
		}

		protected String getEffectName() {
			return "Opacity";
		}

		/**
		 * set the duration of the transition.
		 * default value is 1.0 seconds
		 * @param seconds
		 */
		public void setDuration(float seconds) {
			addOption("duration", new Float(seconds));
		}
		/**
		 * set the starting opacity of the element
		 * @param from
		 */
		public void setFrom(float from) {
			addOption("from", new Float(from));			
		}
		
		/**
		 * set the ending opacity of the element
		 * @param from
		 */
		public void setTo(float to) {
			addOption("to", new Float(to));			
		}
	}

	/**
	 * Make an element appear. 
	 * 
	 * @see http://wiki.script.aculo.us/scriptaculous/show/Effect.Appear
	 */
	public class Appear extends Opacity
	{

		public Appear(Component component) {
			super(component);
		}

		protected String getEffectName() {
			return "Appear";
		}
	}
	
	/**
	 * Make an element fade away. 
	 * 
	 * @see http://wiki.script.aculo.us/scriptaculous/show/Effect.Fade
	 */
	public class Fade extends Opacity
	{

		public Fade(Component component) {
			super(component);
		}

		protected String getEffectName() {
			return "Fade";
		}
	}
	
	/**
	 * Make an element Puff away. 
	 * 
	 * @see http://wiki.script.aculo.us/scriptaculous/show/Effect.Puff
	 */
	public class Puff extends Opacity
	{

		public Puff(Component component) {
			super(component);
		}

		protected String getEffectName() {
			return "Puff";
		}
	}
}
