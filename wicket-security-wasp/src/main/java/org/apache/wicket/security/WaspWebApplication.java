/*
 * $Id: JaasWebApplication.java,v 1.7 2006/06/28 10:00:16 Marrink Exp $ $Revision: 1.7 $ $Date: 2006/06/28 10:00:16 $ ==================================================================== Copyright (c) 2005,
 * Topicus B.V. All rights reserved.
 */

package org.apache.wicket.security;

import org.apache.wicket.ISessionFactory;
import org.apache.wicket.Request;
import org.apache.wicket.Response;
import org.apache.wicket.Session;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.security.actions.ActionFactory;
import org.apache.wicket.security.strategies.StrategyFactory;

/**
 * Base class for WebAplications with a wasp security framework.
 * @author marrink
 */
public abstract class WaspWebApplication extends WebApplication implements ISessionFactory,
		WaspApplication
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * creates a new secure webapplication. sets itself up as a {@link ISessionFactory}.
	 */
	public WaspWebApplication()
	{
		super();
		setSessionFactory(this);

	}

	/**
	 * Initializes the actionfactory and the strategyfactory. If you override this method
	 * you must either call super.init() or setup the actionfactory and the
	 * strategyfactory yourself. In that order.
	 * @see wicket.protocol.http.WebApplication#init()
	 */
	protected void init()
	{
		setupActionFactory();
		setupStrategyFactory();
	}

	/**
	 * Creates a new WaspSession. If you override this method make sure you return a
	 * subclass of {@link WaspSession}.
	 * @see org.apache.wicket.protocol.http.WebApplication#newSession(org.apache.wicket.Request,
	 *      org.apache.wicket.Response)
	 */
	public Session newSession(Request request, Response response)
	{
		return new WaspSession(this, request);
	}

	/**
	 * Called by the {@link WaspWebApplication#init()}. use this to create and initialize
	 * your factory. The factory created here should be returned when calling
	 * {@link WaspApplication#getStrategyFactory()}.
	 * @see WaspApplication#getStrategyFactory()
	 */
	protected abstract void setupStrategyFactory();

	/**
	 * Called by the {@link WaspWebApplication#init()}. use this to create and initialize
	 * your factory. The factory created here should be returned when calling
	 * {@link WaspApplication#getActionFactory()}.
	 * @see WaspApplication#getActionFactory()
	 */
	protected abstract void setupActionFactory();

	/**
	 * Destroys the strategy factory and the action factory. In that order. If you
	 * override ths method you must call super.destroy().
	 * @see wicket.Application#destroy()
	 */
	protected void destroy()
	{
		StrategyFactory factory = getStrategyFactory();
		if (factory != null)
			factory.destroy();
		ActionFactory factory2 = getActionFactory();
		if (factory2 != null)
			factory2.destroy();
	}
}
