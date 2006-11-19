package wicket.contrib.dojo.markup.html.container;

import wicket.ajax.AjaxRequestTarget;
import wicket.contrib.dojo.AbstractRequireDojoBehavior;

/**
 * A very simple handler for Dojo containers
 * @author Vincent Demay
 *
 */
public class DojoSimpleContainerHandler extends AbstractRequireDojoBehavior
{

	/* (non-Javadoc)
	 * @see wicket.contrib.dojo.AbstractRequireDojoBehavior#setRequire(wicket.contrib.dojo.AbstractRequireDojoBehavior.RequireDojoLibs)
	 */
	public void setRequire(final RequireDojoLibs libs)
	{
		libs.add("dojo.widget.ContentPane");
	}

	@Override
	protected void respond(AjaxRequestTarget target)
	{
		//DO NOTHING
		
	}

}
