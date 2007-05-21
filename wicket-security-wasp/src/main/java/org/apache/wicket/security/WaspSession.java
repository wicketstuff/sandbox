/*
 * $Id: WaspSession.java,v 1.3 2006/06/28 10:00:16 Marrink Exp $ $Revision: 1.3 $ $Date: 2006/06/28 10:00:16 $
 * ==================================================================== Copyright (c) 2005, Topicus B.V. All rights
 * reserved.
 */

package org.apache.wicket.security;

import org.apache.wicket.Request;
import org.apache.wicket.authorization.IAuthorizationStrategy;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.security.strategies.LoginException;
import org.apache.wicket.security.strategies.WaspAuthorizationStrategy;

/**
 * Session for keeping the session scoped IAuthorizationStrategy and for providing easy
 * access to login, logoff and isAuthenticated.
 * @author marrink
 */
public class WaspSession extends WebSession
{
	private static final long serialVersionUID = 1L;

	private WaspAuthorizationStrategy securityStrategy;

	/**
	 * @param application a webapplication
	 */
	public WaspSession(WaspApplication application, Request request)
	{
		super((WebApplication) application, request);
		securityStrategy = application.getStrategyFactory().newStrategy();
	}

	/**
	 * Returns a session scoped WaspAuthorizationStrategy.
	 * @see wicket.Session#getAuthorizationStrategy()
	 */
	public IAuthorizationStrategy getAuthorizationStrategy()
	{
		return securityStrategy;
	}

	/**
	 * Attempts to login with the current login info.
	 * @param context any type of information required to login
	 * @throws LoginException
	 * @see WaspAuthorizationStrategy#login(Object)
	 */
	public void login(Object context) throws LoginException
	{
		securityStrategy.login(context);
	}

	/**
	 * Attempst to log off the current user.
	 * @see WaspAuthorizationStrategy#logoff(Object)
	 */
	public boolean logoff(Object context)
	{
		if (securityStrategy != null)
		{
			return securityStrategy.logoff(context);
		}
		return true;
	}

	/**
	 * Cleans up the WaspAuthorizationStrategy before killing this session. If you
	 * override this method you must call super.invalidateNow().
	 * @see wicket.protocol.http.WebSession#invalidateNow()
	 */
	public void invalidateNow()
	{
		securityStrategy.destroy();
		super.invalidateNow();
	}
}
