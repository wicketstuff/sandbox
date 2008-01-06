package wicket.benchmark.wicket12;

import java.util.Arrays;
import java.util.Date;

import wicket.benchmark.dao.Customer;
import wicket.benchmark.dao.CustomerDao;
import wicket.markup.html.WebPage;
import wicket.markup.html.form.DropDownChoice;
import wicket.markup.html.form.Form;
import wicket.markup.html.form.TextField;
import wicket.model.CompoundPropertyModel;
import wicket.model.IModel;

/**
 * Edit customer benchmark page for Wicket.
 * 
 * @author Phil Kulak
 */
public class EditCustomer extends WebPage {    
    public EditCustomer(IModel customer) {
        add(new EditCustomerForm("form", customer));
    }
    
    private static class EditCustomerForm extends Form {
        public EditCustomerForm(String id, IModel model) {
            super(id, new CompoundPropertyModel(model));
            add(new TextField("firstName"));
            add(new TextField("lastName"));
            add(new DropDownChoice("state", Arrays.asList(CustomerDao.STATES)));
            add(new TextField("birthDate", Date.class));
        }
        
        @Override
        public void onSubmit() {
            Customer customer = (Customer) getModelObject();
            CustomerDao.getInstance().saveOrUpdate(customer);
            setResponsePage(newPage(CustomerList.class));
        }
    }
}