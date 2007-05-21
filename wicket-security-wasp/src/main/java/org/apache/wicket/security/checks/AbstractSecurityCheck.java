/*
 * $Id: AbstractSecurityCheck.java,v 1.1 2006/02/24 08:33:05 Marrink Exp $ $Revision: 1.1 $ $Date: 2006/02/24 08:33:05 $ ==================================================================== Copyright (c) 2005,
 * Topicus B.V. All rights reserved.
 */

package org.apache.wicket.security.checks;

import org.apache.wicket.Application;
import org.apache.wicket.security.WaspApplication;
import org.apache.wicket.security.WaspSession;
import org.apache.wicket.security.actions.AbstractWaspAction;
import org.apache.wicket.security.actions.ActionFactory;
import org.apache.wicket.security.strategies.WaspAuthorizationStrategy;


/**
 * Basic check providing some utility methods.
 * @author marrink
 */
public abstract class AbstractSecurityCheck implements ISecurityCheck
{
	/**
	 * Noop constructor for Serialization.
	 *
	 */
	protected AbstractSecurityCheck(){}
	/**
	 * Utility method to get the factory to create {@link AbstractWaspAction}s.
	 * @return the factory
	 * @see WaspApplication#getActionFactory()
	 */
	protected final ActionFactory getActionFactory()
	{
		return ((WaspApplication) Application.get()).getActionFactory();
	}
	/**
	 * Utility method to get to the {@link WaspAuthorizationStrategy}.
	 * @return the strategy
	 * @see WaspSession#getAuthorizationStrategy()
	 */
	protected final WaspAuthorizationStrategy getStrategy()
	{
		return (WaspAuthorizationStrategy)((WaspSession)WaspSession.get()).getAuthorizationStrategy();
	}
	/**
	 * Utility meyhod to get the class of the loginpage.
	 * @return the login page
	 * @see WaspApplication#getLoginPage()
	 */
	protected final Class getLoginPage()
	{
		return ((WaspApplication)Application.get()).getLoginPage();
	}
}
