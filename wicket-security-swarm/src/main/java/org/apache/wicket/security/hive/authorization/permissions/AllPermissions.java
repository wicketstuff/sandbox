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

import org.apache.wicket.security.actions.ActionFactory;
import org.apache.wicket.security.hive.authorization.Permission;

/**
 * Permission that implies all other permissions, when created with the "all"
 * action. If any other action then the "all" action is used only
 * {@link ActionPermission}s implying those actions are implied. Permissions
 * that do not subclass the {@link ActionPermission} are always implied.
 * 
 * Note that actions registered with the {@link ActionFactory} after this
 * permission was created are not automatically implied.
 * 
 * @author marrink
 */
public class AllPermissions extends ActionPermission
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Preferred constructor. All other permissions are implied by this
	 * permission. Equal to using <code>new AllPermissions(name,"all");</code>
	 * or not specifying the action part in the policy file.
	 * 
	 * @param name
	 *            the name of this permission.
	 */
	public AllPermissions(String name)
	{
		this(name, "all");
	}

	/**
	 * Construct.
	 * 
	 * @param name
	 * @param actions
	 */
	public AllPermissions(String name, String actions)
	{
		super(name, getAction(actions));
	}

	/**
	 * Implies every other permission, but the other way around is not
	 * necessarily true. One exception, if this action was not initiated with
	 * the 'all' action it only implies other {@link ActionPermission}s who's
	 * actions are implied by this permission's actions.
	 * 
	 * @see org.apache.wicket.security.hive.authorization.permissions.ActionPermission#implies(org.apache.wicket.security.hive.authorization.Permission)
	 */
	public boolean implies(Permission permission)
	{
		if (permission instanceof ActionPermission)
		{
			ActionPermission other = (ActionPermission)permission;
			return getAction().implies(other.getAction());
		}
		return true;
	}

}
