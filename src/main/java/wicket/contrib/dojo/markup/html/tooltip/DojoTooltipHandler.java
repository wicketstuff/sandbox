package wicket.contrib.dojo.markup.html.tooltip;

import wicket.ajax.AjaxRequestTarget;
import wicket.contrib.dojo.AbstractRequireDojoBehavior;

/**
 * A Dojo Dialog Box Handler
 * @author <a href="http://www.demay-fr.net/blog">Vincent Demay</a>
 *
 */
public class DojoTooltipHandler extends AbstractRequireDojoBehavior
{

	protected void respond(AjaxRequestTarget target)
	{
		//DO NOTHING
	}


	protected void onBind()
	{
		super.onBind();
		((DojoTooltip)getComponent()).getTooltipedComponent().setOutputMarkupId(true);
	}


	public void setRequire(RequireDojoLibs libs)
	{
		libs.add("dojo.widget.Tooltip");
	}

}
