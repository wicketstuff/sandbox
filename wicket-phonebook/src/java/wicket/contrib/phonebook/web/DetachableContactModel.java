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
import wicket.model.AbstractReadOnlyDetachableModel;
import wicket.model.IModel;

/**
 * 
 * @author igor
 *
 */
public class DetachableContactModel extends AbstractReadOnlyDetachableModel {
	private long id;
	private transient Contact contact;
	
	public DetachableContactModel(Contact contact) {
		this(contact.getId());
		this.contact=contact;
	}

	public DetachableContactModel(long id) {
		if (id==0) {
			throw new IllegalArgumentException();
		}
		this.id=id;
	}
	
	public IModel getNestedModel() {
		return null;
	}

	protected void onAttach() {
		contact=PhonebookApplication.getInstance().getContactDao().load(id);
	}

	protected void onDetach() {
		contact=null;
	}

	protected Object onGetObject(Component component) {
		return contact;
	}

}
