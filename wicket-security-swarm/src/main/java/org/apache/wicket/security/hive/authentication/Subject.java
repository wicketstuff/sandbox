package org.apache.wicket.security.hive.authentication;

import java.io.Serializable;
import java.util.Set;

import org.apache.wicket.security.hive.authorization.Principal;


/**
 * Subject represents an authenticated entity.
 * It can be decorated with certain rights ({@link Principal}s).
 * @author marrink
 *
 */
public interface Subject extends Serializable
{
	/**
	 * A readonly view of the principals.
	 * @return
	 */
	public Set getPrincipals();
	/**
	 * When set it is no longer possible to add anymore principals to this subject.
	 * @return
	 */
	public boolean isReadOnly();
	/**
	 * Mark this subject as readonly. preventing more principals to be added.
	 *
	 */
	public void setReadOnly();
	
	/**
	 * Adds a new principal to this subject.
	 * @param principal
	 * @return true if the principal was added, false if it wasn't for instance because the subject is readonly.
	 */
	public boolean addPrincipal(Principal principal);

}