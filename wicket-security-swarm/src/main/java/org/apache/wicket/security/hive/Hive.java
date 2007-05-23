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
 * applications to each have there own security policies or share one. In effect making a
 * hive a codebased policy. The {@link HiveMind} keeps a registry of hives based on keys,
 * everyone with the right key can access that hive, this allows you to choose if multiple
 * (different) webapps all share the same same hive or let each instance of an application
 * have there own hive. The key (sometimes refered to as the "queen", to stick with wasp
 * terminology ;)) can be any object. A good choice would be the application itself or if
 * it is a web application the contextname could be used.
 * @author marrink
 */
public interface Hive
{
	/**
	 * Checks if this hive contains an exact match for this principal.
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
	 * Checks if the subject has the exact permission, or if the permission is implied by
	 * any of the subjects principals.
	 * @param subject the (optional) subject
	 * @param permission the permision to check.
	 * @return true if the subject has the permission, false otherwise.
	 */
	public boolean hasPermision(Subject subject, Permission permission);
}
