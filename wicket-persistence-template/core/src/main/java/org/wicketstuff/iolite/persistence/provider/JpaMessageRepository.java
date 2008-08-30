package org.wicketstuff.iolite.persistence.provider;

import java.util.List;

import org.domdrides.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.wicketstuff.iolite.persistence.domain.Message;

@Repository
public class JpaMessageRepository extends JpaRepository<Message, String>
		implements MessageRepository {

	public JpaMessageRepository() {
		super(Message.class);
	}

	@SuppressWarnings("unchecked")
	public List<Message> getAllAsList() {
		final String messageJpaql = "select x from " + Message.class.getName()
				+ " x";
		return getJpaTemplate().find(messageJpaql);
	}

}
