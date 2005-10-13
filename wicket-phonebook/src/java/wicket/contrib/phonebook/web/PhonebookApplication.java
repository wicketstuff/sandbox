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

import wicket.contrib.phonebook.ContactDao;
import wicket.contrib.phonebook.web.page.ListContactsPage;
import wicket.protocol.http.WebApplication;

/**
 * Extends Wicket's WebApplication to hold the DAO instance for the app.
 * @author igor
 *
 */
public class PhonebookApplication extends WebApplication {

	private ContactDao contactDao;

	/**
	 * Custom initialisation. This method is called right after this
	 * application class is constructed, and the wicket servlet is set.
	 */
	protected void init() {
		super.init();
		
		getPages().setHomePage(ListContactsPage.class);
	}

	/**
	 * Get the Contact DAO instance
	 * @return The Contact DAO instance
	 */
	public ContactDao getContactDao() {
		return contactDao;
	}

	/**
	 * Sets the Contact DAO for this app.  Called by Spring.
	 * @param contactDao
	 */
	public void setContactDao(ContactDao contactDao) {
		this.contactDao = contactDao;
	}

	/**
	 * Get the PhonebookApplication instance for the current session.
	 * Called by the DetachableContactModel's {@link DetachableContactModel#onAttach onAttach} method. 
	 * @return The phonebookApplication instance for the current session.
	 */
	public static PhonebookApplication getInstance() {
		return ((PhonebookApplication)WebApplication.get());
	}
}
