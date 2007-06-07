package org.wicketstuff.pickwick;

import java.io.File;

public interface ImageConverter {
	/**
	 * Scale an image at specified size and save result to specified file
	 * @param source original file (remains untouched)
	 * @param destination destination file to produce
	 * @param size maximum width of a square the resulting image should fit in
	 * @param quality optional, sets the quality parameter of the resulting image
	 * @throws ImageConversionException
	 */
	void scaleImage(File source, File destination, int size, Float quality) throws ImageConversionException;
}
