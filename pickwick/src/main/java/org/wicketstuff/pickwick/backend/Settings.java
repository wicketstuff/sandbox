package org.wicketstuff.pickwick.backend;

import java.io.File;
import java.io.Serializable;

import org.wicketstuff.pickwick.FeedGenerator;
import org.wicketstuff.pickwick.backend.users.UserManagement;

import com.google.inject.ImplementedBy;

/**
 * PickWick settings
 * 
 * @author <a href="mailto:jbq@apache.org">Jean-Baptiste Quenot</a>
 */
@ImplementedBy(DefaultSettings.class)
public interface Settings extends Serializable{
	/**
	 * @return base URL of the application for use in the RSS feed, see {@link FeedGenerator}
	 */
	String getBaseURL();

	/**
	 * @return {@link File} pointing to the directory containing the images
	 */
	File getImageDirectoryRoot();
	
	/**
	 * @return UserMangement implementation used on the application
	 */
	UserManagement getUserManagementModule();
}
