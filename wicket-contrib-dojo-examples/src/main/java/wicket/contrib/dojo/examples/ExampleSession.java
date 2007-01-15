/*
 * $Id$
 * $Revision$ $Date$
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
package wicket.contrib.dojo.examples;

import wicket.Request;
import wicket.protocol.http.WebApplication;
import wicket.protocol.http.WebSession;

/**
 * Subclass of WebSession for ExampleApplication to allow easy and typesafe
 * access to session properties.
 */
public final class ExampleSession extends WebSession {

	/**
	 * Constructor
	 * 
	 * @param application
	 *            The application
	 * @param request
	 *            The current request
	 */
	protected ExampleSession(final WebApplication application, Request request) {
		super(application, request);
	}
}
