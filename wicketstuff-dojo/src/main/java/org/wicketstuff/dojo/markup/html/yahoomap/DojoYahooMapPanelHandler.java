package org.wicketstuff.dojo.markup.html.yahoomap;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.wicketstuff.dojo.AbstractRequireDojoBehavior;

public class DojoYahooMapPanelHandler extends AbstractRequireDojoBehavior {

	@Override
	public void setRequire(RequireDojoLibs libs) {
		libs.add("dojo.widget.YahooMap");
	}

	@Override
	protected void respond(AjaxRequestTarget target) {
		// TODO Auto-generated method stub

	}

}
