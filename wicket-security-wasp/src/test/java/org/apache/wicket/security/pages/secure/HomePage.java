/*
 * $Id: HomePage.java,v 1.1 2007/05/15 14:44:25 marrink Exp $
 * $Revision: 1.1 $
 * $Date: 2007/05/15 14:44:25 $
 *
 * ====================================================================
 * Copyright (c) 2007, Topicus B.V.
 * All rights reserved.
 */
package org.apache.wicket.security.pages.secure;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.security.checks.InverseSecurityCheck;
import org.apache.wicket.security.components.SecureComponentHelper;
import org.apache.wicket.security.components.markup.html.links.SecurePageLink;
import org.apache.wicket.security.pages.SecureTestPage;


/**
 * @author marrink
 *
 */
public class HomePage extends SecureTestPage
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public HomePage()
	{
		add(new Label("welcome","Welcome Only logged in users can see this page"));
		SecurePageLink securePageLink = new SecurePageLink("link",PageA.class);
		add(securePageLink);
		add(SecureComponentHelper.setSecurityCheck(new Label("sorry","you are not allowed to go to Page A"),new InverseSecurityCheck(securePageLink.getSecurityCheck())));
	}

}
