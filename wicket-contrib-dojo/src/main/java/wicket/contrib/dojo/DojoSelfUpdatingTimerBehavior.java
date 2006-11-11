package wicket.contrib.dojo;

import wicket.ajax.AjaxRequestTarget;
import wicket.util.time.Duration;

/**
 * Automatically re-renders the component it is attached to via AJAX at a
 * regular interval.
 * 
 * @author vdemay
 */
public class DojoSelfUpdatingTimerBehavior extends AbstractDojoTimerBehavior
{

	/**
	 * Construct.
	 * 
	 * @param updateInterval
	 *            Duration between AJAX callbacks
	 */
	public DojoSelfUpdatingTimerBehavior(Duration updateInterval)
	{
		super(updateInterval);
	}
	
	/**
	 * @param updateInterval
	 * @param loadingId
	 */
	public DojoSelfUpdatingTimerBehavior(final Duration updateInterval, final String loadingId)
	{
		super(updateInterval, loadingId);
	}

	/**
	 * @param target The AJAX target
	 * @see wicket.ajax.AbstractDojoTimerBehavior#onTimer(wicket.ajax.AjaxRequestTarget)
	 */
	@Override
	protected final void onTimer(final AjaxRequestTarget target)
	{
		target.addComponent(getComponent());
		onPostProcessTarget(target);
	}

	/**
	 * Give the subclass a chance to add something to the target, like a
	 * javascript effect call. Called after the hosting component has been added
	 * to the target.
	 * 
	 * @param target The AJAX target
	 */
	protected void onPostProcessTarget(final AjaxRequestTarget target)
	{
	}

}
