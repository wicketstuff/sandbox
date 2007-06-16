package org.wicketstuff.pickwick.frontend;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.wicketstuff.pickwick.ImageList;
import org.wicketstuff.pickwick.ImageProperties;
import org.wicketstuff.pickwick.backend.Settings;

/**
 * An {@link IDataProvider} that provides images from a given sequence
 * 
 * @author <a href="mailto:jbq@apache.org">Jean-Baptiste Quenot</a>
 */
public class ImageProvider implements IDataProvider {
	Settings settings;

	String imagePath;

	ImageList imageList;

	public ImageProvider(Settings settings) {
		this.settings = settings;
	}

	private void computeImageList() {
		if (imageList == null)
			imageList = new ImageList(new File(settings.getImageDirectoryRoot().getPath() + imagePath));
	}

	public Iterator iterator(int first, int count) {
		computeImageList();
		List<ImageProperties> items = new ArrayList<ImageProperties>();
		Iterator<ImageProperties> it = imageList.iterator();
		int i = 0;
		while (it.hasNext() && i <= count) {
			if (i >= first)
				items.add(it.next());
			i++;
		}
		return items.iterator();
	}

	public void detach() {
	}

	public int size() {
		computeImageList();
		return imageList.size();
	}

	public IModel model(Object object) {
		return new Model((Serializable) object);
	}

	/**
	 * Returns the path to the image relative to image directory root
	 * 
	 * @param imageFile
	 * @return
	 */
	public String getImageRelativePath(File imageFile) {
		try {
			return imageFile.getCanonicalPath().substring(
					(int) settings.getImageDirectoryRoot().getCanonicalPath().length() + 1);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
}
