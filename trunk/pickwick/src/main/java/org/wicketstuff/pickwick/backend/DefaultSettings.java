package org.wicketstuff.pickwick.backend;

import java.io.File;

import org.apache.wicket.Application;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.protocol.http.WebRequestCycle;
import org.wicketstuff.pickwick.backend.users.UserManagement;
import org.wicketstuff.pickwick.backend.users.XmlUserManagement;

import com.google.inject.Singleton;

@Singleton
public class DefaultSettings implements Settings {
	public File getImageDirectoryRoot() {
		return new File("images");
	}
	
	public String getGeneratedImageDirectoryPath() {
		return "imageGenerated";
	}

	public String getBaseURL() {
		// return "http://localhost:8080/";
		return ((WebRequestCycle) RequestCycle.get()).getWebRequest().getHttpServletRequest().getRequestURL()
				.toString();
	}

	public UserManagement getUserManagement() {
		return new XmlUserManagement();
	}

	public String getGoogleKey() {
		return "ABQIAAAALjfJpigGWq5XvKwy7McLIxTEpDPjw6LRH7yL06TcOjcEpKZmCRRGeXL1BMh_MNX22hDtswyQqVAOyQ";
	}

	public String getConfigurationType() {
		return Application.DEVELOPMENT;
	}
	
}
