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
package org.apache.wicket.security.examples.customactions.pages;

import org.apache.wicket.security.WaspSession;
import org.apache.wicket.security.examples.authorization.MyPrincipal;
import org.apache.wicket.security.examples.pages.login.UsernamePasswordSignInPanel;
import org.apache.wicket.security.hive.authentication.DefaultSubject;
import org.apache.wicket.security.hive.authentication.LoginContext;
import org.apache.wicket.security.hive.authentication.Subject;
import org.apache.wicket.security.strategies.LoginException;
import org.apache.wicket.util.lang.Objects;

/**
 * @author marrink
 */
public class LoginPage extends org.apache.wicket.security.examples.pages.login.LoginPage
{
	private static final long serialVersionUID = 1L;

	/**
	 * @see org.apache.wicket.security.examples.pages.login.LoginPage#newUserPasswordSignInPanel(java.lang.String)
	 */
	protected void newUserPasswordSignInPanel(String panelId)
	{
		add(new UsernamePasswordSignInPanel(panelId)
		{

			private static final long serialVersionUID = 1L;

			public boolean signIn(final String username, final String password)
			{
				LoginContext context = new LoginContext()
				{
					/**
					 * @see org.apache.wicket.security.hive.authentication.LoginContext#login()
					 */
					public Subject login() throws LoginException
					{
						// irrelevant check
						if (username != null && Objects.equal(username, password))
						{
							DefaultSubject subject = new DefaultSubject();
							if ("ceo".equals(username))
							{
								subject.addPrincipal(new MyPrincipal("organisation.rights"));
							}
							else
								subject.addPrincipal(new MyPrincipal("department.rights"));
							return subject;
						}
						throw new LoginException(
								"Username and password do not match any known user.");
					}
				};
				try
				{
					((WaspSession)getSession()).login(context);
				}
				catch (LoginException e)
				{
					return false;
				}
				return true;
			}
		});
	}

}
