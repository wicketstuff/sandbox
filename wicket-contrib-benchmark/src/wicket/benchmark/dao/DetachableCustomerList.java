package wicket.benchmark.dao;

import java.util.List;

import wicket.Component;
import wicket.model.AbstractDetachableModel;
import wicket.model.IModel;

public class DetachableCustomerList extends AbstractDetachableModel {
    private List<Customer> customers;

    @Override
    public IModel getNestedModel() {
        return null;
    }

    @Override
    protected void onAttach() {
        customers = CustomerDao.getInstance().findAll();
    }

    @Override
    protected void onDetach() {
        customers = null;
    }

    @Override
    protected Object onGetObject(Component component) {
        return customers;
    }

    @Override
    protected void onSetObject(Component component, Object object) {
        throw new UnsupportedOperationException();
    }
}
