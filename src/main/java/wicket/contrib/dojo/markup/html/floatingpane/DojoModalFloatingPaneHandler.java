package wicket.contrib.dojo.markup.html.floatingpane;

import wicket.ajax.AjaxRequestTarget;
import wicket.contrib.dojo.AbstractRequireDojoBehavior;
import wicket.markup.html.IHeaderResponse;

/**
 * A Dojo Dialog Box Handler
 * @author Vincent Demay
 *
 */
public class DojoModalFloatingPaneHandler extends AbstractRequireDojoBehavior
{
	@Override
	protected void respond(AjaxRequestTarget target)
	{
		//DO NOTHING
	}
	

	@Override
	public void setRequire(RequireDojoLibs libs)
	{
		libs.add("dojo.widget.FloatingPane");
		
	}

}
