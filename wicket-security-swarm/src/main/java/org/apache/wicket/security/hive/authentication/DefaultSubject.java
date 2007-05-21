/*
 * $Id$
 * $Revision$
 * $Date$
 *
 * ====================================================================
 * Copyright (c) 2005, Topicus B.V.
 * All rights reserved.
 */
package org.apache.wicket.security.hive.authentication;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.apache.wicket.security.hive.authorization.Principal;


/**
 * Default implementation of a Subject.
 * 
 * @author marrink
 *
 */
public class DefaultSubject implements Subject
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private boolean readOnly;
	
	private Set principals=new HashSet(100);//guess
	private Set readOnlyPrincipals=Collections.unmodifiableSet(principals);

	/**
	 * @see org.apache.wicket.security.hive.authentication.Subject#getPrincipals()
	 */
	public Set getPrincipals()
	{
		return readOnlyPrincipals;
	}

	/**
	 * @see org.apache.wicket.security.hive.authentication.Subject#isReadOnly()
	 */
	public final boolean isReadOnly()
	{
		return readOnly;
	}

	/**
	 * @see org.apache.wicket.security.hive.authentication.Subject#setReadOnly()
	 */
	public final void setReadOnly()
	{
		this.readOnly = true;
	}
	/**
	 * 
	 * @see org.apache.wicket.security.hive.authentication.Subject#addPrincipal(org.apache.wicket.security.hive.authorization.Principal)
	 */
	public boolean addPrincipal(Principal principal)
	{
		if(readOnly)
			return false;
		if(principal==null)
			throw new IllegalArgumentException("principal can not be null.");
		return principals.add(principal);
	}
}
