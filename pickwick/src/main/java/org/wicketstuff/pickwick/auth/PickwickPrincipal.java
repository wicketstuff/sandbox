package org.wicketstuff.pickwick.auth;

import java.security.Principal;

import javax.servlet.ServletRequest;

import org.apache.wicket.WicketRuntimeException;
import org.wicketstuff.pickwick.GuiceWebApplicationFactory;
import org.wicketstuff.pickwick.backend.Settings;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Stage;

public class PickwickPrincipal implements Principal {
	@Inject
	Settings settings;
	
	private ServletRequest request;

	public String getName() {
		String authenticationClassName = settings.getAuthenticationModule();
		Injector inj = Guice.createInjector(Stage.DEVELOPMENT, GuiceWebApplicationFactory.getModule());

		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		if (loader == null) {
			loader = getClass().getClassLoader();
		}
		final Class<AuthenticationModule> authenticationClass;
		try {
			authenticationClass = (Class<AuthenticationModule>)loader.loadClass(authenticationClassName);
		} catch (ClassNotFoundException e) {
			throw new WicketRuntimeException("Unable to create authentication module of class " + authenticationClassName, e);
		}

		if (!AuthenticationModule.class.isAssignableFrom(authenticationClass)) {
			throw new WicketRuntimeException("Authentication module class " + authenticationClassName
					+ " must be a subclass of AuthenticationModule");
		}

		AuthenticationModule auth = (AuthenticationModule)inj.getInstance(authenticationClass);
		return auth.getUserName(request);
	}

	public ServletRequest getRequest() {
		return request;
	}

	public void setRequest(ServletRequest request) {
		this.request = request;
	}

}
