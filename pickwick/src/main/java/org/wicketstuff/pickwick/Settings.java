package org.wicketstuff.pickwick;

import java.io.File;

public class Settings {
	File imageDirectoryRoot;

	File baseURL;

	public File getBaseURL() {
		return baseURL;
	}

	public void setBaseURL(File baseURL) {
		this.baseURL = baseURL;
	}

	public File getImageDirectoryRoot() {
		return imageDirectoryRoot;
	}

	public void setImageDirectoryRoot(File imageDirectoryRoot) {
		this.imageDirectoryRoot = imageDirectoryRoot;
	}
}
