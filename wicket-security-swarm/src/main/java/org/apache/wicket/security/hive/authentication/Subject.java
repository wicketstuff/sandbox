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

import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;
import org.apache.wicket.security.hive.authorization.Principal;
import org.apache.wicket.security.strategies.WaspAuthorizationStrategy;

/**
 * Subject represents (part of) an authenticated entity, such as an individual,
 * a corporation, or a login id. It can be decorated with certain rights ({@link Principal}s).
 * Most implementations will provide some means to add (and sometimes remove)
 * principals, however all must honor the readonly flag. {@link #setReadOnly()}
 * is automatically triggerd after a login. Subjects are created by
 * {@link LoginContext}s as placeholders for the rights of a user for the
 * duration of the session. In addition Subjects play an importeand part in
 * multi-login scenario's as they define what they authenticate. For example one
 * subject might authenticate all subclassses of BasicSecurePage where another
 * might authenicate all subclasses of AdvancedSecurePage. Effectivly requiring
 * a user to login twice if both type of pages are to be visited.
 * 
 * @author marrink
 */
public interface Subject extends Serializable
{
	/**
	 * A readonly view of the principals.
	 * 
	 * @return the principals
	 */
	public Set getPrincipals();

	/**
	 * When set it is no longer possible to change the set of principals of this
	 * subject.
	 * 
	 * @return true if this Subject is readonly, false otherwise
	 */
	public boolean isReadOnly();

	/**
	 * Mark this subject as readonly. preventing principals to be added or
	 * removed. Note this method is always called on a subject after it has been
	 * handed over to the security layer.
	 */
	public void setReadOnly();

	/**
	 * Performs the authentication check on a class.
	 * 
	 * @param class1
	 * @return true if the class is authenticated, false otherwise.
	 * @see WaspAuthorizationStrategy#isClassAuthenticated(Class)
	 */
	public abstract boolean isClassAuthenticated(Class class1);

	/**
	 * Performs the authentication check on a component.
	 * 
	 * @param component
	 * @return true if the component is authenticated, false otherwise
	 * @see WaspAuthorizationStrategy#isComponentAuthenticated(Component)
	 */
	public abstract boolean isComponentAuthenticated(Component component);

	/**
	 * Performs the authentication check on a model.
	 * 
	 * @param model
	 * @param component
	 * @return true if the model is authenticated, false otherwise
	 * @see WaspAuthorizationStrategy#isModelAuthenticated(IModel, Component)
	 */
	public abstract boolean isModelAuthenticated(IModel model, Component component);

}