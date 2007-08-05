package org.wicketstuff.pickwick.bean;

import java.io.Serializable;


public class User implements Serializable, Comparable<User>{
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
	public int compareTo(User arg0) {
		if (this.name==null || arg0.getName() == null) return 0;
		return this.name.compareTo(arg0.getName());
	}
}
