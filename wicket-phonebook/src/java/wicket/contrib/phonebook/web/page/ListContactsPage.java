/*
 * $Id: ListContactsPage.java 903 2006-08-30 09:08:51Z ivaynberg $
 * $Revision: 903 $
 * $Date: 2006-08-30 02:08:51 -0700 (Wed, 30 Aug 2006) $
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

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.ChoiceFilteredPropertyColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilterForm;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilterToolbar;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilteredAbstractColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.GoAndClearFilter;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.TextFilteredPropertyColumn;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import wicket.contrib.phonebook.Contact;
import wicket.contrib.phonebook.ContactDao;
import wicket.contrib.phonebook.web.ContactsDataProvider;

/**
 * Display a Pageable List of Contacts.
 * 
 * @author igor
 */
public class ListContactsPage extends BasePage {
	@SpringBean(name = "contactDao")
	private ContactDao dao;

	/**
	 * Provides a composite User Actions panel for the Actions column.
	 * 
	 * @author igor
	 */
	private static class UserActionsPanel extends Panel {
		public UserActionsPanel(String id, IModel contactModel) {
			super(id);
			addEditLink(contactModel);
			addDeleteLink(contactModel);
		}

		private void addDeleteLink(IModel contactModel) {
			add(new Link("deleteLink", contactModel) {
				/**
				 * Go to the Delete page, passing this page and the id of the
				 * Contact involved.
				 */
				public void onClick() {
					setResponsePage(new DeleteContactPage(getPage(), getModel()));
				}
			});
		}

		private void addEditLink(IModel contactModel) {
			add(new Link("editLink", contactModel) {
				/**
				 * Go to the Edit page, passing this page and the id of the
				 * Contact involved.
				 */
				public void onClick() {
					setResponsePage(new EditContactPage(getPage(), getModel()));
				}
			});
		}

	}

	/**
	 * Constructor. Having this constructor public means that the page is
	 * 'bookmarkable' and hence can be called/ created from anywhere.
	 */
	public ListContactsPage() {
		addCreateLink();
		IColumn[] columns = createColumns();
		// set up data provider
		ContactsDataProvider dataProvider = new ContactsDataProvider(dao);
		// create the data table
		DefaultDataTable users = new DefaultDataTable("users", Arrays
				.asList(columns), dataProvider, 10);
		users.addTopToolbar(new FilterToolbar(users, dataProvider));
		add(users);

	}

	private IColumn[] createColumns() {
		IColumn[] columns = new IColumn[5];
		/*
		 * This is a composite column, created by extending
		 * FilteredAbstractColumn. This column adds a UserActionsPanel as its
		 * cell contents. It also provides the go-and-clear filter control
		 * panel.
		 */
		columns[0] = createActionsColumn();
		columns[1] = createColumn("first.name", "firstname", "firstname");
		columns[2] = new ChoiceFilteredPropertyColumn(new ResourceModel(
				"last.name"), "lastname", "lastname",
				new LoadableDetachableModel() {
					protected Object load() {
						List<String> uniqueLastNames = dao.getUniqueLastNames();
						uniqueLastNames.add(0, "");
						return uniqueLastNames;
					}
				});
		columns[3] = createColumn("phone", "phone", "phone");
		columns[4] = createColumn("email", "email", "email");
		return columns;
	}

	private TextFilteredPropertyColumn createColumn(String key,
			String sortProperty, String propertyExpression) {
		return new TextFilteredPropertyColumn(new ResourceModel(key),
				sortProperty, propertyExpression);
	}

	private FilteredAbstractColumn createActionsColumn() {
		return new FilteredAbstractColumn(new Model(getString("actions"))) {
			// return the go-and-clear filter for the filter toolbar
			public Component getFilter(String componentId, FilterForm form) {
				return new GoAndClearFilter(componentId, form, new ResourceModel("filter"), new ResourceModel("clear"));
			}

			// add the UserActionsPanel to the cell item
			public void populateItem(Item cellItem, String componentId,
					IModel model) {
				cellItem.add(new UserActionsPanel(componentId, model));
			}
		};
	}

	private void addCreateLink() {
		add(new Link("createLink") {
			/**
			 * Go to the Edit page when the link is clicked, passing an empty
			 * Contact details
			 */
			public void onClick() {
				setResponsePage(new EditContactPage(getPage(), new Model(
						new Contact())));
			}
		});
	}
}
