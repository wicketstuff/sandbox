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

import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;

/**
 * LoginContext for Applications with a signle login. This context always
 * operates at level 0 and authenticates every class, component or model. By
 * default this context prevents additional logins.
 * 
 * @author marrink
 */
public abstract class SingleLoginContext extends LoginContext
{
	private boolean additionalLoginsPrevented = true;

	/**
	 * Constructs a new login context, designed to be used as the only context
	 * for this session, at level 0.
	 */
	public SingleLoginContext()
	{
		super(0);
	}

	/**
	 * Constructor that will allow additional logins. Note that there realy is
	 * no good reason to want this other then for testing purposes.
	 * 
	 * @param allowAdditionalLogins
	 *            flag if additional logins are allowed (to grant more rights)
	 */
	protected SingleLoginContext(boolean allowAdditionalLogins)
	{
		super(0);
		additionalLoginsPrevented = !allowAdditionalLogins;
	}

	/**
	 * @see org.apache.wicket.security.hive.authentication.LoginContext#isClassAuthenticated(java.lang.Class)
	 */
	public boolean isClassAuthenticated(Class class1)
	{
		return true;
	}

	/**
	 * @see org.apache.wicket.security.hive.authentication.LoginContext#isComponentAuthenticated(wicket.Component)
	 */
	public boolean isComponentAuthenticated(Component component)
	{
		return true;
	}

	/**
	 * @see org.apache.wicket.security.hive.authentication.LoginContext#isModelAuthenticated(wicket.model.IModel,
	 *      wicket.Component)
	 */
	public boolean isModelAuthenticated(IModel model, Component component)
	{
		return true;
	}

	/**
	 * @see org.apache.wicket.security.hive.authentication.LoginContext#preventsAdditionalLogins()
	 */
	public boolean preventsAdditionalLogins()
	{
		return additionalLoginsPrevented;
	}

}
