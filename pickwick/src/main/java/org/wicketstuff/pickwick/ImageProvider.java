package org.wicketstuff.pickwick;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

/**
 * An {@link IDataProvider} that provides images from a sequence
 * 
 * @author jbq
 */
public class ImageProvider implements IDataProvider {
	Settings settings;

	String pattern;

	String imagePath;

	ImageList imageList;

	public ImageProvider(Settings settings) {
		this.settings = settings;
	}

	private void computeImageList() {
		if (imageList == null)
			imageList = new ImageList(pattern, new File(settings
					.getImageDirectoryRoot().getPath()
					+ imagePath));
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

	public String getImagePath(File imageFile) {
		try {
			return imagePath + ImageUtils.getRelativePath(settings, imageFile);
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

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}
}
