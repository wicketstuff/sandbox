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

import wicket.Component;
import wicket.contrib.phonebook.Contact;
import wicket.contrib.phonebook.ContactDao;
import wicket.model.AbstractReadOnlyDetachableModel;
import wicket.model.IModel;

/**
 * Detatchable, read-only Contact model.
 * Ensures that memory used to load the contact details is immediately freed
 * rather than held in the session.
 * Typically used by <tt>List</tt>-type pages, where multiple elements are
 * loaded at a time.
 *
 * @author igor
 */
public class DetachableContactModel extends AbstractReadOnlyDetachableModel {
	private final long id;
	private final ContactDao dao;
	private transient Contact contact;
	
	public DetachableContactModel(Contact contact, ContactDao dao) {
		this(contact.getId(), dao);
		this.contact=contact;
	}

	public DetachableContactModel(long id, ContactDao dao) {
		if (id==0) {
			throw new IllegalArgumentException();
		}
		this.id=id;
		this.dao=dao;
	}

	/**
	 * Returns null to indicate there is no nested model.
	 * @return Null
	 */
	public IModel getNestedModel() {
		return null;
	}


	/**
	 * Uses the DAO to load the required contact when the model is attatched to the request.
	 */
	protected void onAttach() {
		contact=dao.load(id);
	}

	/**
	 * Clear the reference to the contact when the model is detatched.
	 */
	protected void onDetach() {
		contact=null;
	}

	/**
	 * Called after attatch to return the detatchable object.
	 * @param component  The component asking for the object
	 * @return The detatchable object.
	 */
	protected Object onGetObject(Component component) {
		return contact;
	}

}
