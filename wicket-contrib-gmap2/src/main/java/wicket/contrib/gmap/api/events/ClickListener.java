package wicket.contrib.gmap.api.events;

import java.io.Serializable;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;



/**
 * Listener to Click Events of AjaxGEvent.
 */
public abstract class ClickListener extends GMap2Listener implements Serializable
{

	/**
	 * @param clickEvent
	 */
	public abstract void clickPerformed(ClickEvent clickEvent, AjaxRequestTarget target);

}
