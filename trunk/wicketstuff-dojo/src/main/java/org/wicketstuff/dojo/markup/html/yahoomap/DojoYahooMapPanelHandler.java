package org.wicketstuff.dojo.markup.html.yahoomap;

import org.wicketstuff.dojo.AbstractDojoWidgetBehavior;

public class DojoYahooMapPanelHandler extends AbstractDojoWidgetBehavior {

	@Override
	public void setRequire(RequireDojoLibs libs) {
		libs.add("dojo.widget.YahooMap");
	}

}
