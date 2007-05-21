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
 * 
 * @author marrink
 *
 */
public final class EverybodyPrincipal implements Principal
{

	/**
	 * 
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
	 * @see org.apache.wicket.security.hive.authorization.Principal#implies(org.apache.wicket.security.hive.authentication.DefaultSubject)
	 */
	public boolean implies(Subject subject)
	{
		return true;
	}

	public boolean equals(Object obj)
	{
		if(obj==this)
			return true;
		if(obj==null)
			return false;
		return obj.getClass()==this.getClass();
		
	}

	public int hashCode()
	{
		return getClass().hashCode();
	}

	public String toString()
	{
		return getName();
	}

}
