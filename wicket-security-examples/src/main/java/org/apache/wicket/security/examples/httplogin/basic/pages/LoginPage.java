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
package org.apache.wicket.security.examples.httplogin.basic.pages;

import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.security.examples.httplogin.basic.authentication.MyLoginContext;
import org.apache.wicket.security.login.http.HttpAuthenticationLoginPage;

/**
 * @author marrink
 */
public class LoginPage extends HttpAuthenticationLoginPage
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Construct.
	 */
	public LoginPage()
	{
		// stateless so the login page will not throw a timeout exception
		setStatelessHint(true);
		add(new FeedbackPanel("feedback")
		{
			private static final long serialVersionUID = 1L;

			/**
			 * @see org.apache.wicket.Component#isVisible()
			 */
			public boolean isVisible()
			{
				return anyMessage();
			}
		});
		add(new Link("link")
		{

			private static final long serialVersionUID = 1L;

			public void onClick()
			{
				doAuthentication();
			}
		});
	}

	/**
	 * @see org.apache.wicket.security.login.http.HttpAuthenticationLoginPage#getBasicLoginContext(java.lang.String,
	 *      java.lang.String)
	 */
	protected Object getBasicLoginContext(String username, String password)
	{
		return new MyLoginContext(username, password);
	}

	/**
	 * @see org.apache.wicket.security.login.http.HttpAuthenticationLoginPage#getRealm()
	 */
	public String getRealm()
	{
		return "examples"; // could be anything according to the http spec
	}

}
