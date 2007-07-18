package org.wicketstuff.pickwick.auth;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.protocol.http.WebApplication;
import org.wicketstuff.pickwick.GuiceWebApplicationFactory;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Stage;

public class PickwickWrapperServletRequest extends HttpServletRequestWrapper {

	public PickwickWrapperServletRequest(HttpServletRequest arg0) {
		super(arg0);
	}
	
	@Override
	public Principal getUserPrincipal() {
		Injector inj = Guice.createInjector(Stage.DEVELOPMENT, GuiceWebApplicationFactory.getModule());

		return inj.getInstance(PickwickPrincipal.class);
	}

}
