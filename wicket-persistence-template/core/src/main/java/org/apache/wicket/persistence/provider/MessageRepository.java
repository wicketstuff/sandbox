package org.apache.wicket.persistence.provider;

import java.util.List;
import java.util.Set;

import org.apache.wicket.persistence.domain.Message;
import org.springframework.transaction.annotation.Transactional;

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
