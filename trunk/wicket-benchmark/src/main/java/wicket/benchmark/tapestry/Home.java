package wicket.benchmark.tapestry;

import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.tapestry.html.BasePage;

import wicket.benchmark.dao.Customer;
import wicket.benchmark.dao.CustomerDao;

/**
 * List customers benchmark page for Tapestry.
 * 
 * @author Phil Kulak
 */
public abstract class Home extends BasePage {
    private static SimpleDateFormat FORMAT = 
        new SimpleDateFormat("MMMM d, yyyy");
    
    public abstract Customer getCustomer();
    public abstract void setCustomer(Customer customer);
    
    public List<Customer> getCustomers() {        
        return CustomerDao.getInstance().findAll();
    }
    
    public String getFormattedDate() {
        return FORMAT.format(getCustomer().getBirthDate());
    }
    
    public void deleteCustomer(Integer id) {
        CustomerDao dao = CustomerDao.getInstance();
        dao.delete(dao.findById(id));
    }
}