/*
 * $Id$
 * $Revision$
 * $Date$
 *
 * ====================================================================
 * Copyright (c) 2005, Topicus B.V.
 * All rights reserved.
 */
package org.apache.wicket.security.checks;

import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.security.WaspApplication;
import org.apache.wicket.security.actions.AbstractWaspAction;
import org.apache.wicket.security.actions.WaspAction;
import org.apache.wicket.security.strategies.ClassAuthorizationStrategy;
import org.apache.wicket.security.strategies.WaspAuthorizationStrategy;

/**
 * Default instantiation check for any type of class. This is used by
 * {@link ClassAuthorizationStrategy} to test for instantiation rights. But you can use it
 * yourself to for any kind of action. Note that errorpages should not be outfitted with a
 * securitycheck such as this that checks for instantiation.
 * @author marrink
 * @see ClassAuthorizationStrategy
 */
public class ClassSecurityCheck extends AbstractSecurityCheck
{
	private static final long serialVersionUID = 1L;

	private Class clazz;

	/**
	 * Constructs a new securitycheck for a class
	 * @param clazz the class to use in the check
	 * @throws IllegalArgumentException if the clazz is null
	 */
	public ClassSecurityCheck(Class clazz)
	{
		this.clazz = clazz;
		if (clazz == null)
			throw new IllegalArgumentException("clazz is null");
	}

	/**
	 * The class to check against.
	 * @return Returns the clazz.
	 */
	public Class getClazz()
	{
		return clazz;
	}

	/**
	 * Checks if the user is authorized for the action. special permission is given to the
	 * loginpage, which is always authorized. If the user is not authenticated he is
	 * redirected to the login page. Redirects the authorization check to the strategy if
	 * the user is authenticated.
	 * @return true if the user is authenticated and authorized, false otherwise.
	 * @see org.apache.wicket.security.checks.ISecurityCheck#isActionAuthorized(org.apache.wicket.security.actions.AbstractWaspAction)
	 * @see WaspApplication#getLoginPage()
	 * @see WaspAuthorizationStrategy#isClassAuthorized(Class, AbstractWaspAction)
	 * @throws RestartResponseAtInterceptPageException if the user is not authenticated.
	 */
	public boolean isActionAuthorized(WaspAction action)
	{
		if (getClazz() == getLoginPage())
			return true;
		if (isAuthenticated())
			return getStrategy().isClassAuthorized(getClazz(), action);
		throw new RestartResponseAtInterceptPageException(getLoginPage());

	}

	/**
	 * Redirects to the {@link WaspAuthorizationStrategy#isClassAuthenticated(Class)}.
	 * @see org.apache.wicket.security.checks.ISecurityCheck#isAuthenticated()
	 */
	public boolean isAuthenticated()
	{
		return getStrategy().isClassAuthenticated(getClazz());
	}

}
