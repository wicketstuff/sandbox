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
import org.apache.wicket.security.hive.authentication.LoginContainer;
import org.apache.wicket.security.hive.authentication.LoginContext;
import org.apache.wicket.security.hive.authentication.SingleLoginContext;
import org.apache.wicket.security.hive.authentication.Subject;
import org.apache.wicket.security.hive.authorization.TestPrincipal;
import org.apache.wicket.security.pages.VerySecurePage;
import org.apache.wicket.security.strategies.LoginException;

import junit.framework.TestCase;

/**
 * @author marrink
 */
public class LoginTest extends TestCase
{
	private static final org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory
			.getLog(LoginTest.class);

	/**
	 * Test method for
	 * {@link org.apache.wicket.security.hive.authentication.LoginContainer#login(org.apache.wicket.security.hive.authentication.LoginContext)}.
	 */
	public void testLogin()
	{
		LoginContainer container = new LoginContainer();
		try
		{
			container.login(null);
			fail("LoginContext is required");
		}
		catch (LoginException e)
		{
		}
		LoginContext ctx = new SingleLoginContext()
		{

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public Subject login()
			{
				Subject subject = new DefaultSubject();
				subject.addPrincipal(new TestPrincipal("home"));
				return subject;
			}
		};
		assertFalse(container.isClassAuthenticated(getClass()));
		try
		{
			container.login(ctx);
		}
		catch (LoginException e)
		{
			log.error(e, e);
			fail(e.getMessage());
		}
		assertNotNull(container.getSubject());
		assertTrue(container.isClassAuthenticated(getClass()));
		// shows that even though the new context does not authenticate anything the
		// previous one does
		ctx = new LoginContext(1)
		{
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public boolean isClassAuthenticated(Class class1)
			{
				return false;
			}

			public boolean isComponentAuthenticated(Component component)
			{
				return false;
			}

			public boolean isModelAuthenticated(IModel model, Component component)
			{
				return false;
			}

			public Subject login() throws LoginException
			{
				return new DefaultSubject();
			}
		};
		try
		{
			container.login(ctx);
		}
		catch (LoginException e)
		{
			log.error(e, e);
			fail(e.getMessage());
		}
		assertTrue(container.isClassAuthenticated(getClass()));
		// note changing the order does not matter since the first that authenticates true
		// is used.
	}

	/**
	 * Test method for
	 * {@link org.apache.wicket.security.hive.authentication.LoginContainer#logoff(org.apache.wicket.security.hive.authentication.LoginContext)}.
	 */
	public void testLogoff()
	{
		LoginContainer container = new LoginContainer();
		LoginContext ctx = new SingleLoginContext()
		{

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public Subject login() throws LoginException
			{
				return new DefaultSubject();
			}
		};
		try
		{
			container.login(ctx);
		}
		catch (LoginException e)
		{
			log.error(e, e);
			fail(e.getMessage());
		}
		assertNotNull(container.getSubject());
		container.logoff(ctx);
		assertNull(container.getSubject());
	}

	/**
	 * Test method for multi login.
	 */
	public void testIsClassAuthenticated()
	{
		// for multilogin to work the least authenticating login should be at the bottom
		LoginContainer container = new LoginContainer();
		LoginContext low = new PrimaryLoginContext();
		LoginContext high = new SecondaryLoginContext();
		try
		{
			container.login(low);
			assertFalse(container.isClassAuthenticated(VerySecurePage.class));
			assertTrue(container.getSubject().getPrincipals().contains(new TestPrincipal("basic")));
			assertFalse(container.getSubject().getPrincipals().contains(new TestPrincipal("admin")));
			container.login(high);
			assertTrue(container.isClassAuthenticated(VerySecurePage.class));
			assertTrue(container.getSubject().getPrincipals().contains(new TestPrincipal("admin")));
		}
		catch (LoginException e)
		{
			log.error(e, e);
			fail(e.getMessage());
		}
	}
}
