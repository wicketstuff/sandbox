/*
 * $Id$
 * $Revision$
 * $Date$
 *
 * ====================================================================
 * Copyright (c) 2005, Topicus B.V.
 * All rights reserved.
 */
package org.apache.wicket.security.pages;

import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.security.actions.WaspAction;
import org.apache.wicket.security.checks.ClassSecurityCheck;
import org.apache.wicket.security.checks.ISecurityCheck;
import org.apache.wicket.security.components.SecureWebPage;


/**
 * @author marrink
 */
public class VerySecurePage extends SecureWebPage implements HighSecurityPage
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Redirects to the secondaryloginpage
	 */
	static final ISecurityCheck check = new ClassSecurityCheck(VerySecurePage.class)
	{
		private static final long serialVersionUID = 1L;

		/**
		 * @see org.apache.wicket.security.checks.ClassSecurityCheck#isActionAuthorized(org.apache.wicket.security.actions.WaspAction)
		 */
		public boolean isActionAuthorized(WaspAction action)
		{
			if (isAuthenticated())
				return getStrategy().isClassAuthorized(getClazz(), action);
			throw new RestartResponseAtInterceptPageException(SecondaryLoginPage.class);
		}
	};

	/**
	 * 
	 */
	public VerySecurePage()
	{
		add(new Label("msg", "Clark Kent is Superman!"));
	}
}
