/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
 * @see Subject
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
