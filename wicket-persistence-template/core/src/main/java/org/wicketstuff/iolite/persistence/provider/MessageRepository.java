package org.wicketstuff.iolite.persistence.provider;

import java.util.List;

import org.domdrides.repository.Repository;
import org.wicketstuff.iolite.persistence.domain.Message;

public interface MessageRepository extends Repository<Message, String> {

    public List<Message> getAllAsList();

	
}
