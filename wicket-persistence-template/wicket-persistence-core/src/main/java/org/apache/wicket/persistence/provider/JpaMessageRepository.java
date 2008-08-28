package org.apache.wicket.persistence.provider;

import org.apache.wicket.persistence.domain.Message;
import org.domdrides.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class JpaMessageRepository extends JpaRepository<Message, String> implements MessageRepository {

	public JpaMessageRepository() {
		super(Message.class);
	}

}
