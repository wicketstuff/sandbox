package wicket.benchmark.wicket12;

import wicket.benchmark.dao.CustomerDao;
import wicket.model.LoadableDetachableModel;

public class DetachableCustomerList extends LoadableDetachableModel {
	/** */
	private static final long serialVersionUID = 1L;

	@Override
	protected Object load() {
		return CustomerDao.getInstance().findAll();
	}
}
