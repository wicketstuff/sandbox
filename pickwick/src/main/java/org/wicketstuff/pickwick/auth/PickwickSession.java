package org.wicketstuff.pickwick.auth;

import org.apache.wicket.Request;
import org.apache.wicket.authentication.AuthenticatedWebApplication;
import org.apache.wicket.authentication.AuthenticatedWebSession;
import org.apache.wicket.authorization.strategies.role.Roles;
import org.wicketstuff.pickwick.GuiceWebApplicationFactory;
import org.wicketstuff.pickwick.PickwickApplication;
import org.wicketstuff.pickwick.backend.Settings;
import org.wicketstuff.pickwick.bean.User;

import com.google.inject.Inject;
import com.google.inject.Injector;

/**
 * A session for the {@link PickwickApplication}.
 * This session contains a {@link User} which can be acceded via {@link #getUser()}
 * @author doume
 *
 */
public class PickwickSession extends AuthenticatedWebSession {
	@Inject
	Settings settings;
	
	private User user;
	
	public PickwickSession(AuthenticatedWebApplication application, Request request) {
		super(application, request);
		// Injects dependencies into the fields and methods of this session object
		Injector inj = (Injector) application.getServletContext().getAttribute(GuiceWebApplicationFactory.GUICE);
		inj.injectMembers(this);
	}
	
	@Override
	public boolean authenticate(String username, String password) {
		if (settings.getUserManagement().checkUser(username, password)){
			user = settings.getUserManagement().getUser(username);
			return true;
		}else{
			user = getDefaultUser();
			return false;
		}
	}

	@Override
	public Roles getRoles() {
		return new Roles(user.getRole());
	}

	/**
	 * get the current connected user
	 * @return
	 */
	public User getUser() {
		return user;
	}
	
	public User getDefaultUser(){
		User user = new User();
		user.setName("anonymous");
		user.setRole("anonymous");
		user.setAdmin(false);
		return user;
	}

}
