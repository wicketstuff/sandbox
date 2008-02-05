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
package org.apache.wicket.security.hive.authorization.permissions;

import org.apache.wicket.security.actions.Inherit;
import org.apache.wicket.security.hive.authorization.Permission;
import org.apache.wicket.security.hive.config.HiveFactory;
import org.apache.wicket.security.swarm.actions.SwarmAction;


/**
 * Base class for any Permission that uses actions. Each implementation of
 * ActionPermission should at least expose the ActionPermission(String name,
 * SwarmAction actions) constructor to the outside world, for it will be used by
 * a {@link HiveFactory} when constructing permissions. Note if you do not wish
 * to use actions in your permissions you should build your own permissions.
 * 
 * @author marrink
 */
public class ActionPermission extends Permission
{

	private static final long serialVersionUID = 1L;

	private SwarmAction actions;

	/**
	 * Creates a new ActionPermission with the specified name and actions.
	 * 
	 * @param name
	 *            non null name of the permission
	 * @param actions
	 *            a logical and of all the allowed / required actions for this
	 *            permission
	 */
	protected ActionPermission(String name, SwarmAction actions)
	{
		super(name);
		if (name == null)
			throw new IllegalArgumentException("Name is required.");
		if (actions == null)
			throw new IllegalArgumentException("Actions is required.");
		this.actions = actions;
		// SwarmAction is immutable
	}

	/**
	 * Performs a logical and to see if this permission has at least all the
	 * actions as the other permission and thus if this permission implies the
	 * other permission. Inherited actions are considered.
	 * 
	 * <pre>
	 *  Some basic rules about implies:
	 * <li>
	 *  if 2 permissions are equal both should imply the other.
	 * </li>
	 * <li>
	 *  if 2 permissions are not equal they might still imply each other, or 1 might implies the other but not vice versa
	 * </li>
	 * <li>
	 *  a permission should imply another permission if they are of the same class and the first has at least,
	 *      all the actions of the second permission and they have the same name.
	 * </li>
	 * <li>
	 *  a permission should imply another permission if there is some kind of hierarchical structure between them
	 *       and the other permission is able to inherit the required actions from the first permission.
	 *  
	 *  To further clarify the part about inheritance. This implementation implies any type of ActionPermission under the following conditions.
	 * <li>
	 *  Equal name and actions
	 * </li>
	 * <li>
	 *  This permission has the inherit action and all or more of the other permissions actions
	 *     and the name of the other permission starts with the name of this permission.
	 * </li>
	 *  Subclasses like ComponentPermission that only wish to imply other ComponentPermissions could simply override implies like this.
	 * <code>
	 * if (permission instanceof ComponentPermission)
	 * 	return super.implies(permission);
	 * return false;
	 * </code>
	 *  Note that this does not prevent other subclasses from ActionPermission to imply your permission (as stated above).
	 * </pre>
	 * 
	 * @see Permission#implies(Permission)
	 */
	public boolean implies(Permission permission)
	{
		if (permission instanceof ActionPermission)
		{
			ActionPermission other = (ActionPermission)permission;
			if (actions.implies(actions.getActionFactory().getAction(Inherit.class)))
				return actions.implies(other.actions) && other.getName().startsWith(getName());
			return actions.implies(other.actions) && getName().equals(other.getName());
		}
		return false;
	}

	/**
	 * ActionPermissions are only equal if they have the same class, name and
	 * actions.
	 * 
	 * @see Permission#equals(java.lang.Object)
	 * 
	 */
	public boolean equals(Object obj)
	{
		if (obj != null && obj.getClass().equals(getClass()))
		{
			ActionPermission other = (ActionPermission)obj;
			return other.getName().equals(getName())
					&& other.actions.actions() == actions.actions();
			// both fields are not null
		}
		return false;
	}

	/**
	 * generates a hashcode including the classname.
	 * 
	 * @see Permission#hashCode()
	 */
	public int hashCode()
	{
		int result = 4679;
		result = 37 * result + getClass().getName().hashCode();
		result = 37 * result + getName().hashCode();
		result = 37 * result + actions.hashCode();
		return result;
	}

	/**
	 * A logically ordered comma separated string containing each action this
	 * permission has.
	 * 
	 * @see Permission#getActions()
	 */
	public final String getActions()
	{
		return actions.getName();
	}

	/**
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		return getClass().getName() + " \"" + getName() + "\" \"" + getActions() + "\"";
	}

	/**
	 * Check if an action is implied by this permission.
	 * 
	 * @param myActions
	 *            the internal value of the action
	 * @return true if the action is implied, false otherwise
	 */
	public boolean hasAction(int myActions)
	{
		return this.actions.implies(myActions);
	}

	/**
	 * The internal representation of this action.
	 * 
	 * @return the action
	 */
	protected final SwarmAction getAction()
	{
		return actions;
	}
}
