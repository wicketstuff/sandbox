package org.wicketstuff.pickwick.backend;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import org.apache.wicket.RequestCycle;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.protocol.http.WebRequestCycle;
import org.wicketstuff.pickwick.backend.users.UserManagement;
import org.wicketstuff.pickwick.backend.users.XmlUserManagement;

import com.google.inject.Singleton;

@Singleton
public class PropertyFileSettings implements Settings {
	
	private Properties prop;
	
	public PropertyFileSettings() {
		prop = new Properties();
		try {
			prop.load(PropertyFileSettings.class.getClassLoader().getResourceAsStream("pickwick.properties"));
		} catch (IOException e) {
			throw new WicketRuntimeException("cannot load pickwick.properties",e);
		}
	}
	
	public File getImageDirectoryRoot() {
		return new File(prop.getProperty("image.root.directory"));
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
		return prop.getProperty("google.map.key");
	}

	public String getConfigurationType() {
		return prop.getProperty("application.configuration");
	}

	public String getGeneratedImageDirectoryPath() {
		return prop.getProperty("image.generated.directory");
	}
	
}
