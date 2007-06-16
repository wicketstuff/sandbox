package org.wicketstuff.pickwick;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.wicketstuff.pickwick.backend.ImageUtils;
import org.wicketstuff.pickwick.frontend.ImageProvider;

/**
 * List of images from a sequence, used in {@link ImageProvider}
 * 
 * @author <a href="mailto:jbq@apache.org">Jean-Baptiste Quenot</a>
 */
public class ImageList implements Serializable {
	private List imageList;

	public Object get(int index) {
		return imageList.get(index);
	}

	public Iterator iterator() {
		return imageList.iterator();
	}

	public int size() {
		return imageList.size();
	}

	public ImageList(File directory) {
		imageList = new ArrayList();
		File[] files = directory.listFiles(PickWickApplication.get().getImageFilter());
		if (files == null) {
			throw new RuntimeException("Not a directory: " + directory);
		}
		for (int i = 0; i < files.length; i++) {
			File file = files[i];
			try {
				imageList.add(ImageUtils.getImageProperties(file));
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}
}
