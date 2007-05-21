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
import org.apache.wicket.security.hive.authentication.DefaultSubject;
import org.apache.wicket.security.hive.authentication.LoginContext;
import org.apache.wicket.security.hive.authentication.Subject;
import org.apache.wicket.security.hive.authorization.TestPrincipal;
import org.apache.wicket.security.pages.HighSecurityPage;


public final class PrimaryLoginContext extends LoginContext
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PrimaryLoginContext()
	{
		super(0);
	}

	public boolean isClassAuthenticated(Class class1)
	{
		// for this test class authentication is enough
		if (class1 == null)
			return false;
		return !HighSecurityPage.class.isAssignableFrom(class1);
	}

	public boolean isComponentAuthenticated(Component component)
	{
		return true;
	}

	public boolean isModelAuthenticated(IModel model, Component component)
	{
		return true;
	}

	public Subject login()
	{
		DefaultSubject defaultSubject = new DefaultSubject();
		defaultSubject.addPrincipal(new TestPrincipal("basic"));
		return defaultSubject;
	}
}