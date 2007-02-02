package wicket.contrib.dojo.markup.html.Bubble;

import wicket.ajax.AjaxRequestTarget;
import wicket.contrib.dojo.AbstractRequireDojoBehavior;

public class DojoBubbleHandler extends AbstractRequireDojoBehavior {

	public void setRequire(RequireDojoLibs libs) {
		libs.add("dojoWicket.widget.Bubble");
	}

	protected void respond(AjaxRequestTarget target) {
	}

}
