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


public final class SecondaryLoginContext extends LoginContext
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SecondaryLoginContext()
	{
		super(1);
	}

	public boolean isClassAuthenticated(Class class1)
	{
		return true;
		// we also could just return true if the class is a HighSecurityPage
		// if we did that we would have to login again for a "normal" page
		// now the 2nd login is good for all pages
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
		defaultSubject.addPrincipal(new TestPrincipal("admin"));
		return defaultSubject;
	}
}