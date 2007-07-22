package org.wicketstuff.pickwick.auth;

import javax.servlet.http.HttpServletRequest;

public class DummyAuthenticationModule implements AuthenticationModule {
	public User getUser(HttpServletRequest request) {
		User user = new User();
		user.setName("pickwick");
		user.setAdmin(true);
		return user;
	}
}
