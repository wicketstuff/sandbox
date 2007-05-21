/*
 * $Id: InverseSecurityCheck.java,v 1.1 2006/10/20 16:00:01 Marrink Exp $
 * $Revision: 1.1 $
 * $Date: 2006/10/20 16:00:01 $
 *
 * ====================================================================
 * Copyright (c) 2005, Topicus B.V.
 * All rights reserved.
 */
package org.apache.wicket.security.checks;

import org.apache.wicket.security.actions.AbstractWaspAction;
import org.apache.wicket.security.actions.WaspAction;

/**
 * SecurityCheck that says you are authorized when in fact you are not and vice versa. The
 * authentication check always returns the correct result. This is convenient when you
 * want to display a component (like a message to the user) when the user is not
 * authorized.
 * @author marrink
 */
public class InverseSecurityCheck implements ISecurityCheck
{
	private static final long serialVersionUID = 1L;

	private ISecurityCheck wrapped;

	/**
	 * Constructs a new SecurityCheck that will invert the result from the provided
	 * security check.
	 * 
	 * @param wrapped
	 */
	public InverseSecurityCheck(ISecurityCheck wrapped)
	{
		if(wrapped==null)
			throw new IllegalArgumentException("Need ISecurityCheck to invert");
		this.wrapped = wrapped;
	}

	/**
	 * Returns false if the user is authorized and true if the user is not authorized.
	 * 
	 * @see wicket.jaas.checks.ISecurityCheck#isActionAuthorized(AbstractWaspAction)
	 */
	public boolean isActionAuthorized(WaspAction action)
	{
		return !wrapped.isActionAuthorized(action);
	}

	/**
	 * @see wicket.jaas.checks.ISecurityCheck#isAuthenticated()
	 */
	public boolean isAuthenticated()
	{
		return wrapped.isAuthenticated();
	}

}
