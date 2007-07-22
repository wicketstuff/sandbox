package org.wicketstuff.pickwick.auth;

import javax.servlet.http.HttpServletRequest;

import com.google.inject.ImplementedBy;

/**
 * Component responsible for managing authentication
 * @author doume
 */
@ImplementedBy(DummyAuthenticationModule.class)
public interface AuthenticationModule {
	User getUser(HttpServletRequest request);
}
