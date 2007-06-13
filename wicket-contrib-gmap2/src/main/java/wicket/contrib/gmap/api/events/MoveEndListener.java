package wicket.contrib.gmap.api.events;

import java.io.Serializable;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;


/**
 * Listener of MoveEnd Events of GMap2Event.
 */
public abstract class MoveEndListener extends GMap2Listener implements Serializable
{
	
	/**
	 * 
	 */
	public abstract void moveEndPerformed(MoveEndEvent event, AjaxRequestTarget target);
}
