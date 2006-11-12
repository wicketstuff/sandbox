package wicket.contrib.dojo;

import wicket.ajax.AjaxRequestTarget;
import wicket.ajax.ClientEvent;
import wicket.markup.ComponentTag;
import wicket.util.time.Duration;

/**
 * An ajax behavior that is attached to a certain client-side (usually
 * javascript) event, such as onClick, onChange, onKeyDown, etc.
 * <p>
 * Example:
 * 
 * <pre>
 *          DropDownChoice choice=new DropDownChoice(...);
 *          choice.add(new DojoEventBehavior(ClientEvent.CHANGE) {
 *              protected void onEvent(AjaxRequestTarget target) {
 *                  System.out.println(&quot;ajax here!&quot;);
 *              }
 *          }
 * </pre>
 * 
 * This behavior will be linked to the onChange javascript event of the select
 * box this DropDownChoice represents, and so anytime a new option is selected
 * we will get the System.out message
 * 
 * @author vdemay
 */
public abstract class DojoEventBehavior extends AbstractDefaultDojoBehavior
{
	private static long sequence = 0;

	private static final long serialVersionUID = 1L;

	private ClientEvent event;
	
	/**
	 * Construct.
	 * 
	 * @param event
	 *            event this behavior will be attached to
	 */
	public DojoEventBehavior(final ClientEvent event)
	{
		if (event == null)
		{
			throw new IllegalArgumentException("argument [event] cannot be null or empty");
		}

		onCheckEvent(event);

		this.event = event;
	}

	/**
	 * @param throttleDelay 
	 * @return do not use for the moment
	 *
	 */
	public final DojoEventBehavior setThrottleDelay(Duration throttleDelay)
	{
		throw new RuntimeException("not yet implemented");
	}

	/**
	 * 
	 * @param tag 
	 * @see wicket.behavior.AbstractDojoBehavior#onComponentTag(wicket.markup.ComponentTag)
	 */
	@Override
	protected void onComponentTag(final ComponentTag tag)
	{
		super.onComponentTag(tag);
		tag.put(event.getEvent(), getEventHandler());
	}

	/**
	 * 
	 * @return event handler
	 */
	protected CharSequence getEventHandler()
	{
		CharSequence handler = getCallbackScript();
		if (event == ClientEvent.HREF)
		{
			handler = "javascript:" + handler;
		}
		return handler;
	}

	/**
	 * 
	 * @param event
	 */
	protected void onCheckEvent(final ClientEvent event)
	{
	}

	/**
	 * 
	 * @return event
	 */
	public final ClientEvent getEvent()
	{
		return event;
	}

	/**
	 * 
	 * @param target 
	 * @see wicket.ajax.AbstractDefaultDojoBehavior#respond(wicket.ajax.AjaxRequestTarget)
	 */
	@Override
	protected final void respond(final AjaxRequestTarget target)
	{
		onEvent(target);
	}

	/**
	 * Listener method for the ajax event
	 * 
	 * @param target
	 */
	protected abstract void onEvent(final AjaxRequestTarget target);

}
