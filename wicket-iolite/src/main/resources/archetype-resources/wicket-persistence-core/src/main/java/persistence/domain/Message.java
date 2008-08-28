package ${package}.persistence.domain;

import javax.persistence.Entity;

import org.domdrides.entity.UuidEntity;

@Entity
public class Message extends UuidEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;

	public Message(String message) {
		super();
		this.message = message;
	}

	public Message() {
		this("");
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	

}
