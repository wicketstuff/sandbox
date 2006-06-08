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
package wicket.contrib.phonebook.web.page;

import wicket.contrib.phonebook.ContactDao;
import wicket.contrib.phonebook.web.PhonebookApplication;
import wicket.markup.html.WebPage;
import wicket.markup.html.panel.FeedbackPanel;

/**
 * Base page class used for phonebook web pages.
 * <p>
 * This page holds a reference to contact dao since all phonebook pages need
 * access to it.
 * <p>
 * The markup of this files provides some common html as well as includes a
 * reference to the css file that all other pages inherit through wicket's
 * markup inheritance feature.
 * <p>
 * The base page also extends the SpringWebPage class so that all its subclasses
 * have their dependencies automatically injected.
 * 
 * @author igor
 */
public class BasePage extends WebPage {

	public BasePage() {
		add(new FeedbackPanel("status"));
	}
	
	protected ContactDao getDao() {
		return ((PhonebookApplication) getApplication()).getContactDao();
	}
}
