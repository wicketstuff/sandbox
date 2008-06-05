package org.wicketstuff.pickwick.auth;

import java.util.ArrayList;

import org.apache.wicket.Application;
import org.apache.wicket.Request;
import org.apache.wicket.Session;
import org.apache.wicket.authentication.AuthenticatedWebApplication;
import org.apache.wicket.authentication.AuthenticatedWebSession;
import org.apache.wicket.authorization.strategies.role.Roles;
import org.apache.wicket.guice.GuiceInjectorHolder;
import org.wicketstuff.pickwick.GuiceWebApplicationFactory;
import org.wicketstuff.pickwick.PickwickApplication;
import org.wicketstuff.pickwick.backend.Settings;
import org.wicketstuff.pickwick.bean.Role;
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
	
	
	
	public PickwickSession(Request request) {
		super(request);
		Application application = AuthenticatedWebApplication.get();
		// Injects dependencies into the fields and methods of this session object
		// FIXME AuthenticatedWebApplication should probably allow to use GuiceComponentInjector
		Injector inj = ((GuiceInjectorHolder)application.getMetaData(GuiceInjectorHolder.INJECTOR_KEY)).getInjector();
		inj.injectMembers(this);
	}

	public PickwickSession(AuthenticatedWebApplication application, Request request) {
		super(application, request);
		// Injects dependencies into the fields and methods of this session object
		// FIXME AuthenticatedWebApplication should probably allow to use GuiceComponentInjector
		Injector inj = ((GuiceInjectorHolder)application.getMetaData(GuiceInjectorHolder.INJECTOR_KEY)).getInjector();
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

	public String getUserName(){
		if (getUser() == null){
			return getDefaultUser().getName();
		}
		return getUser().getName();
	}
	

	@Override
	public Roles getRoles() {
		ArrayList<String> labels = new ArrayList();
		for(Role role : user.getRoles()){
			labels.add(role.getLabel());
		}
		return new Roles(labels.toArray(new String[labels.size()]));
	}

	/**
	 * get the current connected user
	 * @return
	 */
	public User getUser() {
		if (user == null){
			return getDefaultUser();
		}
		return user;
	}
	
	public User getDefaultUser(){
		User user = new User();
		user.setName("anonymous");
		//user.setRole("anonymous");
		user.setAdmin(false);
		return user;
	}

	public static PickwickSession get() {
		return (PickwickSession) Session.get();
	}
}
