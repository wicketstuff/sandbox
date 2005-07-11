package wicket.benchmark.dao;

import wicket.Component;
import wicket.model.IModel;

public class DetachableCustomer implements IModel {
    private Integer id;
    private transient Customer customer;
    
    public DetachableCustomer(Customer customer) {
        this.customer = customer;
        id = customer.getId();
    }

    public IModel getNestedModel() {
        return null;
    }

    public Object getObject(Component component) {
        if (customer == null) {
            customer = CustomerDao.getInstance().findById(id);
        }
        return customer;
    }

    public void setObject(Component component, Object object) {
        customer = (Customer) object;
        id = customer.getId();
    }

    public void detach() {
        customer = null;
    }
}
