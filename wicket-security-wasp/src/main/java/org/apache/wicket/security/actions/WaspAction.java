package org.apache.wicket.security.actions;

import org.apache.wicket.authorization.Action;

/**
 * Inmutable {@link Action} with added logic for implies checks.
 * These actions are instantiated by an {@link ActionFactory}.
 * @author marrink
 * @see Action
 */
public interface WaspAction
{
	/**
	 * @return The name of this action
	 * @see Action#getName()
	 */
	public String getName();
	/**
	 * Check if the supplied action is implied  by this WaspAction.
	 * @return true if the action is implied, false otherwise.
	 */
	public boolean implies(WaspAction other);

	/**
	 * Creates a new WaspAction containing both the specified actions and the actions of this WaspAction. This method
	 * must return a new WaspAction unless this action can be returned unmodified.
	 * 
	 * @param other the actions to add
	 * @return a new WaspAction containing all the actions
	 */
	public WaspAction add(WaspAction other);

	/**
	 * Creates a new WaspAction with all the actions of this action except those specified.
	 * @param action the actions to remove
	 * @return a new WaspAction or this action if the specified actions were never part of this action.
	 */
	public WaspAction remove(WaspAction action);

}