/*
 * $Id$
 * $Revision$
 * $Date$
 *
 * ====================================================================
 * Copyright (c) 2005, Topicus B.V.
 * All rights reserved.
 */
package org.apache.wicket.security.swarm.actions;

import java.io.IOException;
import java.io.ObjectInputStream;

import org.apache.wicket.Application;
import org.apache.wicket.authorization.Action;
import org.apache.wicket.security.WaspWebApplication;
import org.apache.wicket.security.actions.AbstractWaspAction;
import org.apache.wicket.security.actions.Access;
import org.apache.wicket.security.actions.ActionFactory;
import org.apache.wicket.security.actions.Enable;
import org.apache.wicket.security.actions.Render;
import org.apache.wicket.security.actions.WaspAction;


/**
 * Inmutable {@link Action} class based on int values to speed up inheritance checking. Each
 * action is assigned a power of 2 int value. Bitwise or checks provide the implie logic.
 * These actions are instantiated by an {@link ActionFactory} which provides both the name and the
 * int value for the constructor.
 * @author marrink
 * @see Action
 */
public class SwarmAction extends AbstractWaspAction
{
	private static final long serialVersionUID = 1L;

	private final int actions;

	/**
	 * The default constructor for actions. Use it if your action does not inherit any
	 * other actions (other then {@link Access}), like {@link Render}.
	 * @param action a power of 2 which provides the base value for this action
	 * @param name the name of the action
	 */
	protected SwarmAction(int action, String name)
	{
		super(name);
		if (action < 0)
			throw new IllegalArgumentException(action + " must be >= 0");
		this.actions = action;
	}

	/**
	 * Alternate constructor for actions that inherit from another action. For example the
	 * constructor for a {@link Enable} action which implies the {@link Render} action might look like this.
	 * <code>super(actions | ((SwarmAction)factory.getAction(Render.class)).actions(), name);</code>
	 * @param action a power of 2 which provides the base value for this action
	 * @param name the name of the action
	 * @param factory the factory for lookup of implied actions
	 */
	protected SwarmAction(int action, String name, ActionFactory factory)
	{
		this(action, name);
	}

	/**
	 * Check if the supplied actions are implied (bitwise or) by this SwarmAction.
	 * @return true if the actions are implied, false otherwise.
	 */
	public final boolean implies(int actions)
	{
		return ((this.actions & actions) == actions);
	}
	/**
	 * Returns the internal representation of this action.
	 * @return
	 */
	public final int actions()
	{
		return actions;
	}

	/**
	 * Any SwarmAction is equal to another if there 'actions' value is the same. In other
	 * words the name of an action is not important.
	 * @see Object#equals(java.lang.Object)
	 * @see #actions()
	 */
	public final boolean equals(Object obj)
	{
		if (obj instanceof SwarmAction)
		{
			SwarmAction other = (SwarmAction) obj;
			return other.actions() == actions;
		}
		return false;
	}

	/**
	 * @see Object#hashCode()
	 */
	public final int hashCode()
	{
		int result = 4679;
		result = 37 * result + SwarmAction.class.hashCode();
		result = 37 * result + actions;
		return result;
	}

	/**
	 * Check if the supplied action is implied (bitwise or) by this SwarmAction.
	 * @return true if the action is implied, false otherwise.
	 */
	public final boolean implies(WaspAction other)
	{
		return other instanceof SwarmAction && implies(((SwarmAction)other).actions());
	}
	/**
	 * Creates a new {@link WaspAction} containing both the specified actions and the actions of this {@link WaspAction}. This method
	 * always returns a new SwarmAction.
	 * 
	 * @param actions the actions to add
	 * @return a new WaspAction containing all the actions
	 */
	public final WaspAction add(int actions)
	{
		return newInstance(this.actions | actions);
	}
	/**
	 * Creates a new {@link WaspAction} containing both the specified actions and the actions of this {@link WaspAction}. This method
	 * always returns a new SwarmAction.
	 * 
	 * @param action
	 * @return a new WaspAction containing all the actions
	 */
	public final WaspAction add(WaspAction other)
	{
		if(other instanceof SwarmAction)
			return newInstance(actions | ((SwarmAction)other).actions());
		throw new IllegalArgumentException("other must be a SwarmAction");
	}
	/**
	 * Creates a new {@link WaspAction} with all the actions of this action except those specified.
	 * @param actions the actions to remove
	 * @return a new WaspAction or this action if the specified actions were never part of this action.
	 */
	public final SwarmAction remove(int actions)
	{
		if(implies(actions))
			return newInstance(this.actions-actions);
		return this;
	}
	/**
	 * Creates a new WaspAction with all the actions of this action except those specified.
	 * @param action the actions to remove
	 * @return a new WaspAction or this action if the specified actions were never part of this action.
	 */
	public final WaspAction remove(WaspAction action)
	{
		if(action instanceof SwarmAction)
			return remove(((SwarmAction)action).actions);
		throw new IllegalArgumentException("action must be a SwarmAction");
	}
	/**
	 * shortcut to the actionfactory. This might actually return the same object depending on the implementation if the
	 * new SwarmAction does not have more actions then the current.
	 * 
	 * @param myActions
	 * @return
	 */
	private SwarmAction newInstance(int myActions)
	{
		return ((SwarmActionFactory)((WaspWebApplication)Application.get()).getActionFactory()).getAction(myActions);
	}
	/**
	 * De-Serialization implementation.
	 * @param stream
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private final void readObject(ObjectInputStream stream) throws IOException,
			ClassNotFoundException
	{
		stream.defaultReadObject();
	}
}
