package wicket.contrib.dojo;

import wicket.ResourceReference;
import wicket.ajax.AbstractAjaxTimerBehavior;
import wicket.ajax.AjaxRequestTarget;
import wicket.markup.html.IHeaderResponse;
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
public abstract class AbstractDojoTimerBehavior extends AbstractDefaultDojoBehavior
{
	private final Duration interval;
	private String LoadingId = "";
	
	/**
	 * 
	 * @param updateInterval Duration between AJAX callbacks
	 */
	public AbstractDojoTimerBehavior(final Duration updateInterval)
	{
		this.interval = updateInterval;
	}
	
	/**
	 * @param updateInterval Duration between AJAX callbacks
	 * @param loadingId
	 */
	public AbstractDojoTimerBehavior(final Duration updateInterval, String loadingId)
	{
		this.interval = updateInterval;
		this.LoadingId = loadingId;
	}
	
	/**
	 * Subclasses should call super.onBind()
	 * 
	 * @see wicket.ajax.AbstractDefaultAjaxBehavior#onBind()
	 */
	@Override
	protected void onBind()
	{
		super.onBind();
	}
	

	/**
	 * @see wicket.contrib.dojo.AbstractDefaultDojoBehavior#renderHead(wicket.markup.html.IHeaderResponse)
	 */
	@Override
	public void renderHead(IHeaderResponse response)
	{
		super.renderHead(response);
		String require = "";
		require += "<script language=\"JavaScript\" type=\"text/javascript\">\n";
		require += "\n";
		require += "function initAutoRefreshFor" + getComponent().getId() + "(){\n";
		require += "	" + getCallbackScript() + "\n";
		require += "}\n";
		require += "dojo.addOnLoad(initAutoRefreshFor" + getComponent().getId() + ");\n";
		require += "</script>\n";
		
		response.renderString(require);
	}
	
	protected CharSequence getCallbackScript(boolean recordPageVersion)
	{
		return getCallbackScript("dojoAutoUpdate(" + interval.getMilliseconds() + ",'" + getCallbackUrl(recordPageVersion) + "'", null, null, null);
	}
	
	
	/**
	 * the javascript function expects the node corresponding to loadingId to have 
	 * a style attribute: visibility=hidden; and sets it to visible during laoding.
	 * This is not very good method for doing this and should probably be replaced by
	 *  a a generic js function which is called during loading.
	 * @return the CSS id for the loading node.
	 */
	private String findIndicatorId()
	{
		return this.LoadingId;
	}

	/**
	 * 
	 * @param target 
	 * @see wicket.ajax.AbstractDefaultAjaxBehavior#respond(wicket.ajax.AjaxRequestTarget)
	 */
	@Override
	protected final void respond(final AjaxRequestTarget target)
	{
		onTimer(target);
	}
	
	/**
	 * Listener method for the AJAX timer event.
	 * 
	 * @param target
	 *            The request target
	 */
	protected abstract void onTimer(final AjaxRequestTarget target);
    
}
