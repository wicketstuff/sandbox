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
package org.apache.wicket.security.hive.authorization;

import java.io.Serializable;

import org.apache.wicket.Application;
import org.apache.wicket.security.WaspApplication;
import org.apache.wicket.security.actions.ActionFactory;
import org.apache.wicket.security.hive.authorization.permissions.ActionPermission;
import org.apache.wicket.security.swarm.actions.SwarmAction;
import org.apache.wicket.security.swarm.actions.SwarmActionFactory;


/**
 * Abstract class for representing access to a system resource. All permissions
 * have a name (whose interpretation depends on the subclass), as well as
 * abstract functions for defining the semantics of the particular Permission
 * subclass.
 * 
 * <p>
 * Most Permission objects also include an "actions" list that tells the actions
 * that are permitted for the object. For example, for a
 * <code>java.io.FilePermission</code> object, the permission name is the
 * pathname of a file (or directory), and the actions list (such as "read,
 * write") specifies which actions are granted for the specified file (or for
 * files in the specified directory). The actions list is optional for
 * Permission objects, such as <code>java.lang.RuntimePermission</code>, that
 * don't need such a list; you either have the named permission (such as
 * "system.exit") or you don't. See {@link ActionPermission}.
 * 
 * <p>
 * An important method that must be implemented by each subclass is the
 * <code>implies</code> method to compare Permissions. Basically, "permission
 * p1 implies permission p2" means that if one is granted permission p1, one is
 * naturally granted permission p2. Thus, this is not an equality test, but
 * rather more of a subset test.
 * 
 * <P>
 * Permission objects are similar to String objects in that they are immutable
 * once they have been created. Subclasses should not provide methods that can
 * change the state of a permission once it has been created.
 * 
 * All permissions must at least have a public constructor accepting a String
 * parameter which will be used as name for the permission. Note that the
 * {@link ActionPermission} should also have a 2 argument constructor accepting
 * 2 strings where the first is the name and the second a (list of) action(s).
 * 
 * @author marrink
 * 
 */
public abstract class Permission implements Serializable
{
	private final String name;

	/**
	 * Constructs a permission with a certain name.
	 * 
	 * @param name
	 */
	public Permission(String name)
	{
		this.name = name;

	}

	/**
	 * Check if this permission implies the specified permission.
	 * 
	 * @param permission
	 * @return true if this implies the permission, false otherwise.
	 */
	public abstract boolean implies(Permission permission);

	/**
	 * Checks two Permission objects for equality.
	 * <P>
	 * Do not use the <code>equals</code> method for making access control
	 * decisions; use the <code>implies</code> method.
	 * 
	 * @param obj
	 *            the object we are testing for equality with this object.
	 * 
	 * @return true if both Permission objects are equivalent.
	 */

	public abstract boolean equals(Object obj);

	/**
	 * Returns the hash code value for this Permission object.
	 * <P>
	 * The required <code>hashCode</code> behavior for Permission Objects is
	 * the following:
	 * <p>
	 * <ul>
	 * <li>Whenever it is invoked on the same Permission object more than once
	 * during an execution of a Java application, the <code>hashCode</code>
	 * method must consistently return the same integer. This integer need not
	 * remain consistent from one execution of an application to another
	 * execution of the same application.
	 * <p>
	 * <li>If two Permission objects are equal according to the
	 * <code>equals</code> method, then calling the <code>hashCode</code>
	 * method on each of the two Permission objects must produce the same
	 * integer result.
	 * </ul>
	 * 
	 * @return a hash code value for this object.
	 */

	public abstract int hashCode();

	/**
	 * Returns the actions as a String. This is abstract so subclasses can defer
	 * creating a String representation until one is needed. Subclasses should
	 * always return actions in what they consider to be their canonical form.
	 * For example, two FilePermission objects created via the following:
	 * 
	 * <pre>
	 * perm1 = new FilePermission(p1, &quot;read,write&quot;);
	 * perm2 = new FilePermission(p2, &quot;write,read&quot;);
	 * </pre>
	 * 
	 * both return "read,write" when the <code>getActions</code> method is
	 * invoked.
	 * 
	 * @return the actions of this Permission.
	 * 
	 */

	public abstract String getActions();

	/**
	 * Returns the name of this Permission. For example, in the case of a
	 * <code>org.apache.wicket.security.hive.authorization.permissions.ComponentPermission</code>,
	 * the name will be a component path.
	 * 
	 * @return the name of this Permission.
	 * 
	 */
	public final String getName()
	{
		return name;
	}

	/**
	 * Utility method for getting an action.
	 * 
	 * @param action
	 *            the action class
	 * @return the action
	 * @see ActionFactory#getAction(Class)
	 */
	protected static final SwarmAction getAction(Class action)
	{
		return (SwarmAction)((WaspApplication)Application.get()).getActionFactory().getAction(
				action);
	}

	/**
	 * Utility method for getting an action.
	 * 
	 * @param actions
	 *            a bitwise or of the actions
	 * @return the action
	 * @see SwarmActionFactory#getAction(int)
	 */
	protected static final SwarmAction getAction(int actions)
	{
		return ((SwarmActionFactory)((WaspApplication)Application.get()).getActionFactory())
				.getAction(actions);
	}

	/**
	 * Utility method for getting an action.
	 * 
	 * @param actions
	 *            the name of an action
	 * @return the action
	 * @see ActionFactory#getAction(String)
	 */
	protected static final SwarmAction getAction(String actions)
	{
		return (SwarmAction)((WaspApplication)Application.get()).getActionFactory().getAction(
				actions);
	}

}
