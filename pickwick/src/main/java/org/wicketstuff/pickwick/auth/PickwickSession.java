package org.wicketstuff.pickwick.auth;

import org.apache.wicket.Request;
import org.apache.wicket.authentication.AuthenticatedWebApplication;
import org.apache.wicket.authentication.AuthenticatedWebSession;
import org.apache.wicket.authorization.strategies.role.Roles;
import org.wicketstuff.pickwick.PickwickApplication;
import org.wicketstuff.pickwick.backend.users.UserManagement;
import org.wicketstuff.pickwick.backend.users.XmlUserManagement;
import org.wicketstuff.pickwick.bean.User;

/**
 * A session for the {@link PickwickApplication}.
 * This session contains a {@link User} which can be acceded via {@link #getUser()}
 * @author doume
 *
 */
public class PickwickSession extends AuthenticatedWebSession {

	
	private User user;
	private UserManagement userManagement;
	
	public PickwickSession(AuthenticatedWebApplication application, Request request) {
		super(application, request);
	}
	
	@Override
	public boolean authenticate(String username, String password) {
		//FIXME : how to inject that. We are in a thread (session)
		//Jbq?
		userManagement = new XmlUserManagement();
		if (userManagement.checkUser(username, password)){
			user = userManagement.getUser(username);
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
