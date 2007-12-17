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
package org.apache.wicket.seam.example;

import org.apache.wicket.Request;
import org.apache.wicket.Response;
import org.apache.wicket.Session;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.seam.SeamSupport;

/**
 * Test application for Wicket/ Seam/ Jetty.
 */
public class SeamExampleApplication extends WebApplication {

	/**
	 * Constructor.
	 */
	public SeamExampleApplication() {
	}

	/**
	 * @see wicket.Application#getHomePage()
	 */
	@SuppressWarnings("unchecked")
	public Class getHomePage() {
		return Home.class;
	}

	/**
	 * Custom session with invalidation override. We can't just let Wicket
	 * invalidate the session as Seam might have to do some cleaning up to do.
	 */
	@Override
	public Session newSession(Request request, Response response) {
		return new WebSession(SeamExampleApplication.this, request) {

			@Override
			public void invalidate() {
				org.jboss.seam.web.Session.getInstance().invalidate();
			}

			@Override
			public void invalidateNow() {
				// sorry, can't support this with Seam
				org.jboss.seam.web.Session.getInstance().invalidate();
			}
		};
	}

	@Override
	protected void init() {
		super.init();
		SeamSupport.activate(this);
	}
}