/*
 * $Id$
 * $Revision$
 * $Date$
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
package wicket.contrib.phonebook.web;

import wicket.Page;
import wicket.contrib.phonebook.web.page.ListContactsPage;
import wicket.protocol.http.WebApplication;
import wicket.spring.injection.SpringComponentInjector;
import wicket.util.time.Duration;

public class PhonebookApplication extends WebApplication {

	public Class<? extends Page> getHomePage() {
		return ListContactsPage.class;
	}

	/**
	 * Custom initialisation. This method is called right after this application
	 * class is constructed, and the wicket servlet is set.
	 */
	protected void init() {
		super.init();

		addComponentInstantiationListener(new SpringComponentInjector(this));

		getResourceSettings().addResourceFolder("src/java");
		getResourceSettings().setResourcePollFrequency(Duration.seconds(10));

	}
}
