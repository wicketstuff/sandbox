/*
 * $Id$
 * $Revision$
 * $Date$
 *
 * ====================================================================
 * Copyright (c) 2005, Topicus B.V.
 * All rights reserved.
 */
package org.apache.wicket.security.swarm.strategies;

import org.apache.wicket.security.components.ISecurePage;
import org.apache.wicket.security.hive.Hive;
import org.apache.wicket.security.hive.authorization.permissions.ComponentPermission;
import org.apache.wicket.security.strategies.StrategyFactory;
import org.apache.wicket.security.strategies.WaspAuthorizationStrategy;

/**
 * A factory to return new {@link SwarmStrategy}s.
 * @author marrink
 */
public class SwarmStrategyFactory implements StrategyFactory
{
	private final Class secureClass;

	private Object hiveQueen;

	/**
	 * Constructs a new factory. All the strategies will require {@link ISecurePage}s to
	 * have access rights.
	 * @param hiveQueen the key to get the {@link Hive}
	 */
	public SwarmStrategyFactory(Object hiveQueen)
	{
		this(ISecurePage.class, hiveQueen);
	}

	/**
	 * Constructs a new factory.
	 * @param secureClass instances of this class will be required to have access rights.
	 * @param hiveQueen hiveQueen the key to get the {@link Hive}
	 */
	public SwarmStrategyFactory(Class secureClass, Object hiveQueen)
	{
		this.secureClass = secureClass;
		this.hiveQueen = hiveQueen;

	}

	/**
	 * @see org.apache.wicket.security.strategies.StrategyFactory#destroy()
	 */
	public void destroy()
	{
		// should we clean up all sessions or is that taken care of automaticly when the
		// session is invalidated

	}

	/**
	 * @see org.apache.wicket.security.strategies.StrategyFactory#newStrategy()
	 */
	public WaspAuthorizationStrategy newStrategy()
	{
		return new SwarmStrategy(secureClass, hiveQueen);
	}

	/**
	 * The key to the hive.
	 * @return the key
	 */
	protected final Object getHiveKey()
	{
		return hiveQueen;
	}

	/**
	 * All instance of this class will be required to have {@link ComponentPermission}
	 * with atleast the access action.
	 * @return the class required to have instantiation rights
	 */
	protected final Class getSecureClass()
	{
		return secureClass;
	}

}
