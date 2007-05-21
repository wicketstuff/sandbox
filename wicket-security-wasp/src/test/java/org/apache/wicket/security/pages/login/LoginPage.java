/*
 * $Id: LoginPage.java,v 1.20 2007/05/04 11:00:01 hoeve Exp $ $Revision: 1.20 $ $Date: 2007/05/04 11:00:01 $
 * ==================================================================== Copyright (c) 2005, Topicus B.V. All rights
 * reserved.
 */

package org.apache.wicket.security.pages.login;

import org.apache.wicket.security.pages.BasePage;

public class LoginPage extends BasePage
{

	private static final long serialVersionUID = 1L;


	/**
	 * Constructor.
	 */
	public LoginPage()
	{
		String panelId = "signInPanel";
		newUserPasswordSignInPanel(panelId);
	}

	/**
	 * Creeert een sign in panel voor instellingen die hun authenticatie enkel baseren op
	 * username/wachtwoord.
	 * @param panelId
	 * @param info
	 */
	private void newUserPasswordSignInPanel(String panelId)
	{
		add(new UsernamePasswordSignInPanel(panelId));
	}
}
