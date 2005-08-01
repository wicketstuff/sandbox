package wicket.examples.cdapp2;

import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;


import wicket.RequestCycle;
import wicket.contrib.data.model.hibernate.IHibernateDao;

/**
 * @author Phil Kulak
 */
public class CdComponentDao implements IHibernateDao {
	public Object execute(final IHibernateCallback callback) {
		HibernateTemplate template = ((CdRequestCycle) RequestCycle.get())
			.getDao().getHibernateTemplate();
		
		return template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) {
				return callback.execute(session);
			}
		});
	}
}
