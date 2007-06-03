/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.wicket.security.hive.authentication;

import java.io.Serializable;

import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;
import org.apache.wicket.security.strategies.LoginException;
import org.apache.wicket.security.strategies.WaspAuthorizationStrategy;
import org.apache.wicket.security.swarm.strategies.SwarmStrategy;

/**
 * A LoginContext has al the information to log someone in and check which
 * Components, classes or models are authenticated by this subject. Because a
 * {@link SwarmStrategy} allows multiple logins a level is required. where-in
 * the higher levels get queried first by the {@link LoginContainer}. Note that
 * a logincontext does not need a logincontainer to function, you are welcome to
 * subclass SwarmStrategy to use a single logincontext for authentication if you
 * really want to :).
 * 
 * @author marrink
 * @see #preventsAdditionalLogins()
 */
public abstract class LoginContext implements Comparable, Serializable
{
	private int level;

	/**
	 * Constructs a new context at the specified level. levels go from 0 upward.
	 * 
	 * @param level
	 */
	public LoginContext(int level)
	{
		this.level = level;
		if (level < 0)
			throw new IllegalArgumentException("0 is the lowest level allowed, not " + level);
	}

	/**
	 * Perform a login. If the login fails in any way a {@link LoginException}
	 * must be thrown rather then returning null.
	 * 
	 * @return a {@link Subject}, never null.
	 */
	public abstract Subject login() throws LoginException;

	/**
	 * Performs the authentication check on a class.
	 * 
	 * @param class1
	 * @return true if the class is authenticated, false otherwise.
	 * @see WaspAuthorizationStrategy#isClassAuthenticated(Class)
	 */
	public abstract boolean isClassAuthenticated(Class class1);

	/**
	 * Performs the authentication check on a component.
	 * 
	 * @param component
	 * @return true if the component is authenticated, false otherwise
	 * @see WaspAuthorizationStrategy#isComponentAuthenticated(Component)
	 */
	public abstract boolean isComponentAuthenticated(Component component);

	/**
	 * Performs the authentication check on a model.
	 * 
	 * @param model
	 * @param component
	 * @return true if the model is authenticated, false otherwise
	 * @see WaspAuthorizationStrategy#isModelAuthenticated(IModel, Component)
	 */
	public abstract boolean isModelAuthenticated(IModel model, Component component);

	/**
	 * Indicates the level of this context. the higher the level the more you
	 * are authorised / authenticated for.
	 * 
	 * @return the level
	 */
	protected final int getLevel()
	{
		return level;
	}

	/**
	 * Compares contexts by level.
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(Object arg0)
	{
		if (arg0 instanceof LoginContext)
		{
			LoginContext lc0 = (LoginContext)arg0;
			return getLevel() - lc0.getLevel();
		}
		throw new IllegalArgumentException("Can only compare with " + LoginContext.class
				+ " not with " + arg0);
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
	 * A loginContext is equal to a LoginContext of the same class (not
	 * subclass) and level.
	 * 
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
		final LoginContext other = (LoginContext)obj;
		return level == other.level;
	}

	/**
	 * Signals to the {@link LoginContainer} that no additional context should
	 * be allowed to login. This flag is checked once by the container
	 * inmediatly after {@link #login()}. Note in a multi login environment you
	 * will want your logincontext with the highest level to prevent additional
	 * logins. In a single login environment your logincontext should always
	 * prevent additional logins (as {@link SingleLoginContext} does.
	 * 
	 * @return true if you do not want additional logins for this session, false
	 *         otherwise.
	 */
	public abstract boolean preventsAdditionalLogins();
}
