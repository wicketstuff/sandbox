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

import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.security.actions.Render;
import org.apache.wicket.security.actions.WaspAction;
import org.apache.wicket.security.checks.ClassSecurityCheck;
import org.apache.wicket.security.checks.ISecurityCheck;
import org.apache.wicket.security.components.SecureWebPage;

/**
 * Shows how to overide the default instantiation check.
 * Although we only show instantiation checks based on classes you could ofcource use something different like strings.
 * @author marrink
 */
public class PageC extends SecureWebPage //or SecureTestPage, really does not matter
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiation check that requires render rights instead of the default access rights.
	 */
	static final ISecurityCheck alternate = new ClassSecurityCheck(PageC.class)
	{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * @see org.apache.wicket.security.checks.ClassSecurityCheck#isActionAuthorized(org.apache.wicket.security.actions.WaspAction)
		 */
		public boolean isActionAuthorized(WaspAction action)
		{
			if (isAuthenticated())
				return getStrategy().isClassAuthorized(getClazz(), action.add(getActionFactory().getAction(Render.class)));
			throw new RestartResponseAtInterceptPageException(getLoginPage());
		}

	};

	/**
	 * 
	 */
	public PageC()
	{
		add(new Label("welcome", "Demonstrates alternate instantiation check"));
	}

}
