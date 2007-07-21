package org.wicketstuff.pickwick.auth;

import javax.servlet.ServletRequest;

public class PickwickDefaultAuthenticationModule implements AuthenticationModule {

	public String getUserName(ServletRequest request) {
		// TODO Auto-generated method stub
		return "pickwick";
	}

}
