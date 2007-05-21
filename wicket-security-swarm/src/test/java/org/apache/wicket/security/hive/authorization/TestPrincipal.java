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
import org.apache.wicket.security.hive.authorization.Principal;

/**
 * @author marrink
 *
 */
public class TestPrincipal implements Principal
{
	private String name;
	
	
	public TestPrincipal(String name)
	{
		super();
		this.name = name;
	}

	/**
	 * @see org.apache.wicket.security.hive.authorization.Principal#getName()
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @see org.apache.wicket.security.hive.authorization.Principal#implies(Subject)
	 */
	public boolean implies(Subject subject)
	{
		return false;
	}

	public String toString()
	{
		return getClass().getName()+": "+getName();
	}

	public int hashCode()
	{
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final TestPrincipal other = (TestPrincipal) obj;
		if (name == null)
		{
			if (other.name != null)
				return false;
		}
		else if (!name.equals(other.name))
			return false;
		return true;
	}

}
