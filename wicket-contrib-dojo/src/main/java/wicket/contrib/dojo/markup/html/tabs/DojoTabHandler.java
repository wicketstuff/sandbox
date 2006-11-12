package wicket.contrib.dojo.markup.html.tabs;

import wicket.ajax.AjaxRequestTarget;
import wicket.contrib.dojo.AbstractRequireDojoBehavior;

/**
 * A Dojo Dialog Box Handler
 * @author vdemay
 *
 */
public class DojoTabHandler extends AbstractRequireDojoBehavior
{
	@Override
	public void setRequire(RequireDojoLibs libs)
	{
		libs.add("dojo.widget.ContentPane");
		libs.add("dojo.widget.TabContainer");
	}

	@Override
	protected void respond(AjaxRequestTarget target)
	{
		// DO NOTHING
		
	}

}
