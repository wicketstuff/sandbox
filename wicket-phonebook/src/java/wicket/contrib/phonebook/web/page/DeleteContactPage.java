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
 * 
 * @author igor
 *
 */
public class DeleteContactPage extends BasePage {
	private Page backPage;
	
	public DeleteContactPage(Page backPage, final long contactId) {
		this.backPage=backPage;

		Contact contact=getContactDao().load(contactId);
		
		add(new Label("name", contact.getFirstname()+" "+contact.getLastname()));
		
		Form form=new Form("confirmForm");
		
		form.add(new Button("confirm") {
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
