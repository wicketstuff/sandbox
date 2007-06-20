package org.wicketstuff.pickwick;

import java.io.File;
import java.io.FileFilter;

import com.google.inject.Singleton;

/**
 * Only keeps filenames having .jpg extension (case insensitive) and that are not hidden (ie not starting with a dot)
 * 
 * @author <a href="mailto:jbq@apache.org">Jean-Baptiste Quenot</a>
 */
@Singleton
public class ImageFilter implements FileFilter {
	public boolean accept(File pathname) {
		if (pathname.getName().startsWith("."))
			return false;
		if (pathname.getName().toLowerCase().endsWith(".jpg"))
			return true;
		return false;
	}
}
