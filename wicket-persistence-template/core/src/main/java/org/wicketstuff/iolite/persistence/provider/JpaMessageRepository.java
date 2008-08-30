package org.wicketstuff.iolite.persistence.provider;

import java.util.List;

import org.apache.wicket.persistence.domain.Message;
import org.domdrides.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

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
