package org.wicketstuff.pickwick;

import java.io.File;
import java.io.Serializable;

/**
 * PickWick settings
 * 
 * @author <a href="mailto:jbq@apache.org">Jean-Baptiste Quenot</a>
 */
public class Settings implements Serializable{
	File imageDirectoryRoot;

	String baseURL;

	/**
	 * @return base URL of the application for use in the RSS feed, see {@link FeedGenerator}
	 */
	public String getBaseURL() {
		return baseURL;
	}

	public void setBaseURL(String baseURL) {
		this.baseURL = baseURL;
	}

	/**
	 * @return {@link File} pointing to the directory containing the images
	 */
	public File getImageDirectoryRoot() {
		return imageDirectoryRoot;
	}

	public void setImageDirectoryRoot(File imageDirectoryRoot) {
		this.imageDirectoryRoot = imageDirectoryRoot;
	}
}
