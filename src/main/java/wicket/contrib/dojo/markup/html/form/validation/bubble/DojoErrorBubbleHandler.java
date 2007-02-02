package wicket.contrib.dojo.markup.html.form.validation.bubble;

import wicket.ajax.AjaxRequestTarget;
import wicket.contrib.dojo.AbstractRequireDojoBehavior;

public class DojoErrorBubbleHandler extends AbstractRequireDojoBehavior {

	public void setRequire(RequireDojoLibs libs) {
		libs.add("dojoWicket.widget.ErrorBubble");
	}

	protected void respond(AjaxRequestTarget target) {
	}

}
