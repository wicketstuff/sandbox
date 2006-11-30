package wicket.contrib.dojo.markup.html.floatingpane;

import wicket.ajax.AjaxRequestTarget;
import wicket.contrib.dojo.AbstractRequireDojoBehavior;

/**
 * A Dojo Dialog Box Handler
 * @author Vincent Demay
 *
 */
public class DojoModalFloatingPaneHandler extends AbstractRequireDojoBehavior
{
	protected void respond(AjaxRequestTarget target)
	{
		//DO NOTHING
	}
	
	public void setRequire(RequireDojoLibs libs)
	{
		libs.add("dojo.widget.FloatingPane");
		
	}

}
