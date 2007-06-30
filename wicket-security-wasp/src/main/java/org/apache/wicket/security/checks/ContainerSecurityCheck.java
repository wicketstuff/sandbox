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
package org.apache.wicket.security.checks;

import org.apache.wicket.MarkupContainer;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.security.actions.WaspAction;
import org.apache.wicket.security.components.SecureComponentHelper;
import org.apache.wicket.security.models.ISecureModel;

/**
 * Security check for when you replace panels on a page instead of using new
 * pages. This is done by checking if the panel / container class is authorized
 * just like it does with pages.
 * 
 * @author marrink
 */
public class ContainerSecurityCheck extends ComponentSecurityCheck
{

	private static final long serialVersionUID = 1L;

	/**
	 * Construct.
	 * 
	 * @param component
	 */
	public ContainerSecurityCheck(MarkupContainer component)
	{
		super(component);
	}

	/**
	 * Construct.
	 * 
	 * @param component
	 * @param checkSecureModelIfExists
	 */
	public ContainerSecurityCheck(MarkupContainer component, boolean checkSecureModelIfExists)
	{
		super(component, checkSecureModelIfExists);
	}

	/**
	 * Checks the container class.
	 * 
	 * @see org.apache.wicket.security.checks.ComponentSecurityCheck#isActionAuthorized(org.apache.wicket.security.actions.WaspAction)
	 */
	public boolean isActionAuthorized(WaspAction action)
	{
		if (!isAuthenticated())
			throw new RestartResponseAtInterceptPageException(getLoginPage());
		boolean result = getStrategy().isClassAuthorized(getComponent().getClass(), action);
		if (result && checkSecureModel() && SecureComponentHelper.hasSecureModel(getComponent()))
			return ((ISecureModel)getComponent().getModel()).isAuthorized(getComponent(), action);
		return result;
	}
}
