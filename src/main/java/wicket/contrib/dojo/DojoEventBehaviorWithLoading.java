package wicket.contrib.dojo;

import wicket.Component;
import wicket.ajax.IAjaxIndicatorAware;

/**
 * @author vdemay
 *
 */
public abstract class DojoEventBehaviorWithLoading extends DojoEventBehavior implements IAjaxIndicatorAware
{

	private Component loadingComponent;
	
	/**
	 * @param event
	 * @param loadingComponent componant to show or hide for loading
	 */
	public DojoEventBehaviorWithLoading(String event, Component loadingComponent)
	{
		super(event);
		this.loadingComponent = loadingComponent;
	}

	/* (non-Javadoc)
	 * @see wicket.ajax.IAjaxIndicatorAware#getAjaxIndicatorMarkupId()
	 */
	public String getAjaxIndicatorMarkupId()
	{
		return loadingComponent.getMarkupId();
	}

	protected void onBind()
	{
		super.onBind();
		loadingComponent.setOutputMarkupId(true);
	}

}
