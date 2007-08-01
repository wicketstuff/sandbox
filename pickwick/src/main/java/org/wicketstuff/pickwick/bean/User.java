package org.wicketstuff.pickwick.bean;


public class User {
	private String role;
	private String name;
	private boolean admin;
	public boolean isAdmin() {
		return admin;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
}
