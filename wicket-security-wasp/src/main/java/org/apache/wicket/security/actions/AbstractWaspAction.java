/*
 * $Id$
 * $Revision$
 * $Date$
 *
 * ====================================================================
 * Copyright (c) 2005, Topicus B.V.
 * All rights reserved.
 */
package org.apache.wicket.security.actions;

import org.apache.wicket.authorization.Action;

/**
 * Inmutable {@link Action} class, already extends Action.
 * These actions are instantiated by an ActionFactory.
 * @author marrink
 * @see Action
 */
public abstract class AbstractWaspAction extends Action implements WaspAction
{
	private static final long serialVersionUID = 1L;

	/**
	 * The default constructor for actions.
	 * @param actions
	 * @param name
	 */
	protected AbstractWaspAction(String name)
	{
		super(name);
	}
}
