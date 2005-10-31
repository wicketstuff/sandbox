package wicket.contrib.dojo.dojofx;

import wicket.AttributeModifier;
import wicket.Component;
import wicket.WicketRuntimeException;
import wicket.contrib.dojo.DojoAjaxHandler;
import wicket.markup.ComponentTag;
import wicket.markup.html.HtmlHeaderContainer;
import wicket.markup.html.form.FormComponent;
import wicket.model.IModel;
import wicket.util.resource.IResourceStream;
import wicket.util.value.ValueMap;



/**
 * Abstract Handler class for Dojo.fx.html components
 * currently only handles som instance fields which are
 * currently mandatory for every dojo.fx.html animation
 * and has an AppendAttributeModifier private class, mostly
 * for style and onclick attributes.
 * Also declares some method signature for FX subclasses.
 * 
 * @author Marco van de Haar
 * @author Ruud Booltink
 *
 */
public abstract class DojoFXHandler extends DojoAjaxHandler
{
	private final String eventName;
	protected Component component;
	private final int duration;
	private final Component trigger;

	
	/**
	 * @param eventName name of the function to be bound to the effect (i.e. ONCLICK)
	 * @param duration duration of the animation.
	 * @param trigger trigger object for the animation.
	 */
	public DojoFXHandler(String eventName, int duration, Component trigger)
	{
		if (eventName == null)
		{
			throw new NullPointerException("argument eventName must be not null");
		}
		this.eventName = eventName;
		this.duration = duration;
		this.trigger = trigger;
	}
	
	
	
	/**
	 * @return animation duration.
	 */
	public int getDuration()
	{
		return duration;
	}
	
	
	/* (non-Javadoc)
	 * @see wicket.AjaxHandler#renderHeadContribution(wicket.markup.html.HtmlHeaderContainer)
	 */
	protected abstract void renderHeadContribution(HtmlHeaderContainer container);
	
	
	/* (non-Javadoc)
	 * @see wicket.AjaxHandler#onBind()
	 */
	protected abstract void onBind();
	

	/* (non-Javadoc)
	 * @see wicket.AjaxHandler#getResponse()
	 */
	protected IResourceStream getResponse()
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * @return event name
	 */
	public String getEventName()
	{
		return eventName;
	}
	
	/**
	 * @return trigger component
	 */
	public Component getTrigger()
	{
		return trigger;
	}
	/**
	 * @author Ruud Booltink
	 * @author Marco van de Haar
	 * 
	 * AttributeModifier that appends the new value to the current value if an old value
	 * exists. If it does not exist, it sets the new value.
	 */
	final static class AppendAttributeModifier extends AttributeModifier
	{
		public AppendAttributeModifier(String attribute, boolean addAttributeIfNotPresent, IModel replaceModel) {
			super(attribute, addAttributeIfNotPresent, replaceModel);
		}

		public AppendAttributeModifier(String attribute, IModel replaceModel) {
			super(attribute, replaceModel);
		}

		public AppendAttributeModifier(String attribute, String pattern, boolean addAttributeIfNotPresent, IModel replaceModel) {
			super(attribute, pattern, addAttributeIfNotPresent, replaceModel);
		}

		public AppendAttributeModifier(String attribute, String pattern, IModel replaceModel) {
			super(attribute, pattern, replaceModel);
		}

		protected String newValue(String currentValue, String replacementValue) {
			return (currentValue==null?"":currentValue + "; ") + replacementValue;
		}
	}
	
}


