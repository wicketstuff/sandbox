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
import org.apache.wicket.security.strategies.StrategyFactory;
import org.apache.wicket.security.strategies.WaspAuthorizationStrategy;

/**
 * @author marrink
 *
 */
public class SwarmStrategyFactory implements StrategyFactory
{
	private final Class secureClass;
	private Object hiveQueen;
	/**
	 * @param hiveQueen the key to get the {@link Hive}
	 * 
	 */
	public SwarmStrategyFactory(Object hiveQueen)
	{
		this(ISecurePage.class, hiveQueen);
	}
	public SwarmStrategyFactory(Class secureClass, Object hiveQueen)
	{
		this.secureClass = secureClass;
		this.hiveQueen=hiveQueen;
		
	}
	
	/**
	 * @see org.apache.wicket.security.strategies.StrategyFactory#destroy()
	 */
	public void destroy()
	{
		//should we clean up all sessions or is that taken care of automaticly when the session is invalidated

	}

	/**
	 * @see org.apache.wicket.security.strategies.StrategyFactory#newStrategy()
	 */
	public WaspAuthorizationStrategy newStrategy()
	{
		return new SwarmStrategy(secureClass, hiveQueen);
	}
	protected final Object getHiveKey()
	{
		return hiveQueen;
	}
	
	protected final Class getSecureClass()
	{
		return secureClass;
	}

}
