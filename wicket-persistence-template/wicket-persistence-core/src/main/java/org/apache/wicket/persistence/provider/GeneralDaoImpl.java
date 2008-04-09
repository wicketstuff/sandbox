package org.apache.wicket.persistence.provider;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;

import org.apache.wicket.persistence.domain.BaseEntity;
import org.apache.wicket.persistence.domain.Message;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class GeneralDaoImpl implements GeneralDao, InitializingBean {
	public void afterPropertiesSet() throws Exception {

	}

	private EntityManager entityManager;

	protected Session getHibernateSession() {
		return (Session) entityManager.getDelegate();
	}

	/**
	 * 
	 * @param entityManager
	 */
	@PersistenceContext(type = PersistenceContextType.EXTENDED)
	public void setEntityManager(final EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public <T extends BaseEntity> T findEntity(Long id, Class<T> clazz) {
		Query query = entityManager.createQuery("SELECT e FROM "
				+ clazz.getName() + " e WHERE e.id=" + id);
		return (T) query.getSingleResult();
	}

	public List<Message> getMessages() {
		Criteria criteria = getHibernateSession().createCriteria(Message.class);

		List<Message> messages = (List<Message>) criteria.list();
		if (messages == null) {
			messages = new ArrayList<Message>();
		}
		return messages;

	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public <T extends BaseEntity> void persist(T object) {
		if (object.getId() == null)
			entityManager.persist(object);
		else {
			entityManager.merge(object);
		}

	}

}
