package org.wicketstuff.teatime.logbot;

import org.apache.wicket.PageParameters;
import org.wicketstuff.teatime.BasePage;

/**
 * 
 */
public class StatusPage extends BasePage {
	private static final long serialVersionUID = 1L;

	public StatusPage(PageParameters pars) {
		super(pars);
		add(new LogBotPanel("panel"));
	}
}
