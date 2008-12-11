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
package wicket.contrib.activewidgets.examples.web;

import org.apache.wicket.model.LoadableDetachableModel;

import wicket.contrib.activewidgets.examples.Contact;
import wicket.contrib.activewidgets.examples.ContactDao;

/**
 * Detachable, read-only Contact model. Ensures that memory used to load the
 * contact details is immediately freed rather than held in the session.
 * Typically used by <tt>List</tt>-type pages, where multiple elements are
 * loaded at a time.
 *
 * @author ivaynberg
 */
public class DetachableContactModel extends LoadableDetachableModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * database identity of the contact
	 */
	private final long id;

	/**
	 * dao reference - must be a wicket-wrapped proxy, holding onto a reference
	 * to the real dao will cause its serialization into session or a
	 * not-serializable exception when the servlet container serializes the
	 * session.
	 */
	private final ContactDao dao;

	/**
	 * Constructor
	 *
	 * @param contact
	 * @param dao
	 */
	public DetachableContactModel(Contact contact, ContactDao dao) {
		super(contact);
		this.id = contact.getId();
		this.dao = dao;
	}

	/**
	 * Loads the contact from the database
	 *
	 * @see wicket.model.LoadableDetachableModel#load()
	 */
	@Override
	protected Object load() {
		return dao.load(id);
	}
}
