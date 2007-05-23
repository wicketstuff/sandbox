/*
 * $Id$
 * $Revision$
 * $Date$
 *
 * ====================================================================
 * Copyright (c) 2005, Topicus B.V.
 * All rights reserved.
 */
package org.apache.wicket.security.swarm;

import org.apache.wicket.security.WaspWebApplication;
import org.apache.wicket.security.actions.ActionFactory;
import org.apache.wicket.security.strategies.StrategyFactory;
import org.apache.wicket.security.swarm.actions.SwarmActionFactory;
import org.apache.wicket.security.swarm.strategies.SwarmStrategyFactory;

/**
 * A default webapp. It sets up the strategy and action factories and triggers the hive
 * setup. but you must remember to call super in the init or do your own factory setups.
 * @author marrink
 */
public abstract class SwarmWebApplication extends WaspWebApplication
{
	private ActionFactory actionFactory;

	private StrategyFactory strategyFactory;

	/**
	 * @see org.apache.wicket.security.WaspWebApplication#setupActionFactory()
	 */
	protected void setupActionFactory()
	{
		if (actionFactory == null)
			actionFactory = new SwarmActionFactory();
		else
			throw new IllegalStateException("Can not initialize ActionFactory more then once");

	}

	/**
	 * @see org.apache.wicket.security.WaspWebApplication#setupStrategyFactory()
	 */
	protected void setupStrategyFactory()
	{
		if (strategyFactory == null)
			strategyFactory = new SwarmStrategyFactory(getHiveKey());
		else
			throw new IllegalStateException("Can not initialize StrategyFactory more then once");
	}

	/**
	 * triggers the setup of the factories and the hive. Please remember to call
	 * super.init when you override this method.
	 * @see org.apache.wicket.security.WaspWebApplication#init()
	 */
	protected void init()
	{
		setupActionFactory();
		setUpHive();
		setupStrategyFactory();
	}

	/**
	 * Set up a Hive for this Application. For Example<br>
	 * <code>
	 * PolicyFileHiveFactory factory = new PolicyFileHiveFactory();
	 * factory.addPolicyFile("/policy.hive");
	 * HiveMind.registerHive(getHiveKey(), factory);
	 * </code>
	 * Note that you must setup the actionfactory before you can setup the hive. Note that
	 * the hive is not automatically unregistered since there is a chance you want to
	 * share it with another webapp. If you want to unregister the hive please do so in
	 * the {@link #destroy()}
	 */
	protected abstract void setUpHive();

	/**
	 * @see org.apache.wicket.security.WaspApplication#getActionFactory()
	 */
	public ActionFactory getActionFactory()
	{
		return actionFactory;
	}

	/**
	 * @see org.apache.wicket.security.WaspApplication#getStrategyFactory()
	 */
	public StrategyFactory getStrategyFactory()
	{
		return strategyFactory;
	}

	/**
	 * Returns the key to specify the hive.
	 * @return the key
	 */
	protected abstract Object getHiveKey();

}
