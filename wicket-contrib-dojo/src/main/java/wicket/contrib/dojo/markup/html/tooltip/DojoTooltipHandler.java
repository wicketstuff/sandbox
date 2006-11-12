package wicket.contrib.dojo.markup.html.tooltip;

import wicket.ajax.AjaxRequestTarget;
import wicket.contrib.dojo.AbstractDefaultDojoBehavior;
import wicket.contrib.dojo.AbstractRequireDojoBehavior;
import wicket.markup.html.IHeaderResponse;

/**
 * A Dojo Dialog Box Handler
 * @author <a href="http://www.demay-fr.net/blog">Vincent Demay</a>
 *
 */
public class DojoTooltipHandler extends AbstractRequireDojoBehavior
{
	@Override
	protected void respond(AjaxRequestTarget target)
	{
		//DO NOTHING
	}

	@Override
	protected void onBind()
	{
		super.onBind();
		((DojoTooltip)getComponent()).getTooltipedComponent().setOutputMarkupId(true);
	}

	@Override
	public void setRequire(RequireDojoLibs libs)
	{
		libs.add("dojo.widget.Tooltip");
	}

}
