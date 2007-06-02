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

import java.io.Serializable;
import java.util.Set;

import org.apache.wicket.security.hive.authorization.Principal;

/**
 * Subject represents an authenticated entity. It can be decorated with certain rights ({@link Principal}s).
 * A hive subject is very similar to a jaas subject.
 * @author marrink
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
	 */
	public void setReadOnly();

	/**
	 * Adds a new principal to this subject.
	 * @param principal
	 * @return true if the principal was added, false if it wasn't for instance because
	 *         the subject is readonly.
	 */
	public boolean addPrincipal(Principal principal);

}