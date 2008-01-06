package wicket.benchmark.wicket13;

import org.apache.wicket.model.LoadableDetachableModel;

import wicket.benchmark.dao.CustomerDao;

public class DetachableCustomerList extends LoadableDetachableModel {
	/** */
	private static final long serialVersionUID = 1L;

	@Override
	protected Object load() {
		return CustomerDao.getInstance().findAll();
	}
}
