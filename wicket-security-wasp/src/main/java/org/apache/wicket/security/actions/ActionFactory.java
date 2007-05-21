/*
 * $Id: ActionFactory.java,v 1.2 2006/06/04 22:57:57 Marrink Exp $ $Revision: 1.2 $ $Date: 2006/06/04 22:57:57 $
 * ==================================================================== Copyright (c) 2005, Topicus B.V. All rights
 * reserved.
 */

package org.apache.wicket.security.actions;

import org.apache.wicket.authorization.Action;

/**
 * The actionFactory translates between the wicket actions, which are string based, and
 * the waspactions, which are based on something else (depending on the implementation).
 * Swarm for instance uses bitwise or to handle implies checks.
 * @author marrink
 */
public interface ActionFactory
{

	/**
	 * Translates a wicket action to a wasp action. If the input is already a WaspAction
	 * the same object is returned.
	 * @param actions a wicket action
	 * @return a WaspAction or null if there is no mapping possible or the input is null.
	 */
	public WaspAction getAction(Action actions);

	/**
	 * Creates or reuses a WaspAction based on string values.
	 * @param actions
	 * @return a WaspAction
	 */
	public WaspAction getAction(String actions);

	/**
	 * Register a new action. By default {@link Access}, {@link Inherit}, {@link Render} and {@link Enable} are already
	 * registered
	 * @param waspActionClass the class of the action to register (must be subclass of {@link WaspAction}
	 * @param name the name to register the action with
	 * @return an instance of the registered action
	 * @throws RegistrationException if the action cannot be registered
	 */
	public WaspAction register(Class waspActionClass, String name) throws RegistrationException;

	/**
	 * Returns the registered action of this class.
	 * @param waspActionClass a subclass of {@link WaspAction}
	 * @return a new or reused instance of this class
	 * @throws IllegalArgumentException if the class is not registered.
	 */
	public WaspAction getAction(Class waspActionClass);

	/**
	 * Clean up any resources this factory holds.
	 */
	public void destroy();
}
