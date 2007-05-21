/*
 * $Id$
 * $Revision$
 * $Date$
 *
 * ====================================================================
 * Copyright (c) 2005, Topicus B.V.
 * All rights reserved.
 */
package org.apache.wicket.security.strategies;

/**
 * Thrown when an exception arrises during the login.
 * @author marrink
 * @see WaspAuthorizationStrategy#login(Object)
 */
public class LoginException extends Exception
{
	private Object loginContext;

	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public LoginException()
	{
	}

	/**
	 * @param message
	 */
	public LoginException(String message)
	{
		super(message);
	}

	/**
	 * @param cause
	 */
	public LoginException(Throwable cause)
	{
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public LoginException(String message, Throwable cause)
	{
		super(message, cause);
	}

	/**
	 * The login context that cause the exception.
	 * @return login context
	 */
	public Object getLoginContext()
	{
		return loginContext;
	}

	/**
	 * Set the login context that caused the problem.
	 * @param loginContext
	 * @return this exception
	 */
	public LoginException setLoginContext(Object loginContext)
	{
		this.loginContext = loginContext;
		return this;
	}

}
