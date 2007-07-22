package org.wicketstuff.pickwick.backend;

import java.io.File;

import org.apache.wicket.RequestCycle;
import org.apache.wicket.protocol.http.WebRequestCycle;
import org.wicketstuff.pickwick.auth.DummyAuthenticationModule;
import org.wicketstuff.pickwick.backend.users.UserManagement;
import org.wicketstuff.pickwick.backend.users.XmlUserManagement;

import com.google.inject.Singleton;

@Singleton
public class DefaultSettings implements Settings {
	public File getImageDirectoryRoot() {
		return new File("src/main/webapp/images");
	}

	public String getBaseURL() {
		// return "http://localhost:8080/";
		return ((WebRequestCycle) RequestCycle.get()).getWebRequest().getHttpServletRequest().getRequestURL()
				.toString();
	}

	public String getAuthenticationModule() {
		return DummyAuthenticationModule.class.getName();
	}

	public UserManagement getUserManagementModule() {
		return new XmlUserManagement();
	}
}
