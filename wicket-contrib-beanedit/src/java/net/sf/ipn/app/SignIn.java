/*
 * Created on Mar 21, 2005
 */
package net.sf.ipn.app;

import net.sf.ipn.data.User;
import wicket.PageParameters;
import wicket.markup.html.WebPage;

/**
 * Simple example of a sign in page.
 * @author Jonathan Locke
 */
public final class SignIn extends WebPage
{
	/**
	 * Constructor
	 * @param parameters The page parameters
	 */
	public SignIn(final PageParameters parameters)
	{
		SignInPanel panel = new SignInPanel("signInPanel")
		{
			public boolean signIn(final String username, final String password)
			{
				IpnSession session = (IpnSession)getSession();
				// Sign the user in
				final User user = session.authenticate(username, password);

				// If the user was signed in
				if (session.isSignedIn())
					return true;
				System.out.println("$$$$$$$$$$$$$$$$$$$ this: " + this);
				error(getLocalizer().getString("couldNotAuthenticate", this));
				return false;
			}
		};
		add(panel);
		// add(new Label("signInPanel", "This is the SignIn page."));
	}

	/**
	 * Constructor
	 */
	public SignIn()
	{
		this(null);
	}

}