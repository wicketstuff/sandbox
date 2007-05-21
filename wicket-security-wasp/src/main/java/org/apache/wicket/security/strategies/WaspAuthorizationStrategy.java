/*
 * $Id: JaasAuthorizationStrategy.java,v 1.3 2006/09/18 08:00:12 Marrink Exp $ $Revision: 1.3 $ $Date: 2006/02/23
 * 14:52:41 $ ==================================================================== Copyright (c) 2005, Topicus B.V. All
 * rights reserved.
 */

package org.apache.wicket.security.strategies;

import java.io.Serializable;

import org.apache.wicket.Application;
import org.apache.wicket.Component;
import org.apache.wicket.authorization.Action;
import org.apache.wicket.authorization.IAuthorizationStrategy;
import org.apache.wicket.model.IModel;
import org.apache.wicket.security.WaspApplication;
import org.apache.wicket.security.actions.ActionFactory;
import org.apache.wicket.security.actions.WaspAction;
import org.apache.wicket.security.checks.ISecurityCheck;
import org.apache.wicket.security.components.ISecureComponent;
import org.apache.wicket.security.components.SecureComponentHelper;
import org.apache.wicket.security.models.ISecureModel;


/**
 * Base class for every strategy.
 * Checks Authorization and authentication at the class, component and model levels.
 * 
 * @author marrink
 */
public abstract class WaspAuthorizationStrategy implements IAuthorizationStrategy, Serializable
{
	/**
	 * Performs the actual authorization check on the component.
	 * @param component
	 * @param action
	 * @return true if authorized, false otherwise
	 */
	public abstract boolean isComponentAuthorized(Component component, WaspAction action);

	/**
	 * Performs the actual authorization check on the model of the component.
	 * @param model the model
	 * @param component component 'owning' the model if available
	 * @param action the action to check
	 * @return true if authorized, false otherwise
	 */
	public abstract boolean isModelAuthorized(ISecureModel model, Component component, WaspAction action);
	
	/**
	 * Performs the actual authorization check on the component class.
	 * @param clazz typically a component
	 * @param action the action to check
	 * @return true if authorized, false otherwise
	 */
	public abstract boolean isClassAuthorized(Class clazz, WaspAction action);

	/**
	 * Performs the authentication check.
	 * @return true if the user is authenticated, false otherwise
	 */
	public abstract boolean isComponentAuthenticated(Component component);
	/**
	 * Performs the authentication check.
	 * @return true if the user is authenticated, false otherwise
	 */
	public abstract boolean isModelAuthenticated(IModel model, Component component);
	/**
	 * Performs the authentication check.
	 * @return true if the user is authenticated, false otherwise
	 */
	public abstract boolean isClassAuthenticated(Class clazz);

	/**
	 * Attemps to log the user in.
	 * @param context a not further specified object that provides all the information to
	 *            log the user on
	 * @throws LoginException if the login is unsuccesfull
	 */
	public abstract void login(Object context) throws LoginException;

	/**
	 * Log the user off. With the help of a context implementaions might facilitate multi level login / logoff.
	 * @param context a not further specified object
	 * @return true if the logoff was successfull, false otherwise
	 */
	public abstract boolean logoff(Object context);

	/**
	 * Called at the end of the sessions lifecycle to perform some clean up.
	 */
	public abstract void destroy();

	/**
	 * @see wicket.authorization.IAuthorizationStrategy#isActionAuthorized(wicket.Component,
	 *      wicket.authorization.Action)
	 */
	public boolean isActionAuthorized(Component component, Action action)
	{
		if (component == null)
			return true;
		ISecurityCheck check = getSecurityCheck(component);
		if (check != null)
			return check.isActionAuthorized(getActionFactory().getAction(action));
		IModel model = component.getModel();
		if (model instanceof ISecureModel)
			return isModelAuthorized(null, component, getActionFactory().getAction(action));
		return true;
	}

	/**
	 * We cannot assume everybody uses the here specified public methods to store the
	 * securitycheck, so we check if the component is a ISecureComponent and ifso use the
	 * getSecurityCheck on the secure component else we fall back to the
	 * SecureComponentHelper.
	 * @param component
	 * @return
	 * @see SecureComponentHelper#getSecurityCheck(Component)
	 */
	protected final ISecurityCheck getSecurityCheck(Component component)
	{
		if (component instanceof ISecureComponent)
			return ((ISecureComponent) component).getSecurityCheck();
		return SecureComponentHelper.getSecurityCheck(component);
	}
	/**
	 * Shortcut to the actionfactory.
	 * @return the actionfactory from the application
	 */
	protected final ActionFactory getActionFactory()
	{
		return ((WaspApplication) Application.get()).getActionFactory();
	}
}
