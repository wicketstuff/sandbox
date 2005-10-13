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
import wicket.markup.html.form.Button;
import wicket.markup.html.form.Form;
import wicket.markup.html.form.RequiredTextField;
import wicket.markup.html.form.TextField;
import wicket.markup.html.form.validation.EmailAddressPatternValidator;
import wicket.markup.html.form.validation.LengthValidator;
import wicket.markup.html.panel.FeedbackPanel;
import wicket.model.CompoundPropertyModel;

/**
 * Edit the Contact.  Display details if an existing contact, then persist
 * them if saved.
 * @author igor
 *
 */
public class EditContactPage extends BasePage {
	private Page backPage;
	
	/**
	 * Constructor.  Create or edit the contact.
	 * Note that if you don't need the page to be bookmarkable,
	 * you can use whatever constructor you need, such as is done here.
	 *
	 * @param backPage The page that the user was on before coming here
	 * @param contactId The id of the Contact to edit, or 0 for a new contact.
	 */
	public EditContactPage(Page backPage, final long contactId) {
		this.backPage=backPage;
		Contact contact=
			(contactId==0)?null:getContactDao().load(contactId);
		
		add(new FeedbackPanel("feedback"));
		
		add(new ContactForm("contactForm", contact) {

			protected void onSave() {
				Contact contact=
					(contactId==0)?new Contact():getContactDao().load(contactId);
				applyFormChanges(contact);
				getContactDao().save(contact);
				setResponsePage(EditContactPage.this.backPage);
			}

			protected void onCancel() {
				setResponsePage(EditContactPage.this.backPage);
			}
			
		});
	}
	
	/**
	 * Form to allow Contact detail editing.
	 * Note the use of an internal Contact instance, rather than using the one
	 * passed in the constructor, which ensures that any partial updates as a
	 * result of validation failure do not alter the caller's instance.
	 *
	 * @author igor
	 */
	private abstract class ContactForm extends Form {
		private final Contact bean=new Contact();
		
		public ContactForm(String id, Contact contact) {
			super(id);
			if (contact!=null) {
				beanFromTo(contact, bean);
			}
			
			setModel(new CompoundPropertyModel(bean));
			
			add(new RequiredTextField("firstname")
					.add(LengthValidator.max(32)));
			
			add(new RequiredTextField("lastname")
					.add(LengthValidator.max(32)));
			
			add(new RequiredTextField("phone")
					.add(LengthValidator.max(16)));
			
			add(new TextField("email")
					.add(LengthValidator.max(128))
					.add(EmailAddressPatternValidator.getInstance()));
			
			add(new Button("cancel") {
				protected void onSubmit() {
					onCancel();
				}
			}.setDefaultFormProcessing(false));
		}
		
		private void beanFromTo(Contact src, Contact dst) {
			dst.setFirstname(src.getFirstname());
			dst.setLastname(src.getLastname());
			dst.setEmail(src.getEmail());
			dst.setPhone(src.getPhone());
		}
		
		public void applyFormChanges(Contact dst) {
			beanFromTo(bean, dst);
		}
		
		protected void onSubmit() {
			onSave();
		}
		
		protected abstract void onSave();
		protected abstract void onCancel();
	}
}
