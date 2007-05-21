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
 * Exception thrown when an unrecoverable problem arrises.
 * @author marrink
 *
 */
public class SecurityException extends RuntimeException
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public SecurityException()
	{
	}

	/**
	 * @param message
	 */
	public SecurityException(String message)
	{
		super(message);
	}

	/**
	 * @param cause
	 */
	public SecurityException(Throwable cause)
	{
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public SecurityException(String message, Throwable cause)
	{
		super(message, cause);
	}

}
