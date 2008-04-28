package persistence.provider;

import java.util.List;

import persistence.domain.BaseEntity;
import persistence.domain.Message;

public interface GeneralDao {

	public <T extends BaseEntity> T findEntity(Long id,Class<T> clazz);
	public List<Message> getMessages();
	public <T extends BaseEntity> void persist(T object );
}
