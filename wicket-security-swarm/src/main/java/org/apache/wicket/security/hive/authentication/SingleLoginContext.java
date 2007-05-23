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

import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;

/**
 * LoginContext for Applications with a signle login. This context always operates at
 * level 0 and authenticates every class, component or model. Eventhough there is nothing
 * stopping you from login in multiple times with additional (other) contexts, there realy
 * is no point because the application will always think the user is already sufficient
 * authenticated.
 * @author marrink
 */
public abstract class SingleLoginContext extends LoginContext
{

	/**
	 * Constructs a new login context, designed to be used as the only context for this
	 * session, at level 0.
	 */
	public SingleLoginContext()
	{
		super(0);
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
}
