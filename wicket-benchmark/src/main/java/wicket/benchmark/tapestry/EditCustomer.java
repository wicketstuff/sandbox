package wicket.benchmark.tapestry;

import org.apache.tapestry.IExternalPage;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.form.IPropertySelectionModel;
import org.apache.tapestry.form.StringPropertySelectionModel;
import org.apache.tapestry.html.BasePage;

import wicket.benchmark.dao.Customer;
import wicket.benchmark.dao.CustomerDao;

/**
 * Edit customer benchmark page for Tapestry.
 * 
 * @author Phil Kulak
 */
public abstract class EditCustomer extends BasePage implements IExternalPage {
    public abstract void setCustomer(Customer customer);
    public abstract Customer getCustomer();
    
    public void activateExternalPage(java.lang.Object[] parameters, IRequestCycle cycle) {
        setCustomer(CustomerDao.getInstance().findById((Integer) parameters[0]));
    }
    
    public void onSubmit(IRequestCycle cycle) {
        CustomerDao.getInstance().saveOrUpdate(getCustomer());
        cycle.activate("Home");
    }
    
    public static IPropertySelectionModel getStateModel() {
        return new StringPropertySelectionModel(CustomerDao.STATES);
    }
}