package org.wicketstuff.pickwick.backend.converter;

import java.io.File;

import org.wicketstuff.pickwick.ImageConversionException;

import com.google.inject.ImplementedBy;

/**
 * Converter that operates on a picture to achieve scaling, rotation, etc.
 * 
 * @author <a href="mailto:jbq@apache.org">Jean-Baptiste Quenot</a>
 */
@ImplementedBy(ImageMagickImageConverter.class)
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
