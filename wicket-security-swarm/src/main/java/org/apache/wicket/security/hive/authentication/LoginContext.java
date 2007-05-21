/*
 * $Id$
 * $Revision$
 * $Date$
 *
 * ====================================================================
 * Copyright (c) 2005, Topicus B.V.
 * All rights reserved.
 */
package org.apache.wicket.security.hive.authentication;

import java.io.Serializable;

import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;
import org.apache.wicket.security.strategies.LoginException;
import org.apache.wicket.security.swarm.strategies.SwarmStrategy;


/**
 * A LoginContext has al the information to log someone in and check which Components,
 * classes or models are authenticated by this subject. Because a {@link SwarmStrategy}
 * allows multiple logins a level is required. wherein the higher levels get queried
 * first.
 * @author marrink
 */
public abstract class LoginContext implements Comparable, Serializable
{
	private int level;

	/**
	 * Constructs a new context at the specified level.
	 * @param level
	 */
	public LoginContext(int level)
	{
		this.level = level;
		if (level < 0)
			throw new IllegalArgumentException("0 is the lowest level allowed, not " + level);
	}

	/**
	 * Perform a login. If the login fails in any way a {@link LoginException} must be
	 * thrown rather then returning null.
	 * @return a {@link Subject}, never null.
	 */
	public abstract Subject login() throws LoginException;

	public abstract boolean isClassAuthenticated(Class class1);

	public abstract boolean isComponentAuthenticated(Component component);

	public abstract boolean isModelAuthenticated(IModel model, Component component);

	protected final int getLevel()
	{
		return level;
	}

	/**
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(Object arg0)
	{
		if (arg0 instanceof LoginContext)
		{
			LoginContext lc0 = (LoginContext) arg0;
			return getLevel() - lc0.getLevel();
		}
		throw new IllegalArgumentException("Can only compare with "
				+ LoginContext.class + " not with " + arg0);
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode()
	{
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + getClass().hashCode();
		result = PRIME * result + level;
		return result;
	}

	/**
	 * A loginContext is equal to a LoginContext of the same class (not subclass) and
	 * level.
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final LoginContext other = (LoginContext) obj;
		return level == other.level;
	}

}
