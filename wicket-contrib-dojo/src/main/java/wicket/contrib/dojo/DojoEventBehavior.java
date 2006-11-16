package wicket.contrib.dojo;

import wicket.ajax.AjaxEventBehavior;
import wicket.ajax.ClientEvent;

/**
 * TODO : add Drag event and others
 */
public abstract class DojoEventBehavior extends AjaxEventBehavior
{

	/**
	 * @param event
	 */
	public DojoEventBehavior(ClientEvent event)
	{
		super(event);
	}
}
