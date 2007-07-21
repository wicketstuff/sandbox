package org.wicketstuff.pickwick.backend.users;

import java.io.Serializable;

/**
 * A bean to represent a user in the pickwick auth system
 * @author Vincent Demay
 *
 */
public class UserBean implements Serializable{
	private String email;
	private String role;
	private String name;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
}
