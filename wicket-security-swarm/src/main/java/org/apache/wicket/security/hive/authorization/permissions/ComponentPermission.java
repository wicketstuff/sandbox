/*
 * $Id: ComponentPermission.java,v 1.3 2006/06/19 14:03:43 Marrink Exp $ $Revision: 1.3 $ $Date: 2006/06/19 14:03:43 $
 * ==================================================================== Copyright (c) 2005, Topicus B.V. All rights
 * reserved.
 */

package org.apache.wicket.security.hive.authorization.permissions;


import org.apache.wicket.Component;
import org.apache.wicket.security.actions.Access;
import org.apache.wicket.security.components.SecureComponentHelper;
import org.apache.wicket.security.hive.authorization.Permission;
import org.apache.wicket.security.swarm.actions.SwarmAction;


/**
 * Permission for certain components. Can have actions like access, read or write.
 * 
 * @author marrink
 */
public class ComponentPermission extends ActionPermission
{

	private static final long serialVersionUID = 8950870313751454034L;

	/**
	 * Creates a new ComponentPermission with the default access action.
	 * 
	 * @param component
	 */
	public ComponentPermission(Component component)
	{
		super(SecureComponentHelper.alias(component), getAction(Access.class));
	}
	public ComponentPermission(Component component, int actions)
	{
		super(SecureComponentHelper.alias(component), getAction(actions));
	}
	/**
	 * Creates a new ComponentPermission with the specified actions.
	 * 
	 * @param component
	 * @param action
	 */
	public ComponentPermission(Component component, SwarmAction action)
	{
		super(SecureComponentHelper.alias(component), action);
	}

	/**
	 * Creates a new ComponentPermission with the specified actions.
	 * 
	 * @param componentPath
	 * @param actions
	 */
	public ComponentPermission(String componentPath, String actions)
	{
		super(componentPath, getAction(actions));
	}
	/**
	 * @param securityString
	 * @param actions
	 */
	public ComponentPermission(String securityString, SwarmAction actions)
	{
		super(securityString, actions);
	}

	/**
	 * @see Permission#implies(Permission)
	 */
	public boolean implies(Permission permission)
	{
		return (permission instanceof ComponentPermission) && super.implies(permission);
	}
}
