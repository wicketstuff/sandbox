package org.wicketstuff.teatime;

import org.apache.wicket.Request;
import org.apache.wicket.protocol.http.WebSession;

public class TeatimeSession extends WebSession {
	/** */
	private static final long serialVersionUID = 1L;

	private String username;

	public TeatimeSession(Request request) {
		super(request);
	}

	public static TeatimeSession get() {
		return (TeatimeSession) WebSession.get();
	}

	public boolean isAuthenticated() {
		return username != null && !"".equals(username);
	}

	public void logout() {
		username = null;
		invalidate();
	}

	public boolean login(String username, String password) {
		if (WicketApplication.get().login(username, password)) {
			this.username = username;
			return true;
		}
		return false;
	}
}
