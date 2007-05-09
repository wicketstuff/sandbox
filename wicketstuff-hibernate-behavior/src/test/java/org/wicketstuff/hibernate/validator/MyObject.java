package org.wicketstuff.hibernate.validator;

import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;

/**
 * <p>
 * POJO to test Hibernate Validator annotations
 * </p>
 * 
 * @author rsonnek
 * @author miojo
 */
public class MyObject {

	@NotNull
	private String id;

	@Length(max = 50)
	private String name;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}