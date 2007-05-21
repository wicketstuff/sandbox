/*
 * $Id: ISecurityModel.java,v 1.2 2006/03/03 10:17:54 Marrink Exp $
 * $Revision: 1.2 $
 * $Date: 2006/03/03 10:17:54 $
 *
 * ====================================================================
 * Copyright (c) 2005, Topicus B.V.
 * All rights reserved.
 */
package org.apache.wicket.security.models;

import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;
import org.apache.wicket.security.actions.AbstractWaspAction;
import org.apache.wicket.security.actions.WaspAction;
import org.apache.wicket.security.checks.ISecurityCheck;
import org.apache.wicket.security.strategies.WaspAuthorizationStrategy;


/**
 * ISecureModel is much like ISecurityCheck in that it knows how to authorize or
 * authenticate a user. Usually they just redirect the call to the
 * {@link WaspAuthorizationStrategy}, but it is not unimagenable that models are targeted at
 * specific wasp implementations and take care of there authentication or authorization themself.
 * Note authentication should rarely take place at the model level.
 * @author marrink
 * @see WaspAuthorizationStrategy
 * @see ISecurityCheck
 */
public interface ISecureModel extends IModel
{
	/**
	 * Checks if the component is authorized for this model.
	 * @param component the (owning) component of the model, some models might allow null.
	 * @param action
	 * @return true if the component is allowed to access this model, false otherwise
	 * @see WaspAuthorizationStrategy#isModelAuthorized(ISecureModel, Component, AbstractWaspAction)
	 */
	public boolean isAuthorized(Component component, WaspAction action);

	/**
	 * Checks if the user is authenticated for this model.
	 * @param component the (owning) component of the model, some models might allow null.
	 * @return true if the user is authenticated for this model, false otherwise.
	 * @see WaspAuthorizationStrategy#isModelAuthenticated(IModel, Component)
	 */
	public boolean isAuthenticated(Component component);
}
