package wicket.benchmark.wicket;

import java.text.SimpleDateFormat;
import java.util.List;

import wicket.Component;
import wicket.benchmark.dao.Customer;
import wicket.benchmark.dao.CustomerDao;
import wicket.benchmark.dao.DetachableCustomer;
import wicket.benchmark.dao.DetachableCustomerList;
import wicket.markup.html.WebPage;
import wicket.markup.html.basic.Label;
import wicket.markup.html.link.Link;
import wicket.markup.html.list.ListItem;
import wicket.markup.html.list.ListView;
import wicket.model.CompoundPropertyModel;
import wicket.model.IModel;
import wicket.model.Model;

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
            return new CompoundPropertyModel(new DetachableCustomer(customer));
        }

        @Override
        protected void populateItem(final ListItem item) {
            item.add(new Label("firstName"));
            item.add(new Label("lastName"));
            item.add(new Label("state"));
            item.add(new Label("birthDate", new Model(){@Override
            public Object getObject(Component arg0) {
                Customer customer = (Customer) item.getModelObject();
                return FORMAT.format(customer.getBirthDate());
            }}));
            
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
