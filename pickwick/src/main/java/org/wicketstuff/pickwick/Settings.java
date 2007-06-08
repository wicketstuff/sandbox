package org.wicketstuff.pickwick;

import java.io.File;
import java.io.Serializable;

public class Settings implements Serializable{
	File imageDirectoryRoot;

	String baseURL;

	public String getBaseURL() {
		return baseURL;
	}

	public void setBaseURL(String baseURL) {
		this.baseURL = baseURL;
	}

	public File getImageDirectoryRoot() {
		return imageDirectoryRoot;
	}

	public void setImageDirectoryRoot(File imageDirectoryRoot) {
		this.imageDirectoryRoot = imageDirectoryRoot;
	}
}
