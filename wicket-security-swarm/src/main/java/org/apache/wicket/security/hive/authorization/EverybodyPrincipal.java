/*
 * $Id$
 * $Revision$
 * $Date$
 *
 * ====================================================================
 * Copyright (c) 2005, Topicus B.V.
 * All rights reserved.
 */
package org.apache.wicket.security.hive.authorization;

import org.apache.wicket.security.hive.authentication.Subject;


/**
 * Principal used for permissions granted to everyone, regardless of an authenticated subject.
 * This is the principal used when you specify a grant statement without principal in the policy file.
 * 
 * @author marrink
 *
 */
public final class EverybodyPrincipal implements Principal
{

	/**
	 *  creates a new Principal named "everybody";
	 */
	public EverybodyPrincipal()
	{
	}

	/**
	 * Returns the name everybody.
	 * @see java.security.Principal#getName()
	 */
	public String getName()
	{
		return "everybody";
	}
	/**
	 * Always returns true.
	 * @see org.apache.wicket.security.hive.authorization.Principal#implies(org.apache.wicket.security.hive.authentication.Subject)
	 */
	public boolean implies(Subject subject)
	{
		return true;
	}
	/**
	 * This principal equals every instance of the class.
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj)
	{
		if(obj==this)
			return true;
		if(obj==null)
			return false;
		return obj.getClass()==this.getClass();
		
	}
	/**
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode()
	{
		return getClass().hashCode();
	}

	public String toString()
	{
		return getName();
	}

}
