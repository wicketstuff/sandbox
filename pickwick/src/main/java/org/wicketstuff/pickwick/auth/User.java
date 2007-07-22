package org.wicketstuff.pickwick.auth;

import org.wicketstuff.pickwick.frontend.pages.Role;

public class User {
	private Role role;
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
}
