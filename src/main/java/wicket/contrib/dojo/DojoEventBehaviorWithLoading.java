package wicket.contrib.dojo;

import wicket.Component;
import wicket.ajax.ClientEvent;
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
	public DojoEventBehaviorWithLoading(ClientEvent event, Component loadingComponent)
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

	@Override
	protected void onBind()
	{
		super.onBind();
		loadingComponent.setOutputMarkupId(true);
	}

}
