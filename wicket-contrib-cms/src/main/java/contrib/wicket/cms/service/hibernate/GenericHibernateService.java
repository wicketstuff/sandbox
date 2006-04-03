package contrib.wicket.cms.service.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import contrib.wicket.cms.service.GenericService;

@Transactional
public class GenericHibernateService implements GenericService {

	SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public Session session() {
		return sessionFactory.getCurrentSession();
	}
	
	public void save(Object object) {
		session().saveOrUpdate(object);
	}

}
