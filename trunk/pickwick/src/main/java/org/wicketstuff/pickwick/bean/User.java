package org.wicketstuff.pickwick.bean;

import java.io.Serializable;
import java.util.List;


public class User implements Serializable, Comparable<User>{
	private List<Role> roles;
	private String name;
	private boolean admin;
	private String password;
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
	public List<Role> getRoles() {
		return roles;
	}
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	public int compareTo(User arg0) {
		if (this.name==null || arg0.getName() == null) return 0;
		return this.name.compareTo(arg0.getName());
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
