/*
 * $Id: ISecurityCheck.java,v 1.2 2006/03/03 10:17:54 Marrink Exp $
 * $Revision: 1.2 $ $Date: 2006/03/03 10:17:54 $
 * 
 * ==============================================================================
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

/**
 * 
 */
package org.apache.wicket.security.checks;

import java.io.Serializable;

import org.apache.wicket.security.actions.AbstractWaspAction;
import org.apache.wicket.security.actions.WaspAction;
import org.apache.wicket.security.components.ISecureComponent;
import org.apache.wicket.security.models.ISecureModel;
import org.apache.wicket.security.strategies.WaspAuthorizationStrategy;


/**
 * A securitycheck knows how to authorize or authenticate a user. and will decide if the
 * class, component, model or any combination of those 3 or something entirely different
 * will be checked. Usually a securitycheck is triggerd by the
 * {@link WaspAuthorizationStrategy#isActionAuthorized(wicket.Component, wicket.authorization.Action)}
 * , {@link ISecureComponent#isActionAuthorized(AbstractWaspAction)} or by {@link ISecureComponent#isAuthenticated()}.
 * Usually they just redirect the call to the
 * {@link WaspAuthorizationStrategy}, but it is not unimagenable that securitychecks are targeted at
 * specific wasp implementations and take care of there authentication or authorization themself.
 * 
 * @author marrink
 */
public interface ISecurityCheck extends Serializable
{
	/**
	 * Checks if there are sufficient rights to perform the desired action(s). Note that
	 * we dont ask what needs to have these rights, the implementation will decide if it
	 * checks the class, component, model or whatever they like.
	 * @param actions the action(s) like read or read and write.
	 * @return true if there are sufficient rights, false otherwise.
	 * @see WaspAuthorizationStrategy#isComponentAuthorized(wicket.Component, AbstractWaspAction)
	 * @see WaspAuthorizationStrategy#isClassAuthorized(Class, AbstractWaspAction)
	 * @see ISecureModel#isAuthorized(wicket.Component, AbstractWaspAction)
	 */
	public boolean isActionAuthorized(WaspAction action);

	/**
	 * Checks if there is an authenticated user available. If not a page might decide to
	 * redirect to a login page instead. other components won't use this ordinarily.
	 * @return true if an authenticated user is available, false otherwise.
	 * @see WaspAuthorizationStrategy#isComponentAuthenticated(wicket.Component)
	 * @see WaspAuthorizationStrategy#isClassAuthenticated(Class)
	 * @see ISecureModel#isAuthenticated(wicket.Component)
	 */
	public boolean isAuthenticated();
}
