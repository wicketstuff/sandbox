package wicket.benchmark.wicket13;

import java.text.SimpleDateFormat;
import java.util.List;

import wicket.benchmark.dao.Customer;
import wicket.benchmark.dao.CustomerDao;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

/**
 * List customers benchmark page for Wicket.
 * 
 * @author Phil Kulak
 */
public class CustomerList extends WebPage {
	private static class CustomerListView extends ListView {
		public CustomerListView(String id, IModel model) {
			super(id, model);
		}

		@Override
		protected IModel getListItemModel(IModel model, int index) {
			Customer customer = ((List<Customer>) model.getObject()).get(index);
			return new CompoundPropertyModel(new DetachableCustomer(customer));
		}

		@Override
		protected void populateItem(final ListItem item) {
			item.add(new Label("firstName"));
			item.add(new Label("lastName"));
			item.add(new Label("state"));
			item.add(new Label("birthDate", new Model() {
				@Override
				public Object getObject() {
					Customer customer = (Customer) item.getModelObject();
					SimpleDateFormat FORMAT = new SimpleDateFormat("MMMM d, yyyy");
					return FORMAT.format(customer.getBirthDate());
				}
			}));

			item.add(new Link("delete") {
				@Override
				public void onClick() {
					Customer customer = (Customer) getParent().getModelObject();
					CustomerDao.getInstance().delete(customer);
				}
			});

			item.add(new Link("edit") {
				@Override
				public void onClick() {
					EditCustomer editPage = new EditCustomer(getParent()
							.getModel());
					setResponsePage(editPage);
				}
			});
		}
	}

	private static SimpleDateFormat FORMAT = new SimpleDateFormat(
			"MMMM d, yyyy");

	public CustomerList() {
		add(new CustomerListView("rows", new DetachableCustomerList()));
	}
}
