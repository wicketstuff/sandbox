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

import wicket.Page;
import wicket.contrib.phonebook.Contact;
import wicket.markup.html.basic.Label;
import wicket.markup.html.form.Button;
import wicket.markup.html.form.Form;

/**
 * Delete the Contact.
 * @author igor
 *
 */
public class DeleteContactPage extends BasePage {
	private Page backPage;

	/**
	 * Constructor.  Display the summary (names) before asking for confirmation.
	 * Note that if you don't need the page to be bookmarkable,
	 * you can use whatever constructor you need, such as is done here.
	 *
	 * @param backPage The page that the user was on before coming here
	 * @param contactId The id of the Contact to delete.
	 */
	public DeleteContactPage(Page backPage, final long contactId) {
		this.backPage=backPage;

		Contact contact=getContactDao().load(contactId);
		
		add(new Label("name", contact.getFirstname()+" "+contact.getLastname()));

		/*
		 * Use a form to hold the buttons, but set the default form processing
		 * off as there's no point it trying to do anything, as all we're
		 * interested in are the button clicks.
		 */
	        Form form=new Form("confirmForm");
		
		form.add(new Button("confirm") {
	                /**
	                 * If clicked, delete the contact and return to the calling page.
	                 */
                        protected void onSubmit() {
				DeleteContactPage.this.getContactDao().delete(contactId);
				setResponsePage(DeleteContactPage.this.backPage);
			}
		}.setDefaultFormProcessing(false));
		
		form.add(new Button("cancel") {
			protected void onSubmit() {
				setResponsePage(DeleteContactPage.this.backPage);
			}
		}.setDefaultFormProcessing(false));
		
		add(form);
	}
}
