package wicket.contrib.dojo;

import wicket.ajax.AbstractAjaxTimerBehavior;
import wicket.util.time.Duration;

/**
 * Dojo Ajax auto update handler. <br/>
 * Bind this handler to any component implementing IUpdatable.<br/>
 * Every <i>interval</i> the bound component's update() method will be called<br/>
 * followed by a rerender of the bound component.<br/>
 * 
 * @author Ruud Booltink
 * @author Marco van de Haar
 *
 * TODO : show getJsTimeoutCall in {@link AbstractAjaxTimerBehavior} to implement it
 */
public abstract class AbstractDojoTimerBehavior extends AbstractAjaxTimerBehavior
{
	private String loadingId = "";
	
	/**
	 * 
	 * @param updateInterval Duration between AJAX callbacks
	 */
	public AbstractDojoTimerBehavior(final Duration updateInterval)
	{
		super(updateInterval);
	}
	
	/**
	 * @param updateInterval Duration between AJAX callbacks
	 * @param loadingId
	 */
	public AbstractDojoTimerBehavior(final Duration updateInterval, String loadingId)
	{
		super(updateInterval);
		loadingId = loadingId;
		
	}
	
}
