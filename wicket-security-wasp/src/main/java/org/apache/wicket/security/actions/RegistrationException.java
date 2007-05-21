/*
 * $Id$
 * $Revision$
 * $Date$
 *
 * ====================================================================
 * Copyright (c) 2005, Topicus B.V.
 * All rights reserved.
 */
package org.apache.wicket.security.actions;

/**
 * Exception throw when there is a problem registering a new action. See the cause for
 * more information.
 * @author marrink
 */
public class RegistrationException extends Exception
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param message
	 */
	public RegistrationException(String message)
	{
		super(message);
	}

	/**
	 * @param cause
	 */
	public RegistrationException(Throwable cause)
	{
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public RegistrationException(String message, Throwable cause)
	{
		super(message, cause);
	}

}
