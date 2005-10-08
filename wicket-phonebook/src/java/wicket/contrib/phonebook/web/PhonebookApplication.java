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
 * 
 * @author igor
 *
 */
public class PhonebookApplication extends WebApplication {

	private ContactDao contactDao;
	
	protected void init() {
		super.init();
		
		getPages().setHomePage(ListContactsPage.class);
	}

	public ContactDao getContactDao() {
		return contactDao;
	}

	public void setContactDao(ContactDao contactDao) {
		this.contactDao = contactDao;
	}
	
	public static PhonebookApplication getInstance() {
		return ((PhonebookApplication)WebApplication.get());
	}
}
