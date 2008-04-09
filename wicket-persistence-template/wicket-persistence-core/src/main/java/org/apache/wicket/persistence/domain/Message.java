package org.apache.wicket.persistence.domain;

import javax.persistence.Entity;

@Entity
public class Message extends BaseEntity {

	private String message;

	public Message(String message) {
		super();
		this.message = message;
	}

	public Message() {
		this("");
	}

}
