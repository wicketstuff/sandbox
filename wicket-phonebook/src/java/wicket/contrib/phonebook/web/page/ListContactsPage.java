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

import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;

import wicket.Component;
import wicket.contrib.phonebook.Contact;
import wicket.contrib.phonebook.ContactDao;
import wicket.contrib.phonebook.QueryParam;
import wicket.contrib.phonebook.web.DetachableContactModel;
import wicket.contrib.phonebook.web.PhonebookApplication;
import wicket.extensions.markup.html.repeater.data.table.DataTable;
import wicket.extensions.markup.html.repeater.data.table.HeadersToolbar;
import wicket.extensions.markup.html.repeater.data.table.IColumn;
import wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import wicket.extensions.markup.html.repeater.data.table.filter.FilterForm;
import wicket.extensions.markup.html.repeater.data.table.filter.FilterToolbar;
import wicket.extensions.markup.html.repeater.data.table.filter.FilteredAbstractColumn;
import wicket.extensions.markup.html.repeater.data.table.filter.FilteredPropertyColumn;
import wicket.extensions.markup.html.repeater.data.table.filter.GoAndClearFilter;
import wicket.extensions.markup.html.repeater.data.table.filter.IFilterStateLocator;
import wicket.extensions.markup.html.repeater.data.table.filter.SingleChoiceFilter;
import wicket.extensions.markup.html.repeater.data.table.filter.TextFilter;
import wicket.extensions.markup.html.repeater.refreshing.Item;
import wicket.extensions.markup.html.repeater.util.SortParam;
import wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import wicket.markup.html.form.ChoiceRenderer;
import wicket.markup.html.form.Form;
import wicket.markup.html.form.TextField;
import wicket.markup.html.link.Link;
import wicket.markup.html.panel.Panel;
import wicket.model.IModel;
import wicket.model.Model;
import wicket.model.PropertyModel;
import wicket.util.lang.Objects;

/**
 * Display a Pageable List of Contacts.
 * 
 * @author igor
 */
public class ListContactsPage extends BasePage {
	private String property;
	
	
	
	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	/**
	 * Constructor. Having this constructor public means that the page is
	 * 'bookmarkable' and hence can be called/ created from anywhere.
	 */
	public ListContactsPage() {
		
		add(new Link("createLink") {
			/**
			 * Go to the Edit page when the link is clicked, passing an empty
			 * Contact details
			 */
			public void onClick() {
				setResponsePage(new EditContactPage(getPage(), 0));
			}

		});

		IColumn[] columns = new IColumn[5];

		/*
		 * First column is a composite column, created by extending
		 * AbstractColumn in order to add a UserActionsPanel as the cell
		 * contents.
		 */
		columns[0] = new FilteredAbstractColumn(new Model("Actions")) {

			public void populateItem(Item cellItem, String componentId,
					IModel model) {
				final Contact contact = (Contact) model.getObject(cellItem);
				cellItem.add(new UserActionsPanel(componentId, contact));
			}

			public Component getFilter(String componentId, FilterForm form) {
				final Object originalState = Objects.clone(form
						.getModelObject());
				return new GoAndClearFilter(componentId) {
					protected void onClearSubmit() {
						getClearButton().getForm()
								.setModelObject(originalState);
					}
				};
			}

		};

		/*
		 * Use PropertyColumn to provide the 'normal' columns.
		 */
		columns[1] = new FilteredPropertyColumn(new Model("First Name"),
				"firstname", "firstname") {

			public Component getFilter(String componentId, FilterForm form) {
				return new TextFilter(componentId, new PropertyModel(form
						.getModel(), "firstname"), form);
			}

		};
		columns[2] = new FilteredPropertyColumn(new Model("Last Name"),
				"lastname", "lastname") {

			public Component getFilter(String componentId, FilterForm form) {
				String[] choices = { "ANY", "Nelson", "Bailey", "Ortiz",
						"Baker", "Johnson", "Smith" };
				return new SingleChoiceFilter(componentId, new PropertyModel(
						form.getModel(), "lastname"), form, new Model(
						(Serializable) Arrays.asList(choices)),
						new ChoiceRenderer(), true);
			}

		};
		columns[3] = new PropertyColumn(new Model("Phone Number"), "phone",
				"phone");
		columns[4] = new FilteredPropertyColumn(new Model("Email"), "email",
				"email") {

			public Component getFilter(String componentId, FilterForm form) {
				return new TextFilter(componentId, new PropertyModel(form
						.getModel(), "email"), form);
			}

		};

		// set up data provider and default sort for the data table
		ContactsDataProvider dataProvider = new ContactsDataProvider();
		dataProvider.setSort("firstname", true);

		DataTable users = new DataTable("users", Arrays.asList(columns),
				dataProvider, 10);

		// users.addBottomToolbar(new HeadersToolbar(users, dataProvider));
		users.addTopToolbar(new FilterToolbar(users, dataProvider));
		users.addBottomToolbar(new HeadersToolbar(users, dataProvider));
		add(users);

	}

	/**
	 * 
	 * @author igor
	 * 
	 * note static is important here because dataprovider is the model of the
	 * dataview so having a reference to the page will cause an out of memory
	 * error eventually
	 */
	private static class ContactsDataProvider extends SortableDataProvider
			implements IFilterStateLocator {
		private ContactDao getDao() {
			return PhonebookApplication.getInstance().getContactDao();
		}

		/**
		 * Gets an iterator for the subset of total data.
		 * 
		 * @param first
		 *            first row of data
		 * @param count
		 *            minumum number of elements to retrieve
		 * @return iterator capable of iterating over {first, first+count} items
		 */
		public Iterator iterator(int first, int count) {
			SortParam sp = getSort();
			QueryParam qp = null;
			if (sp != null) {
				qp = new QueryParam(first, count, sp.getProperty(), sp
						.isAscending());
			} else {
				qp = new QueryParam(first, count);
			}
			System.out.println(filter.toString());
			return getDao().find(qp, filter);
		}

		/**
		 * Gets total number of items in the collection.
		 * 
		 * @return total item count
		 */
		public int size() {
			return getDao().count(filter);
		}

		/**
		 * Converts the object in the collection to its model representation. A
		 * good place to wrap the object in a detachable model.
		 * 
		 * @param object
		 *            The object that needs to be wrapped
		 * @return The model representation of the object
		 */
		public IModel model(Object object) {
			return new DetachableContactModel((Contact) object);
		}

		private Contact filter = new Contact();

		public ContactsDataProvider() {
			filter.setLastname("ANY");
		}

		public Object getFilterState() {
			return filter;
		}

		public void setFilterState(Object state) {
			filter = (Contact) state;
		}
	};

	/**
	 * Provides a composite User Actions panel for the Actions column.
	 * 
	 * @author igor
	 */
	private static class UserActionsPanel extends Panel {

		public UserActionsPanel(String id, Contact contact) {
			super(id);
			final long contactId = contact.getId();

			add(new Link("editLink") {
				/**
				 * Go to the Edit page, passing this page and the id of the
				 * Contact involved.
				 */
				public void onClick() {
					setResponsePage(new EditContactPage(getPage(), contactId));
				}

			});

			add(new Link("deleteLink") {
				/**
				 * Go to the Delete page, passing this page and the id of the
				 * Contact involved.
				 */
				public void onClick() {
					setResponsePage(new DeleteContactPage(getPage(), contactId));
				}

			});

		}

	}
}
