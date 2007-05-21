/*
 * $Id$
 * $Revision$
 * $Date$
 *
 * ====================================================================
 * Copyright (c) 2005, Topicus B.V.
 * All rights reserved.
 */
package org.apache.wicket.security.hive;

import java.security.Policy;

import org.apache.wicket.security.hive.authentication.Subject;
import org.apache.wicket.security.hive.authorization.Permission;
import org.apache.wicket.security.hive.authorization.Principal;


/**
 * A Hive contains the security policy for the system, much like the {@link Policy} in
 * JAAS does. However where the Policy only has a single instance for each virtual
 * machine, multiple Hives are allowed per vm. Allowing for instance multiple web
 * applications to each have there own security policies or share one.
 * 
 * @author marrink
 */
public interface Hive
{
	/**
	 * Checks if this hive contains a principal.
	 * @param principal
	 * @return true if the hive contains the principal, false otherwise.
	 */
	public boolean containsPrincipal(Principal principal);
	
	/**
	 * Checks if this hive contains exactly the permision.
	 * @param permission
	 * @return true if the hive contains the permission.
	 */
	public boolean containsPermission(Permission permission);
	/**
	 * Checks if the subject has the permission.
	 * @param subject
	 * @param permission
	 * @return true if the subject has the permission, false otherwise.
	 */
	public boolean hasPermision(Subject subject,Permission permission);
}
