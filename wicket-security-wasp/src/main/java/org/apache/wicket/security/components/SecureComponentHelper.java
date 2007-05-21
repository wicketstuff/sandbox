/*
 * $Id: SecureComponentHelper.java,v 1.4 2006/06/28 10:00:16 Marrink Exp $ $Revision: 1.4 $ $Date: 2006/06/28 10:00:16 $
 * ==================================================================== Copyright (c) 2005, Topicus B.V. All rights
 * reserved.
 */

package org.apache.wicket.security.components;

import org.apache.wicket.Application;
import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.security.WaspApplication;
import org.apache.wicket.security.actions.AbstractWaspAction;
import org.apache.wicket.security.actions.ActionFactory;
import org.apache.wicket.security.checks.ISecurityCheck;
import org.apache.wicket.security.checks.WaspKey;
import org.apache.wicket.security.models.ISecureModel;
import org.apache.wicket.security.strategies.SecurityException;
import org.apache.wicket.security.strategies.WaspAuthorizationStrategy;


/**
 * Utility class for secure components.
 * @author marrink
 * @see ISecurityCheck
 * @see ISecureComponent
 * @see ISecureModel
 */
public final class SecureComponentHelper
{

	/**
	 * The security check placed on the component or null. This uses the metadata of a
	 * component to store or retrieve the {@link ISecurityCheck}.
	 * @param component if null, null will be returned
	 * @return the security check or null if none was placed on the component
	 */
	public static ISecurityCheck getSecurityCheck(Component component)
	{
		if (component != null)
			return (ISecurityCheck) component.getMetaData(new WaspKey());
		return null;
	}

	/**
	 * Places a security check on a component. This uses the metadata of a component to
	 * store or retrieve the {@link ISecurityCheck}.
	 * @param component
	 * @param securityCheck
	 * @return the component or null if the component was null to begin with.
	 */
	public static Component setSecurityCheck(Component component, ISecurityCheck securityCheck)
	{
		if (component == null)
			return null;
		component.setMetaData(new WaspKey(), securityCheck);
		return component;
	}

	/**
	 * We cannot assume everybody uses the here specified public methods to store the
	 * {@link ISecurityCheck}, so we check if the component is a {@link ISecureComponent}
	 * and ifso use the {@link ISecureComponent#getSecurityCheck()} on the secure
	 * component else we fall back to the metadata.
	 * @param component
	 * @return the security check or null if the component does not have one.
	 */
	private static ISecurityCheck saveGetSecurityCheck(Component component)
	{
		if (component instanceof ISecureComponent)
			return ((ISecureComponent) component).getSecurityCheck();
		return getSecurityCheck(component);
	}

	/**
	 * Checks if the component has a {@link ISecureModel}.
	 * @param component
	 * @return true if the component has a securemodel, else false.
	 */
	public static boolean hasSecureModel(Component component)
	{
		return component != null && component.getModel() instanceof ISecureModel;
	}

	/**
	 * Gets the {@link ActionFactory}.
	 * @return the cationfactory
	 * @throws WicketRuntimeException if the ActionFactory is not found.
	 */
	private static ActionFactory getActionFactory()
	{
		Application application = Application.get();
		if (application instanceof WaspApplication)
		{
			WaspApplication app = (WaspApplication) application;
			return app.getActionFactory();
		}
		throw new WicketRuntimeException(application + " is not a WaspApplication");
	}

	/**
	 * Default implementation for {@link ISecureComponent#isActionAuthorized(String)} and
	 * {@link WaspAuthorizationStrategy#isActionAuthorized(Component, wicket.authorization.Action)}.
	 * First tries to use the {@link ISecurityCheck} from the component if that is not
	 * available it tries the {@link ISecureModel} if neither is present the action is
	 * authorized on the component.
	 * @return true, if the component is authorized, false otherwise.
	 * @see ISecureComponent#isActionAuthorized(String)
	 * @see ISecurityCheck
	 * @see ISecureModel
	 */
	public static boolean isActionAuthorized(Component component, String action)
	{
		if (action == null)
			return true;
		ISecurityCheck check = saveGetSecurityCheck(component);
		if (check != null)
			return check.isActionAuthorized(getActionFactory().getAction(action));
		if (hasSecureModel(component))
			return ((ISecureModel) component.getModel()).isAuthorized(component, getActionFactory()
					.getAction(action));
		return true;
	}

	/**
	 * Default implementation for
	 * {@link ISecureComponent#isActionAuthorized(AbstractWaspAction)} and
	 * {@link WaspAuthorizationStrategy#isActionAuthorized(Component, wicket.authorization.Action)}.
	 * First tries to use the {@link ISecurityCheck} from the component if that is not
	 * available it tries the {@link ISecureModel} if neither is present the action is
	 * authorized on the component.
	 * @return true, if the component is authorized, false otherwise.
	 * @see ISecureComponent#isActionAuthorized(AbstractWaspAction)
	 * @see ISecurityCheck
	 * @see ISecureModel
	 */
	public static boolean isActionAuthorized(Component component, AbstractWaspAction action)
	{
		if (action == null)
			return true;
		ISecurityCheck check = saveGetSecurityCheck(component);
		if (check != null)
			return check.isActionAuthorized(getActionFactory().getAction(action));
		if (hasSecureModel(component))
			return ((ISecureModel) component.getModel()).isAuthorized(component, getActionFactory()
					.getAction(action));
		return true;
	}

	/**
	 * Default implementation for {@link ISecureComponent#isAuthenticated()}. First tries
	 * to use the {@link ISecurityCheck} from the component if that is not available it
	 * tries the {@link ISecureModel} if neither is present the user is authenticated.
	 * @return true, if the user is authenticated, false otherwise.
	 * @see ISecureComponent#isAuthenticated()
	 * @see ISecurityCheck
	 * @see ISecureModel
	 */
	public static boolean isAuthenticated(Component component)
	{
		ISecurityCheck check = saveGetSecurityCheck(component);
		if (check != null)
			return check.isAuthenticated();
		if (hasSecureModel(component))
			return ((ISecureModel) component.getModel()).isAuthenticated(component);
		return true;
	}

	/**
	 * Builds a 'unique' name for the component. The name is based on the page class alias
	 * and the relativepath to the page (if not a page itself). Note that although it is
	 * unlikely, it is not impossible for two components to have the same alias.
	 * @param component
	 * @return a name.
	 * @throws SecurityException if the component is null, or if the page of the component is not available.
	 */
	public static String alias(Component component)
	{
		// might be usefull in wicket core itself
		if (component == null)
			throw new SecurityException("Specified component is null");
		Page page = null;
		try
		{
			page=component.getPage();
		}
		catch(IllegalStateException e)
		{
			throw new SecurityException("Unable to create alias for component: "+component,e);
		}
		String alias = alias(page.getClass());
		String relative = component.getPageRelativePath();
		if (relative == null || "".equals(relative))
			return alias;
		return alias + ":" + relative;
	}

	/**
	 * Builds an alias for a class.
	 * @param class1
	 * @return
	 */
	public static String alias(Class class1)
	{
		if (class1 == null)
			throw new SecurityException("Specified class is null");
		return class1.getName();
	}
}
