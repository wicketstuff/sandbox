/*
 * $Id$
 * $Revision$
 * $Date$
 *
 * ====================================================================
 * Copyright (c) 2005, Topicus B.V.
 * All rights reserved.
 */
package org.apache.wicket.security.hive.authorization;

import org.apache.wicket.security.hive.authentication.Subject;

/**
 * This interface represents the abstract notion of a principal, which can be used to
 * represent any entity, such as an individual, a corporation, or a login id.
 * @see java.security.Principal
 * @author marrink
 */
public interface Principal
{

	/**
	 * Compares this principal to the specified object. Returns true if the object passed
	 * in matches the principal represented by the implementation of this interface.
	 * @param another principal to compare with.
	 * @return true if the principal passed in is the same as that encapsulated by this
	 *         principal, and false otherwise.
	 */
	public boolean equals(Object another);

	/**
	 * Returns a string representation of this principal.
	 * @return a string representation of this principal.
	 */
	public String toString();

	/**
	 * Returns a hashcode for this principal.
	 * @return a hashcode for this principal.
	 */
	public int hashCode();

	/**
	 * Returns the name of this principal.
	 * @return the name of this principal.
	 */
	public String getName();

	/**
	 * Eventhough a subject does not explicitly hold a principal, it may still be implied
	 * by the subject. For example a read and a write principal, if the subject only holds
	 * the write principal it is only logical it also implies the read principal.
	 * @param subject
	 * @return true if the subject in any way implies this principal, false otherwise.
	 */
	public boolean implies(Subject subject);
}
