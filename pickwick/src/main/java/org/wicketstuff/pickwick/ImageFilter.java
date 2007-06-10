package org.wicketstuff.pickwick;

import java.io.File;
import java.io.FileFilter;

public class ImageFilter implements FileFilter {
	public boolean accept(File pathname) {
		if (pathname.getName().startsWith("."))
			return false;
		if (pathname.getName().toLowerCase().endsWith(".jpg"))
			return true;
		return false;
	}
}
