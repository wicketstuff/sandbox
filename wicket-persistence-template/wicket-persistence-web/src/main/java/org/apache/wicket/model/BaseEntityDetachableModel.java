package org.apache.wicket.model;

import org.apache.wicket.injection.web.InjectorHolder;
import org.apache.wicket.persistence.domain.Message;
import org.apache.wicket.persistence.provider.MessageRepository;
import org.apache.wicket.spring.injection.annot.SpringBean;

public class BaseEntityDetachableModel  <E extends Message> extends LoadableDetachableModel {
	
	@SpringBean(name = "messageRepository")
	protected MessageRepository messageRepository;

	private String id;
	private Class<E> clazz;

	public BaseEntityDetachableModel() {
		InjectorHolder.getInjector().inject(this);
	}

	public BaseEntityDetachableModel(E baseEntity) {
		this();
		this.id = baseEntity.getId();
		this.clazz = (Class<E>) baseEntity.getClass();

	}

	public void setBaseEntityDetachableModel(Message message) {
		this.id = message.getId();
		this.clazz =(Class<E>) message.getClass();
	}
	

	@Override
	protected E load() {
	if(clazz!=null)
	{
		
		return (E) messageRepository.getById(id);
	}
	else{
		return null;
	}
	}

}
