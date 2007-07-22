package org.wicketstuff.pickwick.auth;

import org.apache.wicket.Application;
import org.apache.wicket.Request;
import org.apache.wicket.Session;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.protocol.http.WebSession;

public class PickwickSession extends WebSession {
	private User user;

	public void setUser(User user) {
		this.user = user;
	}

	public PickwickSession(Application application, Request request) {
		super(application, request);
	}

	public PickwickSession(WebApplication application, Request request) {
		super(application, request);
	}

	public User getUser() {
		return user;
	}

	public static PickwickSession get() {
		return (PickwickSession)Session.get();
	}
}
