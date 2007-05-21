/*
 * $Id: JaasApplication.java,v 1.3 2006/06/28 10:00:16 Marrink Exp $ $Revision: 1.3 $ $Date: 2006/06/28 10:00:16 $ ==================================================================== Copyright (c) 2005,
 * Topicus B.V. All rights reserved.
 */

package org.apache.wicket.security;

import org.apache.wicket.Application;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.security.actions.ActionFactory;
import org.apache.wicket.security.strategies.StrategyFactory;


/**
 * Interface over Application to get the factories. All implementations should extend the {@link Application} class or a subclass
 * thereof. Implementations of {@link WebApplication} should also cleanup the factories with a call to destroy when the time has come.
 * 
 * @see wicket.Application
 * @see ActionFactory
 * @see StrategyFactory
 * @author marrink
 */
public interface WaspApplication
{

	/**
	 * Returns the factory that will be used to create strategys for each session. There is only one factory for each
	 * application. Can not be null.
	 * 
	 * @return a factory
	 */
	public StrategyFactory getStrategyFactory();
	/**
	 * Returns factory for action creation / tanslation. There is only one factory for each
	 * application. Can not be null.
	 * @return a factory.
	 */
	public ActionFactory getActionFactory();
	/**
	 * The Page to redirect to when the user is not authenticated.
	 * @return a page.
	 */
	public Class getLoginPage();

}