package org.apache.wicket.persistence.provider;

import java.util.Set;

import org.apache.wicket.persistence.domain.Message;

public interface MessageRepository {

    public Message add(Message entity);

    public boolean contains(Message entity)
;
    public Set<Message> getAll();

    public Message getById(String id);

    public void remove(Message entity)
;
    public Message update(Message entity);
	
}
