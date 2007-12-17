package org.wicketstuff.teatime.secure;

import org.apache.wicket.PageParameters;
import org.wicketstuff.teatime.BasePage;

/**
 * Page for administration of the bot.
 */
public class SignInPage extends BasePage {
	private static final long serialVersionUID = 1L;

	public SignInPage(PageParameters parameters) {
		super(parameters);
		add(new UserPanel("login"));
	}

	public String getTitle() {
		return "Login page for Wicket logbot";
	}
}
