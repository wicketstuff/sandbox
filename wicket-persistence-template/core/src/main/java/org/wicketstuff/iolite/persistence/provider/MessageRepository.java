package org.wicketstuff.iolite.persistence.provider;

import java.util.List;
import java.util.Set;

import org.springframework.transaction.annotation.Transactional;
import org.wicketstuff.iolite.persistence.domain.Message;

@Transactional
public interface MessageRepository {

    public Message add(Message entity);

    public boolean contains(Message entity)
;
    public Set<Message> getAll();
    public List<Message> getAllAsList();

    public Message getById(String id);

    public void remove(Message entity);
    public Message update(Message entity);
	
}
