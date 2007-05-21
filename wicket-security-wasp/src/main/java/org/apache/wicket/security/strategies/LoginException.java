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
 * @author marrink
 *
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

	public Object getLoginContext()
	{
		return loginContext;
	}

	public void setLoginContext(Object loginContext)
	{
		this.loginContext = loginContext;
	}

}
