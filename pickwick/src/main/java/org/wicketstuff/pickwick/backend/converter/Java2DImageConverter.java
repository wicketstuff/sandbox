package org.wicketstuff.pickwick.backend.converter;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.commons.io.IOUtils;
import org.wicketstuff.pickwick.ImageConversionException;

/**
 * A {@link ImageConverter} implmentation using Java2D
 * @author Vincent Demay
 *
 */
public class Java2DImageConverter implements ImageConverter {

	public void scaleImage(File source, File destination, int size, Float quality) throws ImageConversionException {
		BufferedImage image = null;

		try {
		    image = ImageIO.read(source);
		} catch (IOException ioe) {}

		int x = image.getWidth();
		int y = image.getHeight();
		int new_x = size;
		int new_y = (new_x * y / x);

		FileOutputStream os = null;
		try {
		    // Write image to the output stream
			os = new FileOutputStream(destination);
		    ImageIO.write(createResizedCopy(image, new_x, new_y, true), "jpeg", os);
		} catch (IOException e) {
		    // TODO log error
		} finally{
			IOUtils.closeQuietly(os);
		}
	}

	BufferedImage createResizedCopy(Image originalImage, int scaledWidth, int scaledHeight, boolean preserveAlpha) {
		int imageType = preserveAlpha ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
		BufferedImage scaledBI = new BufferedImage(scaledWidth, scaledHeight, imageType);

		Graphics2D g = scaledBI.createGraphics();

		if (preserveAlpha) {
			g.setComposite(AlphaComposite.Src);
		}
		g.drawImage(originalImage, 0, 0, scaledWidth, scaledHeight, null);
		g.dispose();
		return scaledBI;

	}

}
