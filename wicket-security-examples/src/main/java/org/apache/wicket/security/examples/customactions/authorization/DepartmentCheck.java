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
package org.apache.wicket.security.examples.customactions.authorization;

import org.apache.wicket.Component;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.security.actions.WaspAction;
import org.apache.wicket.security.checks.ComponentSecurityCheck;
import org.apache.wicket.security.components.SecureComponentHelper;
import org.apache.wicket.security.examples.customactions.entities.Department;
import org.apache.wicket.security.models.ISecureModel;

/**
 * @author marrink
 */
public class DepartmentCheck extends ComponentSecurityCheck
{
	private static final long serialVersionUID = 1L;

	// Note always have some way of detaching your bussiness entities, unlike
	// this example
	private Department department;

	/**
	 * Construct.
	 * 
	 * @param component
	 */
	public DepartmentCheck(Component component, Department department)
	{
		super(component);
		this.department = department;
	}

	/**
	 * Construct.
	 * 
	 * @param component
	 * @param checkSecureModelIfExists
	 */
	public DepartmentCheck(Component component, boolean checkSecureModelIfExists)
	{
		super(component, checkSecureModelIfExists);
	}

	/**
	 * @see org.apache.wicket.security.checks.ComponentSecurityCheck#isActionAuthorized(org.apache.wicket.security.actions.WaspAction)
	 */
	public boolean isActionAuthorized(WaspAction action)
	{
		if (!isAuthenticated())
			throw new RestartResponseAtInterceptPageException(getLoginPage());
		//for secure departments you need organization rights, else department rights are sufficient
		WaspAction myAction = action.add(getActionFactory().getAction(
				department.secure ? Organization.class : org.apache.wicket.security.examples.customactions.authorization.Department.class));
		boolean result = getStrategy().isComponentAuthorized(getComponent(), myAction);
		if (result && checkSecureModel() && SecureComponentHelper.hasSecureModel(getComponent()))
			return ((ISecureModel)getComponent().getModel()).isAuthorized(getComponent(), myAction);
		return result;
	}

}
