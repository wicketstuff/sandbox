package wicket.benchmark.page;

import java.text.SimpleDateFormat;
import java.util.List;

import wicket.benchmark.dao.Customer;
import wicket.benchmark.dao.CustomerDao;
import wicket.benchmark.dao.DetachableCustomer;
import wicket.benchmark.dao.DetachableCustomerList;
import wicket.markup.html.WebPage;
import wicket.markup.html.basic.Label;
import wicket.markup.html.link.Link;
import wicket.markup.html.list.ListItem;
import wicket.markup.html.list.ListView;
import wicket.model.IModel;

/**
 * List customers benchmark page for Wicket.
 * 
 * @author Phil Kulak
 */
public class CustomerList extends WebPage {
    private static SimpleDateFormat FORMAT = 
        new SimpleDateFormat("MMMM d, yyyy");
    
    public CustomerList() {
        add(new CustomerListView("rows", new DetachableCustomerList()));
    }
    
    private static class CustomerListView extends ListView {
        public CustomerListView(String id, IModel model) {
            super(id, model);
        }
        
        @Override
        protected IModel getListItemModel(IModel model, int index) {
            Customer customer = ((List<Customer>) model.getObject(this)).get(index);
            return new DetachableCustomer(customer);
        }

        @Override
        protected void populateItem(ListItem item) {
            Customer customer = (Customer) item.getModelObject();
            String birthDate = FORMAT.format(customer.getBirthDate());
            
            item.add(new Label("firstName", customer.getFirstName()));
            item.add(new Label("lastName", customer.getLastName()));
            item.add(new Label("state", customer.getState()));
            item.add(new Label("birthDate", birthDate));
            
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
                   EditCustomer editPage = new EditCustomer(getParent().getModel());
                   setResponsePage(editPage);
               }
            });
        }
    }
}
