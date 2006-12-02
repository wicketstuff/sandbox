package wicket.contrib.dojo.markup.html.toaster;

import wicket.ajax.AjaxRequestTarget;
import wicket.contrib.dojo.AbstractRequireDojoBehavior;

/**
 * A dojo Toaster. allow to show a message in a toaster in a screen corner
 * @author <a href="http://www.demay-fr.net/blog">Vincent Demay</a>
 *
 */
public class DojoToasterHandler extends AbstractRequireDojoBehavior
{

	@Override
	public void setRequire(RequireDojoLibs libs){
		libs.add("dojo.widget.Toaster");
	}

	@Override
	protected void respond(AjaxRequestTarget target){
		
	}

}
