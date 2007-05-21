/*
 * $Id: ISecureComponent.java,v 1.2 2006/04/26 10:24:21 Marrink Exp $ $Revision: 1.2 $ $Date: 2006/04/26 10:24:21 $ ==================================================================== Copyright (c) 2005,
 * Topicus B.V. All rights reserved.
 */

package org.apache.wicket.security.components;


import java.io.Serializable;

import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.apache.wicket.authorization.IAuthorizationStrategy;
import org.apache.wicket.security.actions.AbstractWaspAction;
import org.apache.wicket.security.checks.ISecurityCheck;



/**
 * Some methods for secure components to easier get a hold of the securitycheck. components not implementing this
 * interface can still get or set a securitycheck on them with the {@link SecureComponentHelper}. implementations should as default use
 * these methods to wrap the calls to {@link SecureComponentHelper}, although classes like {@link IAuthorizationStrategy} should not depend on
 * this behaviour.
 * 
 * @author marrink
 */
public interface ISecureComponent extends Serializable
{
	/**
	 * Sets (or removes in the case of null) the security check on this component.
	 * @param check
	 */
	public void setSecurityCheck(ISecurityCheck check);
	/**
	 * Gets the security check attached to this component.
	 * @return the check or null if there is no check
	 */
	public ISecurityCheck getSecurityCheck();

	/**
	 * Wrapper method for the isActionAuthorized method on component.
	 * Subclasses can use the default implementation in SecureComponentHelper.
	 * @param waspAction
	 * @return true if the action is allowed, false otherwise.
	 * @see wicket.Component#isActionAuthorized(wicket.authorization.Action)
	 * @see SecureComponentHelper#isActionAuthorized(Component, String)
	 */
	public boolean isActionAuthorized(String waspAction);
	/**
	 * Wrapper method for the isActionAuthorized method on component.
	 * Subclasses can use the default implementation in SecureComponentHelper.
	 * @param action
	 * @return true if the action is allowed, false otherwise.
	 * @see wicket.Component#isActionAuthorized(wicket.authorization.Action)
	 * @see SecureComponentHelper#isActionAuthorized(Component, JaasAction)
	 */
	public boolean isActionAuthorized(AbstractWaspAction action);
	/**
	 * authenticates the user for this component.
	 * Note authentication should usually only be done by {@link Page}s.
	 * @return true if the user is authenticated, false otherwise
	 */
	public boolean isAuthenticated();
}
