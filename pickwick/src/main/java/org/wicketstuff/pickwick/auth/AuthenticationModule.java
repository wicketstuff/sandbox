package org.wicketstuff.pickwick.auth;

import javax.servlet.ServletRequest;

/**
 * Component responsible for managing authentication
 * @author doume
 */
public interface AuthenticationModule {
	
	/**
	 * Return user name  
	 * @param request
	 * @return the userName
	 */
	String getUserName(ServletRequest request);
}
